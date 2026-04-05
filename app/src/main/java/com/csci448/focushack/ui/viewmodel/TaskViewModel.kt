package com.csci448.focushack.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.serialization.saved
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.csci448.focushack.data.TaskDatabase
import com.csci448.focushack.data.entities.TaskData
import com.csci448.focushack.data.repos.TaskRepo
import com.csci448.focushack.ui.util.BackgroundTaskCheckWorker
import com.csci448.focushack.ui.viewmodel.effect.TaskEffect
import com.csci448.focushack.ui.viewmodel.intent.TaskIntent
import com.csci448.focushack.ui.viewmodel.state.TaskState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import java.util.concurrent.TimeUnit

class TaskViewModel
internal constructor(
    private val taskRepo: TaskRepo,
    savedStateHandle: SavedStateHandle,
    context: Context
) : ViewModel(), IViewModelContract<TaskState, TaskIntent, TaskEffect> {

    private var _savedState: TaskState by savedStateHandle.saved(
        key = "SAVED_CARD_STATE",
        init = { TaskState() }
    )

    private val _stateFlow: MutableStateFlow<TaskState> = MutableStateFlow(_savedState)
    override val stateFlow: StateFlow<TaskState> = _stateFlow.asStateFlow()

    private val _effectFlow: MutableStateFlow<TaskEffect?> = MutableStateFlow(null)
    override val effectFlow: SharedFlow<TaskEffect?> = _effectFlow.asSharedFlow()

    private var idx: Int = 0

    init {
        viewModelScope.launch {
            taskRepo.getTasks().collectLatest { taskList ->
                //brute force method of picking up index from where left off.
                //prolly bad code. But it works(?). So it's good code(???).
                for (task in taskList) {
                    if (task.id >= idx) {
                        idx = task.id + 1
                    }
                }
                _stateFlow.update { state ->
                    _savedState = state.copy(
                        taskList = taskList
                    )
                    _savedState
                }
            }
        }

        viewModelScope.launch {
            val dateList = mutableListOf<Long>()
            val IDList = mutableListOf<Int>()
            val tasks = taskRepo.getTasks().first()

            for (task in tasks) {
                dateList.add(task.deadline)
                IDList.add(task.id)
            }

            val testWorkRequest = OneTimeWorkRequest
                .Builder(BackgroundTaskCheckWorker::class.java)
                .setInputData(
                    workDataOf(
                        "taskDateList" to dateList.toLongArray(),
                        "taskIdList" to IDList.toIntArray()
                    )
                )
                .build()
            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(testWorkRequest)


            workManager.getWorkInfoByIdFlow(testWorkRequest.id).collect { workInfo ->
                when (workInfo?.state) {
                    WorkInfo.State.RUNNING -> {
                        Log.d("CSCI448.TaskVM", "WorkInfo Running")
                    }

                    WorkInfo.State.SUCCEEDED -> {
                        Log.d("CSCI448.TaskVM", "WorkInfo Succeeded")
                        Log.d("CSCI448.TaskVM", "WorkInfo Succeeded")
                        val IDs = workInfo.outputData.getIntArray("IDs")
                        if (IDs != null) {
                            Log.d("CSCI448.TaskVM", "Task(s) expired: ${IDs.size}")
                            for (id in IDs) {
                               tasks.forEachIndexed {idx, task->

                                   val newList = _stateFlow.value.taskList.map { taskItem ->
                                       if (taskItem.id == id) {
                                           Log.d("CSCI448.TaskVM", "task expired: ${taskItem.taskName}")
                                           taskItem.copy(isExpired = true)
                                       } else taskItem
                                   }

                                   _stateFlow.update { state ->
                                       _savedState = state.copy(
                                           taskList = newList
                                       )
                                       _savedState
                                   }
                               }
                            }
                        }
                    }

                    else -> {
                        Log.d("CSCI448.TaskVM", "WorkInfo Other")
                    }
                }
            }
        }



        /*
        val workRequest = PeriodicWorkRequestBuilder<BackgroundTaskCheckWorker>(120, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "checkTasksWorker", // unique name
            ExistingPeriodicWorkPolicy.KEEP, // KEEP = don’t replace existing
            request = workRequest
        )
         */
    }

    override fun handleIntent(intent: TaskIntent){
        when(intent) {
            is TaskIntent.TaskComplete -> {
                viewModelScope.launch {
                    _stateFlow.update { state ->
                        //kinda convoluted BUT IT WORKSSSS
                        val newList = state.taskList.toMutableList()
                        var index: Int = 0
                        state.taskList.forEachIndexed { idx, task->
                            if(task.id == intent.id) index = idx
                        }
                        newList[index] = TaskData(
                            newList[index].taskName,
                            newList[index].goal,
                            newList[index].taskDescription,
                            newList[index].id,
                            intent.value,
                            newList[index].deadline,
                            newList[index].isExpired
                        )

                        _savedState = state.copy(taskList = newList)
                        taskRepo.deleteTask(newList[index])
                        _savedState
                    }
                }
            }
            is TaskIntent.UpdateNewTaskName -> {
                viewModelScope.launch {
                    _stateFlow.update { state ->
                        val newData = TaskData(
                        intent.newName,
                            state.newTask.goal,
                            state.newTask.taskDescription,
                            idx,
                            state.newTask.finished,
                            state.newTask.deadline,
                            state.newTask.isExpired
                        )
                        _savedState = state.copy(newTask = newData)
                        _savedState
                    }
                }
            }
            is TaskIntent.UpdateNewTaskDescription -> {
                viewModelScope.launch {
                    _stateFlow.update { state ->
                        val newData = TaskData(
                            state.newTask.taskName,
                            state.newTask.goal,
                            intent.newDescription,
                            idx,
                            state.newTask.finished,
                            state.newTask.deadline,
                            state.newTask.isExpired
                        )
                        _savedState = state.copy(newTask = newData)
                        _savedState
                    }
                }
            }
            is TaskIntent.UpdateNewTaskDate -> {
                if(intent.newDate != null) {
                    viewModelScope.launch {
                        _stateFlow.update { state ->
                            val newData = TaskData(
                                state.newTask.taskName,
                                state.newTask.goal,
                                state.newTask.taskDescription,
                                idx,
                                state.newTask.finished,
                                intent.newDate + 40000000,
                                state.newTask.isExpired
                            )
                            if(intent.newDate + 40000000 < System.currentTimeMillis()) newData.isExpired = true
                            _savedState = state.copy(newTask = newData)
                            _savedState
                        }
                    }
                }
            }
            is TaskIntent.UpdateNewTaskGoal -> {
                viewModelScope.launch {
                    _stateFlow.update { state ->
                        val newData = TaskData(
                            state.newTask.taskName,
                            intent.newGoal,
                            state.newTask.taskDescription,
                            idx,
                            state.newTask.finished,
                            state.newTask.deadline,
                            state.newTask.isExpired
                        )
                        _savedState = state.copy(newTask = newData)
                        _savedState
                    }
                }
            }
            is TaskIntent.SaveTask -> {
                viewModelScope.launch {
                    taskRepo.addTask(_stateFlow.value.newTask)
                    idx++
                }
            }
            is TaskIntent.ResetTask -> {
                viewModelScope.launch {
                    _stateFlow.update { state ->
                        val newData = TaskData(
                            "",
                            "",
                            "",
                            0,
                            false,
                            0L,
                            isExpired = false
                        )
                        _savedState = state.copy(newTask = newData)
                        _savedState
                    }
                }
            }
        }
    }
}