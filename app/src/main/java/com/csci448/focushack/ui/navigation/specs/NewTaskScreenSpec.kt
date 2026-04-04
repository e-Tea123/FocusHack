package com.csci448.focushack.ui.navigation.specs

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.csci448.focushack.R
import com.csci448.focushack.ui.newtask.NewTaskScreen
import com.csci448.focushack.ui.viewmodel.FocusHackViewModelFactory
import com.csci448.focushack.ui.viewmodel.TaskViewModel
import com.csci448.focushack.ui.viewmodel.intent.TaskIntent

object NewTaskScreenSpec : IScreenSpec {
    override val route = "newtask"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title = "New Task"

    @SuppressLint("UnrememberedGetBackStackEntry")
    @Composable
    override fun Content(
        navBackStackEntry: NavBackStackEntry,
        navController: NavController,
        modifier: Modifier
    ) {
        val context = LocalContext.current

        val viewModel = ViewModelProvider(
            store = navController.getBackStackEntry(TaskListScreenSpec.route).viewModelStore,
            factory = FocusHackViewModelFactory(),
            defaultCreationExtras = FocusHackViewModelFactory.creationExtras(
                defaultCreationExtras = navBackStackEntry.defaultViewModelCreationExtras,
                context = context
            )
        )[TaskViewModel::class]

        val (state, dispatcher, effects) = viewModel.use(navBackStackEntry)

        NewTaskScreen(state.newTask.taskName,
            {newName-> dispatcher.invoke(TaskIntent.UpdateNewTaskName(newName)) },
            {date-> dispatcher.invoke(TaskIntent.UpdateNewTaskDate(date))},
            listOf(),
            state.newTask.goal,
            {goal-> dispatcher.invoke((TaskIntent.UpdateNewTaskGoal(goal)))},
            state.newTask.taskDescription,
            {newDesc-> dispatcher.invoke(TaskIntent.UpdateNewTaskDescription(newDesc))},
            {dispatcher.invoke(TaskIntent.SaveTask)
                dispatcher.invoke(TaskIntent.ResetTask)
            navController.popBackStack()},
            resetTask = {dispatcher.invoke(TaskIntent.ResetTask)})
    }

    @Composable
    override fun TopAppBarActions(
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?
    ) {}
}