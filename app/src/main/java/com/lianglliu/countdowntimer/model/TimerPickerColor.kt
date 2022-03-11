package com.lianglliu.countdowntimer.model

import androidx.compose.ui.graphics.Color
import com.lianglliu.countdowntimer.ui.theme.bgDarkColorCenter
import com.lianglliu.countdowntimer.ui.theme.bgDarkColorEdge
import com.lianglliu.countdowntimer.ui.theme.darkDividersColor
import com.lianglliu.countdowntimer.ui.theme.bgLightColorCenter
import com.lianglliu.countdowntimer.ui.theme.bgLightColorEdge
import com.lianglliu.countdowntimer.ui.theme.darkTextColor
import com.lianglliu.countdowntimer.ui.theme.lightDividersColor
import com.lianglliu.countdowntimer.ui.theme.lightIconTintCoLor
import com.lianglliu.countdowntimer.ui.theme.lightTextColor

data class TimerPickerColor(
    val bgColorCenter: Color,
    val bgColorEdge: Color,
    val textColor: Color,
    val dividersColor: Color,
    val iconTintCoLor: Color,
)

private val DefaultTimerPickerColor =
    TimerPickerColor(
        bgLightColorCenter,
        bgLightColorEdge,
        lightTextColor,
        lightDividersColor,
        lightIconTintCoLor
    )

private val DarkTimerPickerColor =
    TimerPickerColor(
        bgDarkColorCenter,
        bgDarkColorEdge,
        darkTextColor,
        darkDividersColor,
        bgLightColorEdge
    )

fun buildTimerPickerColor(appTheme: AppTheme): TimerPickerColor {
    if (appTheme == AppTheme.Light) {
        return DefaultTimerPickerColor
    }

    if (appTheme == AppTheme.Dark) {
        return DarkTimerPickerColor
    }

    return DefaultTimerPickerColor
}