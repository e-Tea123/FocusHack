package com.csci448.focushack.ui.settingsscreen.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.csci448.focushack.ui.viewmodel.intent.ConsequenceIntent

@Composable
fun ConsequenceDetailScreen(details: String,
                            enabled: Boolean,
                            onEnabledChange:(Boolean)->Unit){
    Log.d("CSCI448.ConsequenceDetailScreen", "Compose, enabled: $enabled")

    Row(Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(48.dp)) {
        Text(details,
            modifier = Modifier.fillMaxWidth(.5f),
            textAlign = TextAlign.Center)
        Checkbox(enabled, onEnabledChange)
    }
}