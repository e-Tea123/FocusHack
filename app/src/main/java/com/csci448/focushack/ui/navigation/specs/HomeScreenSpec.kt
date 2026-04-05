package com.csci448.focushack.ui.navigation.specs

import android.Manifest
import android.app.Activity
import android.app.AppOpsManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.csci448.focushack.R
import com.csci448.focushack.ui.home.HomeScreen
import com.csci448.focushack.ui.util.PunishTime
import com.csci448.focushack.ui.util.UsageDialog
import com.csci448.focushack.ui.viewmodel.ConsequencesViewModel
import com.csci448.focushack.ui.viewmodel.FocusHackViewModelFactory
import com.csci448.focushack.ui.viewmodel.TaskViewModel
import com.csci448.focushack.ui.viewmodel.intent.ConsequenceIntent
import java.io.File
import java.security.Permission
import java.security.Permissions

data object HomeScreenSpec : IScreenSpec {
    override val route = "home"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title = "Home"

    @Composable
    override fun Content(
        navBackStackEntry: NavBackStackEntry,
        navController: NavController,
        modifier: Modifier
    ) {
        val context = LocalContext.current
        val viewModel = ViewModelProvider(
            store = navBackStackEntry.viewModelStore,
            factory = FocusHackViewModelFactory(),
            defaultCreationExtras = FocusHackViewModelFactory.creationExtras(
                defaultCreationExtras = navBackStackEntry.defaultViewModelCreationExtras,
                context = context
            )
        )[ConsequencesViewModel::class]

        val (state, dispatcher, effects) = viewModel.use(navBackStackEntry)
        var launchCamera = remember{mutableStateOf(false)}

        UsageDialog(
            showDialog = state.showUsageDialogue,
            onDismiss = { dispatcher(ConsequenceIntent.ToggleUsageDialogue) }
        )

        HomeScreen(
            taskButtonClicked = { navController.navigate(TaskListScreenSpec.route) },
            focusButtonClicked = {
                if(hasUsageAccess(context)){
                    Log.d("CSCI448.HomeScreenSpec", "permission granted")
                    dispatcher.invoke(ConsequenceIntent.ToggleFocus)
                } else {
                    Log.d("CSCI448.HomeScreenSpec", "launching permission")
                    dispatcher.invoke(ConsequenceIntent.ToggleUsageDialogue)
                }
            },
            focusEnabled = state.focusEnabled,
            launchCamera.value
        )

        var randInt = (0..2).random()
        var count = 0
        while(state.consequencesEnabled[randInt] != true){
            randInt = (0..2).random()
            count++
            if(count >= 20){
                PunishTime.PunishUser = false
                break
            }
        }

        if(PunishTime.PunishUser) {
            when (randInt) {
                0 -> {//notifications

                }

                1 -> {//message
                    val msgString: String =
                        "Hey, I just failed a task! Let me tell you all about it."

                    var intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, "Check out my new failure!")
                        putExtra(Intent.EXTRA_TEXT, msgString)
                    }
                    intent = Intent.createChooser(intent, "Share Your Failure!")
                    context.startActivity(intent)
                }

                2 -> {//selfie
                    Log.d("CSCI448.HomeScreenSpec", "Consequence: Selfie")
                    launchCamera.value = true
                }
            }
        }
    }

    @Composable
    override fun TopAppBarActions(
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?
    ) {
    }

    fun hasUsageAccess(context: Context): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(),
            context.packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }
}