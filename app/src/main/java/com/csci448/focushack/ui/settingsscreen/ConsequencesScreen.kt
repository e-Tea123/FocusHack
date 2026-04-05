package com.csci448.focushack.ui.settingsscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csci448.focushack.R

@Composable
fun ConsequencesScreen(onDetailClick:(String, Int)->Unit){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Row(modifier = Modifier.fillMaxWidth()
            .clickable(onClick = {onDetailClick(
                "Notifications will be sent over the next 24H with increasing frequency",
                1)}
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly){

            Text("Notification Spam",
                textAlign = TextAlign.Start,
                fontSize = 24.sp)
            Image(painterResource(R.drawable.arrow_icon),
                "Clickable arrow",
                modifier = Modifier.height(24.dp).width(24.dp))
        }
    }
}