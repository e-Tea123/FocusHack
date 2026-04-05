package com.csci448.focushack.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.csci448.focushack.data.daos.TaskDao
import com.csci448.focushack.data.entities.TaskData

@Database(entities = [TaskData::class], version = 2)
abstract class TaskDatabase : RoomDatabase() {
    companion object {
        @Volatile private var INSTANCE: TaskDatabase? = null
        fun getInstance(context: Context): TaskDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(context, TaskDatabase::class.java,
                        "focushack-database")
                        .addMigrations(MIGRATION_1_2)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE tasks ADD COLUMN isExpired INTEGER NOT NULL DEFAULT 0")
            }
        }
    }

    abstract val taskDao: TaskDao
}