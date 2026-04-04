package com.csci448.focushack.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras

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

                else -> {
                    Log.e(LOG_TAG, "Unknown ViewModel: $modelClass")
                    throw IllegalArgumentException("Unknown ViewModel")
                }
            }
        } as T
}