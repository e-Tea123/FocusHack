package com.csci448.focushack.ui.tasklist

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.csci448.focushack.data.entities.TaskData
import com.csci448.focushack.ui.tasklist.components.TaskBlock
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextField

@Composable
fun TaskScreen(taskList: List<TaskData>,
                   checkClicked:(Boolean, Int)->Unit){
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = taskList){item->
            TaskBlock(taskName = item.taskName,
                goal = item.goal,
                description = item.taskDescription,
                finished = item.finished,
                onCheckChanged = {bool-> checkClicked(bool, item.id)},
                date = item.deadline)
        }
    }
}

@Preview
@Composable
fun PreviewTaskList(){
    TaskScreen(mutableListOf(), {bool, int->})
}