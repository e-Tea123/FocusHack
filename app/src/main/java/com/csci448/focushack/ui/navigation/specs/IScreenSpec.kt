package com.csci448.focushack.ui.navigation.specs

import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
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
            title = {Text(title)}
        )
    }

}