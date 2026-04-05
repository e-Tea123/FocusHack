package com.csci448.focushack.ui.navigation.specs

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.csci448.focushack.ui.settingsscreen.ConsequencesScreen
import com.csci448.focushack.ui.viewmodel.ConsequencesViewModel
import com.csci448.focushack.ui.viewmodel.FocusHackViewModelFactory
import com.csci448.focushack.ui.viewmodel.intent.ConsequenceIntent

object ConsequencesScreenSpec : IScreenSpec {
    override val route = "consequences"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title = "Consequences"

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

        ConsequencesScreen(onDetailClick = {detail, id->
            when{
                (id == 1)->{
                    dispatcher.invoke(ConsequenceIntent.setConsequenceDetails(
                        enabled = state.consequencesEnabled[0],
                        detail = detail,
                        onEnabledChange = ConsequenceIntent.consequenceNotifToggle,
                        id = id
                    ))

                    navController.navigate(ConsequenceDetailScreenSpec.route)
                }
                (id == 2)->{
                    dispatcher.invoke(ConsequenceIntent.setConsequenceDetails(
                        enabled = state.consequencesEnabled[1],
                        detail = detail,
                        onEnabledChange = ConsequenceIntent.consequenceMessageToggle,
                        id = id
                    ))
                    navController.navigate(ConsequenceDetailScreenSpec.route)
                }
                (id == 3)->{
                    dispatcher.invoke(ConsequenceIntent.setConsequenceDetails(
                        enabled = state.consequencesEnabled[2],
                        detail = detail,
                        onEnabledChange = ConsequenceIntent.consequenceSelfieToggle,
                        id = id
                    ))
                    navController.navigate(ConsequenceDetailScreenSpec.route)
                }
            }
        })
    }

    @Composable
    override fun TopAppBarActions(
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?
    ) {}
}