package com.csci448.focushack.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.csci448.focushack.ui.navigation.specs.IScreenSpec

@Composable
fun FocusHackTopBar(navController: NavHostController){
    val navBackStackEntryState = navController.currentBackStackEntryAsState()
    IScreenSpec.TopBar(navBackStackEntryState.value, navController)
}

@Preview
@Composable
fun PreviewFocusHackTopBar(){
    FocusHackTopBar(navController = rememberNavController())
}