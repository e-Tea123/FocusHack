package com.csci448.focushack.ui.util

import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.csci448.focushack.R

@Composable
fun UsageDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    // Only show Toast once
    LaunchedEffect(showDialog) {
        if (showDialog) {
            Toast.makeText(context, "POPUP", Toast.LENGTH_LONG).show()
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Permission Required") },
            text = { Text(stringResource(R.string.usage_access_permission_dialog)) },
            confirmButton = {
                TextButton(onClick = {
                    val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                    context.startActivity(intent)
                    onDismiss()
                }) {
                    Text("Open Settings")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) { Text("Cancel") }
            }
        )
    }
}