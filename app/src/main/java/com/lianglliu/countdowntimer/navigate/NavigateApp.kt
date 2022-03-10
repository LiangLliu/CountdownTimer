package com.lianglliu.countdowntimer.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lianglliu.countdowntimer.CountdownTimerAppState
import com.lianglliu.countdowntimer.rememberMobileComputingAppState
import com.lianglliu.countdowntimer.ui.countdown.Countdown
import com.lianglliu.countdowntimer.ui.timerpicker.TimerPicker
import com.lianglliu.countdowntimer.ui.viewmodels.SharedViewModel

@Composable
fun NavigateApp(
    appState: CountdownTimerAppState = rememberMobileComputingAppState(),
    sharedViewModel: SharedViewModel
) {
    NavHost(
        navController = appState.navController,
        startDestination = "timerPicker"
    ) {
        composable(route = "timerPicker") {
            TimerPicker(navController = appState.navController, viewModel = sharedViewModel)
        }
        composable("countdown") {
            Countdown(navController = appState.navController, viewModel = sharedViewModel)
        }
    }
}