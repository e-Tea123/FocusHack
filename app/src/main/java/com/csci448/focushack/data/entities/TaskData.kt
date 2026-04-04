package com.csci448.focushack.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.Date

@Entity(tableName = "tasks")
@Serializable
class TaskData(val taskName: String,
               val goal: String,
               val taskDescription: String,
               @PrimaryKey
               val id: Int,
               var finished: Boolean,
               val deadline: Long)