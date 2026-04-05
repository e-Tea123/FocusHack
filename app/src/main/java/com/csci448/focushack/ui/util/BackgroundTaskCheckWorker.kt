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
                                workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val taskDateList = inputData.getLongArray("taskDateList")
        val taskIdList = inputData.getIntArray("taskIdList")

        if(taskDateList?.size != taskIdList?.size) Log.d("CSCI448.BackgroundTaskCheckWorker", "Error, mismatched arrays in worker")

        if(taskIdList == null || taskDateList == null
            || taskIdList.size == 0 || taskDateList.size == 0){
            Log.d("CSCI448.BackgroundTaskCheckWorker", "No data, cancel worker")
            return Result.success()
        }

        Log.d("CSCI448.BackgroundTaskCheckWorker", "Worker run")
        var outputData: Data
        val IDs: MutableList<Int> = mutableListOf()
        var idx = 0

        for(date in taskDateList){
            if(date < System.currentTimeMillis()){
                IDs.add(taskIdList[idx])
            }
            idx++
        }
        if(IDs.isNotEmpty()){
            outputData = workDataOf(
                "IDs" to IDs.toIntArray()
            )
            Log.d("CSCI448.BackgroundTaskCheckWorker", "$idx expired tasks found")
            return Result.success(outputData)
        } else {
            Log.d("CSCI448.BackgroundTaskCheckWorker", "No expired tasks found")
            return Result.success()
        }
    }
}
