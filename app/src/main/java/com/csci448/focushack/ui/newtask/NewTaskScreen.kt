package com.csci448.focushack.ui.newtask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskScreen(taskName: String,
                  onTaskNameChange:(String)->Unit,
                  onDateSelected: (Long?) -> Unit,
                  goalList: List<String>,
                  selectedGoal: String,
                  onGoalSelected:(String)->Unit,
                  taskDescription: String,
                  onTaskDescriptionChanged:(String)->Unit,
                  saveTask:()->Unit,
                  resetTask:()->Unit){
    val fontSize = 20.sp
    Column(verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()) {
            Text("Task Name: ", fontSize = fontSize)
            TextField(value = taskName, onTaskNameChange)
        }

        Text("Due Date: ", fontSize = fontSize)

        val datePickerState = rememberDatePickerState()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                DatePicker(modifier = Modifier.height(500.dp)
                    .width(500.dp),
                    state = datePickerState
                )
            }
        }
        LaunchedEffect(datePickerState.selectedDateMillis) {
            onDateSelected(datePickerState.selectedDateMillis)
        }


        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()) {
            Text("Goal: ", fontSize = fontSize)

            var expanded = false
            ExposedDropdownMenuBox(expanded = expanded,
                onExpandedChange = { expanded = it }) {
                TextField(value = selectedGoal,
                    readOnly = true,
                    onValueChange = {},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                    },
                    modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable))
                ExposedDropdownMenu(onDismissRequest = { expanded = false },
                    expanded = expanded){
                    goalList.forEach{goal->
                        DropdownMenuItem(text = { Text(goal) },
                            onClick = { onGoalSelected(goal) }
                        )
                    }
                }
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()) {
            Text("Description: ", fontSize = fontSize)
            TextField(value = taskDescription, onTaskDescriptionChanged)
        }

        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()) {
            Button(onClick = saveTask) {
                Text("Save")
            }
            Button(onClick = resetTask) {
                Text("Reset")
            }
        }
        Spacer(Modifier.height(48.dp))
    }
}

