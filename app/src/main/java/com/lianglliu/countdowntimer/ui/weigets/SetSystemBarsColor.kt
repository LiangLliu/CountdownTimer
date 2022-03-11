package com.lianglliu.countdowntimer.ui.weigets

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SetSystemBarsColor(
    statusBarColor: Color,
    navigationBarColor: Color,
    statusBarDarkIcons: Boolean,
    navigationDarkIcons: Boolean
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = statusBarColor,
        darkIcons = statusBarDarkIcons
    )
    systemUiController.setNavigationBarColor(
        color = navigationBarColor,
        darkIcons = navigationDarkIcons,
        navigationBarContrastEnforced = false
    )
}
