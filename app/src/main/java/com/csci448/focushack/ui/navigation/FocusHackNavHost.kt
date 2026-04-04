package com.csci448.focushack.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.csci448.focushack.ui.navigation.specs.IScreenSpec

@Composable
fun FocusHackNavHost(navController: NavHostController,
                      modifier: Modifier = Modifier){
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = IScreenSpec.root,
    ) {
        navigation(
            route = IScreenSpec.root,
            startDestination = IScreenSpec.startDestination
        ) {
            IScreenSpec.allScreens.forEach { (_, screen) ->
                if(screen != null) {
                    composable(
                        route = screen.route,
                        arguments = screen.arguments
                    ) { navBackStackEntry ->
                        screen.Content(
                            modifier = Modifier,
                            navController = navController,
                            navBackStackEntry = navBackStackEntry
                        )
                    }
                }
            }
        }
    }
}