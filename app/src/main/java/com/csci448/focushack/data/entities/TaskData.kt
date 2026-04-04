package com.csci448.focushack.data.entities

import kotlinx.serialization.Serializable

@Serializable
class TaskData(val taskName: String,
               val goal: String,
               val taskDescription: String,
               val id: Int,
               var finished: Boolean)