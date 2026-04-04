package com.csci448.focushack.ui.viewmodel.intent

sealed class TaskIntent : FocusHackIntent() {
    data class TaskComplete(val value: Boolean,
                            val id: Int) : TaskIntent()
}