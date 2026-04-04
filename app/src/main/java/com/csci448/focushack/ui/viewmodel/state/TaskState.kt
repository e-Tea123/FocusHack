package com.csci448.focushack.ui.viewmodel.state

import androidx.compose.runtime.mutableStateListOf
import com.csci448.focushack.data.entities.TaskData
import kotlinx.serialization.Serializable

@Serializable
data class TaskState(val taskList: List<TaskData> = mutableStateListOf(),
    val newTask: TaskData = TaskData("",
        "",
        "",
        0,
        false,
        0L)
) : FocusHackState
