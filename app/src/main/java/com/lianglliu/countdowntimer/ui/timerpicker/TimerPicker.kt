package com.lianglliu.countdowntimer.ui.timerpicker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lianglliu.countdowntimer.model.AppTheme
import com.lianglliu.countdowntimer.model.buildTimerPickerColor
import com.lianglliu.countdowntimer.ui.components.CircleIcon
import com.lianglliu.countdowntimer.ui.components.picker.MinutesSecondsPicker
import com.lianglliu.countdowntimer.ui.viewmodels.SharedViewModel

@Composable
fun TimerPicker(
    navController: NavController,
    viewModel: SharedViewModel,
    appTheme: AppTheme
) {
    val timerState by viewModel.timerState.collectAsState()
    val timerPickerColor = buildTimerPickerColor(appTheme)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp),
        verticalArrangement = Arrangement.Center, // 指定垂直方向居中显示
        horizontalAlignment = Alignment.CenterHorizontally // 指定水平方向居中对齐
    )
    {
        Text(
            modifier = Modifier
                .weight(1f),
            text = "倒计时",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = timerPickerColor.textColor
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 50.dp)
                .weight(3f),
            contentAlignment = Alignment.Center
        ) {
            MinutesSecondsPicker(
                value = timerState,
                onValueChange = {
                    viewModel.updateTimer(it)
                },
                dividersColor = timerPickerColor.pickerDividersColor,
                textColor = timerPickerColor.textColor,
            )
        }

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleIcon(
                imageVector = Icons.Default.PlayArrow,
                onClick = {
                    navController.navigate("countdown")
                    viewModel.startTimer()
                },
                bgCoLor = timerPickerColor.bgIconColor,
                iconTintCoLor = timerPickerColor.iconTintCoLor
            )
        }
    }
}
