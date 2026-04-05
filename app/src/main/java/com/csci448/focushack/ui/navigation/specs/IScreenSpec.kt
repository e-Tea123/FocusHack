package com.csci448.focushack.ui.navigation.specs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.csci448.focushack.R

sealed interface IScreenSpec {
    val route: String
    val arguments: List<NamedNavArgument>
    val title: String

    companion object {
        val root = "focushack"
        val startDestination = HomeScreenSpec.route
        val allScreens: Map<String?, IScreenSpec?>
            get() = IScreenSpec::class.sealedSubclasses.associate {
                it.objectInstance?.route to it.objectInstance
            }


        @Composable
        fun TopBar(navBackStackEntry: NavBackStackEntry?, navHost: NavHostController){
            val route = navBackStackEntry?.destination?.route ?: ""
            allScreens[route]?.TopAppBarContent(navHost, navBackStackEntry)
        }

        @Composable
        fun BottomBar(navBackStackEntry: NavBackStackEntry?, navHost: NavHostController){
            val route = navBackStackEntry?.destination?.route ?: ""
            allScreens[route]?.BottomAppBarContent(navHost, navBackStackEntry)
        }
    }

    @Composable
    fun Content(navBackStackEntry: NavBackStackEntry,
                navController: NavController,
                modifier: Modifier = Modifier)

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopAppBarContent(navController: NavHostController, navBackStackEntry: NavBackStackEntry?){
        TopAppBar(navigationIcon = if(navController.previousBackStackEntry != null && navBackStackEntry?.destination?.route != "battle") {
            {
                IconButton(onClick = {navController.navigateUp()}){
                    Image(
                        painter = painterResource(id = R.drawable.back_button_image),
                        contentDescription = "Back button"
                    )
                }
            }
        } else {
            {}
        },
            title = {Text(title)},
            actions = {TopAppBarActions(navController, navBackStackEntry)}
        )
    }

    @Composable
    private fun BottomAppBarContent(navController: NavHostController, navBackStackEntry: NavBackStackEntry?) {
        BottomAppBar {
            Row(horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = {}) {
                    Image(
                        painterResource(R.drawable.goal_icon),
                        "Goal Icon"
                    )
                }
                IconButton(onClick = {}) {
                    Image(
                        painterResource(R.drawable.timer_icon),
                        "Timer Icon"
                    )
                }
                IconButton(onClick = {navController.navigate(SettingsScreenSpec.route)}) {
                    Image(
                        painterResource(R.drawable.settings_icon),
                        "Settings Icon"
                    )
                }
                IconButton(onClick = {navController.navigate(CreditScreenSpec.route)}) {
                    Image(
                        painterResource(R.drawable.credit_icon),
                        "Credits Icon"
                    )
                }
            }
        }
    }

    @Composable
    abstract fun TopAppBarActions(navController: NavHostController, navBackStackEntry: NavBackStackEntry?)
}