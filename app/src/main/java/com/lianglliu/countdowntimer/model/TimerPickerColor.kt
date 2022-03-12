package com.lianglliu.countdowntimer.model

import androidx.compose.ui.graphics.Color
import com.lianglliu.countdowntimer.ui.theme.bgIconDarkCenter
import com.lianglliu.countdowntimer.ui.theme.bgIconLightCenter
import com.lianglliu.countdowntimer.ui.theme.pickerDarkDividersColor
import com.lianglliu.countdowntimer.ui.theme.darkIconTintCoLor
import com.lianglliu.countdowntimer.ui.theme.darkTextColor
import com.lianglliu.countdowntimer.ui.theme.lightPrickerDividersColor
import com.lianglliu.countdowntimer.ui.theme.lightIconTintCoLor
import com.lianglliu.countdowntimer.ui.theme.lightTextColor

data class TimerPickerColor(
    val bgIconColor: Color,
    val iconTintCoLor: Color,
    val textColor: Color,
    val pickerDividersColor: Color,
)

private val DefaultTimerPickerColor =
    TimerPickerColor(
        bgIconColor = bgIconLightCenter,
        iconTintCoLor = lightIconTintCoLor,
        textColor = lightTextColor,
        pickerDividersColor = lightPrickerDividersColor,
    )

private val DarkTimerPickerColor =
    TimerPickerColor(
        bgIconColor = bgIconDarkCenter,
        iconTintCoLor = darkIconTintCoLor,
        textColor = darkTextColor,
        pickerDividersColor = pickerDarkDividersColor,
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