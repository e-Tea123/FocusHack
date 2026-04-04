package com.csci448.focushack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.csci448.focushack.ui.navigation.FocusHackBottomBar
import com.csci448.focushack.ui.navigation.FocusHackNavHost
import com.csci448.focushack.ui.navigation.FocusHackTopBar
import com.csci448.focushack.ui.theme.FocusHackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = { FocusHackTopBar(navController) },
                bottomBar = { FocusHackBottomBar(navController) },
                content = { innerPadding ->
                    FocusHackNavHost(navController = navController,
                        modifier = Modifier.padding(innerPadding))
                }
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FocusHackTheme() {
        Greeting("Android")
    }
}