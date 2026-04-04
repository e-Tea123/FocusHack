package com.csci448.focushack.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(taskButtonClicked:()-> Unit,
               focusButtonClicked:()-> Unit,
               focusEnabled: Boolean){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly) {

        Button(onClick = taskButtonClicked) {
            Text("Task")
        }

        if(!focusEnabled) {
            Button(
                onClick = focusButtonClicked,
                modifier = Modifier.fillMaxWidth(.5f)
                    .fillMaxHeight(.1f)
            ) {
                Text("Focus")
            }
        }
        else{
            Button(
                onClick = focusButtonClicked,
                modifier = Modifier.fillMaxWidth(.5f)
                    .fillMaxHeight(.1f),
                colors = ButtonColors(Color.Gray, Color.Black, Color.Gray, Color.Gray)
            ) {
                Text("Disable Focus")
            }
        }
    }
}


@Preview
@Composable
fun PreviewHomeScreen(){
    HomeScreen({}, {}, true)
}