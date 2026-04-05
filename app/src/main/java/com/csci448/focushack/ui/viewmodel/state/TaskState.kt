package com.csci448.focushack.ui.viewmodel.state

import androidx.compose.runtime.mutableStateListOf
import com.csci448.focushack.data.entities.TaskData
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.util.UUID

@Serializable
data class TaskState(val taskList: List<TaskData> = mutableStateListOf(),
                     val newTask: TaskData = TaskData("",
                         "",
                         "",
                         0,
                         false,
                         0L,
                         isExpired = false),
                     @Transient
                     val workerID: UUID = UUID.randomUUID()
) : FocusHackState
