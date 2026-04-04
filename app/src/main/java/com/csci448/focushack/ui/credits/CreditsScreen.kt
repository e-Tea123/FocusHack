package com.csci448.focushack.ui.credits

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CreditsScreen(){
    val fontSize = 24.sp
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(48.dp))
        Text("Implementation, Programming, and Generation Creation",
            textDecoration = TextDecoration.Underline,
            fontSize = fontSize,
            textAlign = TextAlign.Center)
        Text("Evan Chai, student at Colorado School of Mines",
            fontSize = fontSize,
            textAlign = TextAlign.Center)
        Text("Tristan Karno, student at Colorado School of Mines",
            fontSize = fontSize,
            textAlign = TextAlign.Center)
        Spacer(Modifier.height(48.dp))
        Text("Concept Creation", textDecoration = TextDecoration.Underline,
            fontSize = fontSize,
            textAlign = TextAlign.Center)
        Text("Axl B., student at Embry-Riddle Aeronautical University",
            fontSize = fontSize,
            textAlign = TextAlign.Center)
    }
}


@Preview
@Composable
fun PreviewCreditScreen(){
    CreditsScreen()
}