package com.csci448.focushack.ui.navigation.specs

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.csci448.focushack.ui.settingsscreen.ConsequencesScreen
import com.csci448.focushack.ui.settingsscreen.components.ConsequenceDetailScreen
import com.csci448.focushack.ui.viewmodel.ConsequencesViewModel
import com.csci448.focushack.ui.viewmodel.FocusHackViewModelFactory
import com.csci448.focushack.ui.viewmodel.intent.ConsequenceIntent

object ConsequenceDetailScreenSpec : IScreenSpec {
    override val route = "consequenceDetail"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title = "Details"

    @SuppressLint("UnrememberedGetBackStackEntry")
    @Composable
    override fun Content(
        navBackStackEntry: NavBackStackEntry,
        navController: NavController,
        modifier: Modifier
    ) {
        val context = LocalContext.current
        val viewModel = ViewModelProvider(
            store = navController.getBackStackEntry(HomeScreenSpec.route).viewModelStore,
            factory = FocusHackViewModelFactory(),
            defaultCreationExtras = FocusHackViewModelFactory.creationExtras(
                defaultCreationExtras = navBackStackEntry.defaultViewModelCreationExtras,
                context = context
            )
        )[ConsequencesViewModel::class]

        val (state, dispatcher, effects) = viewModel.use(navBackStackEntry)

        ConsequenceDetailScreen(state.detailDetail,
            enabled = state.detailEnabled,
            onEnabledChange = {bool->dispatcher.invoke(state.detailOnEnabledChange)})
    }

    @Composable
    override fun TopAppBarActions(
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?
    ) {}
}