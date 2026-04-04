package com.csci448.focushack.ui.viewmodel.state

import com.csci448.focushack.data.entities.TaskData
import kotlinx.serialization.Serializable

@Serializable
data class TaskState(val taskList: MutableList<TaskData> = mutableListOf(
    TaskData("Test Name",
        "test goal",
        "test desc",
        1,
        false)
)) : FocusHackState
