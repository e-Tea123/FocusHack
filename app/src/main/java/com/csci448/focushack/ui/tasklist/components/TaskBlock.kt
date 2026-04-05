package com.csci448.focushack.ui.tasklist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import kotlin.time.Instant

@Composable
fun TaskBlock(taskName: String,
              goal: String,
              description: String,
              finished: Boolean,
              date: Long,
              onCheckChanged:(Boolean)->Unit){
    Row() {
        Column(
            Modifier.Companion.weight(.8f),
            horizontalAlignment = Alignment.Companion.Start
        ) {
            Text(
                taskName,
                fontSize = 32.sp
            )
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateStr = formatter.format(date)
            Text(
                text = dateStr,
                fontSize = 24.sp
            )
            Text(
                text = goal,
                fontSize = 16.sp
            )
            Text(
                text = description,
                fontSize = 16.sp
            )
        }
        Checkbox(finished, onCheckChanged)
    }
}


@Preview
@Composable
fun PreviewTask(){
    //val finishState = remember{mutableStateOf(false)}

    TaskBlock(
        taskName = "Test Task",
        goal = "test goal",
        description = "test description",
        finished = false,
        onCheckChanged = {},//{finishState.value = !finishState.value},
        date = 1765833600000L
    )
}