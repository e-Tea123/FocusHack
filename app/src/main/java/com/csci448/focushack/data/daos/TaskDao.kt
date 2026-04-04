package com.csci448.focushack.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.csci448.focushack.data.entities.TaskData
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun addTask(task: TaskData)

    @Query("SELECT * FROM tasks")
    fun getTasks(): Flow<List<TaskData>>

    @Query("SELECT * FROM tasks WHERE id = (:id)")
    suspend fun getTaskByID(id: Int): TaskData?

    @Delete
    suspend fun deleteTask(task: TaskData): Int
}