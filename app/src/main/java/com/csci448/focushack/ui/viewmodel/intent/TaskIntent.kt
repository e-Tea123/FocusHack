package com.csci448.focushack.ui.viewmodel.intent

sealed class TaskIntent : FocusHackIntent() {
    data class TaskComplete(val value: Boolean,
                            val id: Int) : TaskIntent()

    data class UpdateNewTaskName(val newName: String) : TaskIntent()
    data class UpdateNewTaskGoal(val newGoal: String) : TaskIntent()
    data class UpdateNewTaskDescription(val newDescription: String) : TaskIntent()
    data class UpdateNewTaskDate(val newDate: Long?) : TaskIntent()
    object SaveTask : TaskIntent()
    object ResetTask: TaskIntent()
}