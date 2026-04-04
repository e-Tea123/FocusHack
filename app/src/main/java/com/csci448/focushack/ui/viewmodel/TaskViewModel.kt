package com.csci448.focushack.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.serialization.saved
import androidx.lifecycle.viewModelScope
import com.csci448.focushack.data.entities.TaskData
import com.csci448.focushack.ui.viewmodel.effect.TaskEffect
import com.csci448.focushack.ui.viewmodel.intent.TaskIntent
import com.csci448.focushack.ui.viewmodel.state.TaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel
internal constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel(), IViewModelContract<TaskState, TaskIntent, TaskEffect> {

    private var _savedState: TaskState by savedStateHandle.saved(
        key = "SAVED_CARD_STATE",
        init = { TaskState() }
    )
    private val _stateFlow: MutableStateFlow<TaskState> = MutableStateFlow(_savedState)
    override val stateFlow: StateFlow<TaskState> = _stateFlow.asStateFlow()

    private val _effectFlow: MutableStateFlow<TaskEffect?> = MutableStateFlow(null)
    override val effectFlow: SharedFlow<TaskEffect?> = _effectFlow.asSharedFlow()

    override fun handleIntent(intent: TaskIntent){
        when(intent) {
            is TaskIntent.TaskComplete -> {
                _stateFlow.update {state ->
                    val newList = state.taskList
                    for(task in newList){
                        if(task.id == intent.id)task.finished = intent.value
                    }
                    _savedState = state.copy(taskList = newList)
                    _savedState
                }
            }
        }
    }
}