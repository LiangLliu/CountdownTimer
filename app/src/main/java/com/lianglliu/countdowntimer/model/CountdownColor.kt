package com.lianglliu.countdowntimer.model

import androidx.compose.ui.graphics.Color
import com.lianglliu.countdowntimer.ui.theme.bgIconDarkCenter
import com.lianglliu.countdowntimer.ui.theme.bgIconLightCenter
import com.lianglliu.countdowntimer.ui.theme.darkIconTintCoLor
import com.lianglliu.countdowntimer.ui.theme.darkTextColor
import com.lianglliu.countdowntimer.ui.theme.darkTickEndColor
import com.lianglliu.countdowntimer.ui.theme.darkTickStartColor
import com.lianglliu.countdowntimer.ui.theme.lightIconTintCoLor
import com.lianglliu.countdowntimer.ui.theme.lightTextColor
import com.lianglliu.countdowntimer.ui.theme.lightTickEndColor
import com.lianglliu.countdowntimer.ui.theme.lightTickStartColor

data class CountdownColor(
    val bgIconColor: Color,
    val iconTintCoLor: Color,
    val textColor: Color,
    val tickStartColor: Color,
    val tickEndColor: Color,
    val brushColor: Color,
)

private val DefaultCountdownColor =
    CountdownColor(
        bgIconColor = bgIconLightCenter,
        iconTintCoLor = lightIconTintCoLor,
        textColor = lightTextColor,
        tickStartColor = lightTickStartColor,
        tickEndColor = lightTickEndColor,
        brushColor = bgIconLightCenter
    )

private val DarkCountdownColor =
    CountdownColor(
        bgIconColor = bgIconDarkCenter,
        iconTintCoLor = darkIconTintCoLor,
        textColor = darkTextColor,
        tickStartColor = darkTickStartColor,
        tickEndColor = darkTickEndColor,
        brushColor = bgIconDarkCenter
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