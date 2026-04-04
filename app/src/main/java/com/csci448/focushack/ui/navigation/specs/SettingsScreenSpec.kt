package com.csci448.focushack.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.csci448.focushack.ui.settingsscreen.SettingsScreen


object SettingsScreenSpecdata : IScreenSpec {
    override val route = "settings"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val title = "Settings"

    @Composable
    override fun Content(
        navBackStackEntry: NavBackStackEntry,
        navController: NavController,
        modifier: Modifier
    ) {
        SettingsScreen()
    }
}