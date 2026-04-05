package com.csci448.focushack.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import com.csci448.focushack.data.repos.TaskRepo

class FocusHackViewModelFactory : ViewModelProvider.Factory {
    companion object {
        private const val LOG_TAG = "448.FocusHackViewModelFactory"
        private val CONTEXT_KEY = object : CreationExtras.Key<Context> {}
        fun creationExtras(defaultCreationExtras: CreationExtras, context: Context) = MutableCreationExtras(defaultCreationExtras).apply {
            set(CONTEXT_KEY, context)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T =
        with(modelClass) {

            when {
                isAssignableFrom(TaskViewModel::class.java) -> {
                    val context = checkNotNull(extras[CONTEXT_KEY])
                    val savedStateHandle = extras.createSavedStateHandle()
                    TaskViewModel(
                        taskRepo = TaskRepo.getInstance(context),
                        savedStateHandle = savedStateHandle,
                        context
                    )
                }
                isAssignableFrom(ConsequencesViewModel::class.java) ->{
                    val context = checkNotNull(extras[CONTEXT_KEY])
                    val savedStateHandle = extras.createSavedStateHandle()
                    ConsequencesViewModel(
                        savedStateHandle = savedStateHandle
                    )
                }

                else -> {
                    Log.e(LOG_TAG, "Unknown ViewModel: $modelClass")
                    throw IllegalArgumentException("Unknown ViewModel")
                }
            }
        } as T
}