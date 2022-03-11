package com.lianglliu.countdowntimer.model

import androidx.compose.ui.graphics.Color
import com.lianglliu.countdowntimer.ui.theme.bgDarkColorCenter
import com.lianglliu.countdowntimer.ui.theme.bgDarkColorEdge
import com.lianglliu.countdowntimer.ui.theme.bgLightColorCenter
import com.lianglliu.countdowntimer.ui.theme.bgLightColorEdge
import com.lianglliu.countdowntimer.ui.theme.darkTextColor
import com.lianglliu.countdowntimer.ui.theme.lightIconTintCoLor
import com.lianglliu.countdowntimer.ui.theme.lightTextColor

data class CountdownColor(
    val bgColorCenter: Color,
    val bgColorEdge: Color,
    val textColor: Color,
    val iconTintCoLor: Color,
)

private val DefaultCountdownColor =
    CountdownColor(
        bgLightColorCenter,
        bgLightColorEdge,
        lightTextColor,
        lightIconTintCoLor
    )

private val DarkCountdownColor =
    CountdownColor(
        bgDarkColorCenter,
        bgDarkColorEdge,
        darkTextColor,
        bgDarkColorEdge
    )

fun buildCountdownColor(appTheme: AppTheme): CountdownColor {
    if (appTheme == AppTheme.Light) {
        return DefaultCountdownColor
    }

    if (appTheme == AppTheme.Dark) {
        return DarkCountdownColor
    }

    return DefaultCountdownColor
}