package com.csci448.focushack.ui.home

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(taskButtonClicked:()-> Unit,
               focusButtonClicked:()-> Unit,
               focusEnabled: Boolean,
               launchCamera: Boolean){

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly) {

        Button(onClick = taskButtonClicked) {
            Text("Task")
        }

        val context = LocalContext.current
        if(launchCamera){
            Button(
                onClick = {
                    val activity = context as? Activity
                    activity?.let { act ->
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        if (cameraIntent.resolveActivity(act.packageManager) != null) {
                            act.startActivity(cameraIntent)
                        } else {
                            Toast.makeText(act, "No camera app found", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(.55f)
                    .fillMaxHeight(.15f)
            ) {
                Text("Document Failure", fontSize = 36.sp)
            }
        }
        else if(!focusEnabled) {
            Button(
                onClick = focusButtonClicked,
                modifier = Modifier
                    .fillMaxWidth(.55f)
                    .fillMaxHeight(.15f)
            ) {
                Text("Focus", fontSize = 45.sp)
            }
        }
        else{
            Button(
                onClick = focusButtonClicked,
                modifier = Modifier
                    .fillMaxWidth(.55f)
                    .fillMaxHeight(.15f),
                colors = ButtonColors(Color.Gray, Color.Black, Color.Gray, Color.Gray)
            ) {
                Text("Disable Focus", fontSize = 45.sp)
            }
        }
    }
}


@Preview
@Composable
fun PreviewHomeScreen(){
    HomeScreen({}, {}, true, true)
}