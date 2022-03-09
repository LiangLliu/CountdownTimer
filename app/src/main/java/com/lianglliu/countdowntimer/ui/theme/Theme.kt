package com.lianglliu.countdowntimer.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

@Composable
fun CountdownTimerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = darkColors(
            primary = darkRed,
            primaryVariant = lightOrange,
            secondary = lightOrange,
            background = bgColorEdge
        ),
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}