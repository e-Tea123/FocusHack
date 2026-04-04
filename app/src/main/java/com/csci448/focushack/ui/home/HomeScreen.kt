package com.csci448.focushack.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(taskButtonClicked:()-> Unit,
               focusButtonClicked:()-> Unit){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly) {

        Button(onClick = taskButtonClicked) {
            Text("Task")
        }
        Button(onClick = focusButtonClicked) {
            Text("Focus")
        }
    }
}


@Preview
@Composable
fun PreviewHomeScreen(){
    HomeScreen({}, {})
}