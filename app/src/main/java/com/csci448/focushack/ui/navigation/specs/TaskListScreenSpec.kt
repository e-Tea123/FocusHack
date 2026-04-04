package com.csci448.focushack.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.csci448.focushack.ui.tasklist.TaskScreen
import com.csci448.focushack.ui.viewmodel.FocusHackViewModelFactory
import com.csci448.focushack.ui.viewmodel.TaskViewModel
import com.csci448.focushack.ui.viewmodel.intent.TaskIntent


data object TaskListScreenSpec : IScreenSpec {
    override val route = "tasklist"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title = "Task List"

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
        )[TaskViewModel::class]

        val (state, dispatcher, effects) = viewModel.use(navBackStackEntry)

        TaskScreen(state.taskList,
            checkClicked = {bool, id-> dispatcher.invoke(TaskIntent.TaskComplete(bool, id))},
        )
    }
}