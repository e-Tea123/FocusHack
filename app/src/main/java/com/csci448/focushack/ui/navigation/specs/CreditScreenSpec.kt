package com.csci448.focushack.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.csci448.focushack.ui.credits.CreditsScreen
import com.csci448.focushack.ui.home.HomeScreen

object CreditScreenSpec : IScreenSpec {
    override val route = "credits"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title = "Credits"

    @Composable
    override fun Content(
        navBackStackEntry: NavBackStackEntry,
        navController: NavController,
        modifier: Modifier
    ) {
        CreditsScreen()
    }

    @Composable
    override fun TopAppBarActions(
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?
    ) {}
}