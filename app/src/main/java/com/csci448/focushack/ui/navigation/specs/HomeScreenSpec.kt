package com.csci448.focushack.ui.navigation.specs

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.widget.Toast
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.csci448.focushack.R
import com.csci448.focushack.ui.home.HomeScreen
import com.csci448.focushack.ui.util.UsageDialog
import com.csci448.focushack.ui.viewmodel.ConsequencesViewModel
import com.csci448.focushack.ui.viewmodel.FocusHackViewModelFactory
import com.csci448.focushack.ui.viewmodel.TaskViewModel
import com.csci448.focushack.ui.viewmodel.intent.ConsequenceIntent

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

        Log.d("CSCI448.HomeScreenSpec", "Compose: ${state.showUsageDialogue}")

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
        )
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