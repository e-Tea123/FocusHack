package com.csci448.focushack.data.repos

import android.content.Context
import android.util.Log
import com.csci448.focushack.data.TaskDatabase
import com.csci448.focushack.data.daos.TaskDao
import com.csci448.focushack.data.entities.TaskData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class TaskRepo private constructor(private val taskDao: TaskDao) {
    companion object {
        private const val LOG_TAG = "448.TaskRepo"
        private var INSTANCE: TaskRepo? = null

        /**
         * @param context
         */
        fun getInstance(context: Context): TaskRepo {
            var instance = INSTANCE
            var DBinstance: TaskDatabase
            if(instance == null) {
                DBinstance = TaskDatabase.getInstance(context)
                instance = TaskRepo(DBinstance.taskDao)
                INSTANCE = instance
            }
            return instance
        }
    }

    private val _tasks: MutableList<TaskData> = mutableListOf()
    private val _taskFlow = MutableStateFlow<List<TaskData>> (_tasks)
    val taskFlow: StateFlow<List<TaskData>> = _taskFlow.asStateFlow()

    fun getTasks(): Flow<List<TaskData>>{
        return taskDao.getTasks()
    }

    suspend fun getTaskById(id: Int): TaskData? {
        Log.d(LOG_TAG, "searching for task with id $id")
        return taskDao.getTaskByID(id)
    }

    suspend fun addTask(taskToAdd: TaskData) {
        Log.d(LOG_TAG, "adding new task $taskToAdd")
        taskDao.addTask(taskToAdd)
    }

    suspend fun deleteTask(taskToDelete: TaskData): Boolean{
        val numTasksDeleted = taskDao.deleteTask(taskToDelete)
        return numTasksDeleted > 0
    }
}