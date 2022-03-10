package com.lianglliu.countdowntimer.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lianglliu.countdowntimer.CountdownTimerAppState
import com.lianglliu.countdowntimer.rememberMobileComputingAppState
import com.lianglliu.countdowntimer.ui.timerpicker.TimerPicker
import com.lianglliu.countdowntimer.ui.countdown.Countdown

@Composable
fun NavigateApp(
    appState: CountdownTimerAppState = rememberMobileComputingAppState()
) {
    NavHost(
        navController = appState.navController,
        startDestination = "countdown"
    ) {
        composable(route = "timerPicker") {
            TimerPicker(navController = appState.navController)
        }
        composable("countdown") {
            Countdown(navController = appState.navController)
        }
    }
}