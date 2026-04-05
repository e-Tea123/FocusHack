package com.csci448.focushack.ui.util

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.csci448.focushack.data.TaskDatabase
import com.csci448.focushack.data.entities.TaskData
import com.csci448.focushack.data.repos.TaskRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update

class BackgroundTaskCheckWorker(appContext: Context,
                                workerParams: WorkerParameters,
                                private val scope: CoroutineScope)
    : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        var taskList = emptyList<TaskData>()
        val tasks = TaskDatabase.getInstance(applicationContext).taskDao.getTasks().collectLatest { TaskList->
            taskList = TaskList
        }

        var outputData: Data = workDataOf()
        for(task in taskList){
            if(task.deadline < System.currentTimeMillis()){
                outputData = workDataOf(
                    "name" to task.taskName,
                    "dueDate" to task.deadline,
                    "id" to task.id
                )
            }
        }
        return Result.success(outputData)
    }
}

suspend fun getTasks(context: Context): List<TaskData>{
    var TaskList: List<TaskData> = emptyList()
    val tasks = TaskDatabase.getInstance(context).taskDao.getTasks().collectLatest { taskList->
        TaskList = taskList
    }
    return TaskList
}