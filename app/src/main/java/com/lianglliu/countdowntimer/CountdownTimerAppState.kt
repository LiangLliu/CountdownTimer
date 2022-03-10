package com.lianglliu.countdowntimer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class CountdownTimerAppState(
    val navController: NavHostController
) {
    fun navigateBack() {
        navController.popBackStack()
    }
}

@Composable
fun rememberMobileComputingAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController) {
    CountdownTimerAppState(navController)
}