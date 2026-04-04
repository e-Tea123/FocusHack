package com.csci448.focushack.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.csci448.focushack.data.daos.TaskDao
import com.csci448.focushack.data.entities.TaskData

@Database(entities = [TaskData::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    companion object {
        @Volatile private var INSTANCE: TaskDatabase? = null
        fun getInstance(context: Context): TaskDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(context, TaskDatabase::class.java,
                        "focushack-database").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    abstract val taskDao: TaskDao
}