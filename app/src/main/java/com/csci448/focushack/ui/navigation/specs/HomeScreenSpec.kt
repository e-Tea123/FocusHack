package com.csci448.focushack.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

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
        HomeScreen(
            onStoreClick = { navController.navigate(StoreScreenSpec.route) },
            onMapClick = { navController.navigate(MapScreenSpec.route) },
            onProfileClick = { navController.navigate(ProfileScreenSpec.route) },
            onCollectionClick = { navController.navigate(CollectionScreenSpec.route) })
    }
}