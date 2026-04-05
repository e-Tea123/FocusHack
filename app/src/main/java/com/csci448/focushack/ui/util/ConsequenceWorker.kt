package com.csci448.focushack.ui.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.csci448.focushack.data.TaskDatabase
import com.csci448.focushack.data.entities.TaskData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest

class ConsequenceWorker(appContext: Context,
                                workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return Result.failure()
    }
}