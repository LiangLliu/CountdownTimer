package com.lianglliu.countdowntimer.ui.countdown

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lianglliu.countdowntimer.model.AppTheme
import com.lianglliu.countdowntimer.model.buildCountdownColor
import com.lianglliu.countdowntimer.ui.components.CircleIcon
import com.lianglliu.countdowntimer.ui.components.tickwheel.TickWheel
import com.lianglliu.countdowntimer.ui.viewmodels.SharedViewModel
import com.lianglliu.countdowntimer.utils.toHours
import com.lianglliu.countdowntimer.utils.toMinutes
import com.lianglliu.countdowntimer.utils.toSeconds
import com.lianglliu.countdowntimer.utils.toTimeHHMMSS


@Composable
fun Countdown(
    navController: NavController,
    viewModel: SharedViewModel,
    appTheme: AppTheme
) {
    val viewState by viewModel.countdownState.collectAsState()
    val seconds by rememberSaveable { mutableStateOf(viewState.totalSeconds) }
    var isNotification by remember { mutableStateOf(false) }
    val countdownColor = buildCountdownColor(appTheme)
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp),
        verticalArrangement = Arrangement.Center, // 指定垂直方向居中显示
        horizontalAlignment = Alignment.CenterHorizontally // 指定水平方向居中对齐
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = "计时中",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = countdownColor.textColor
        )

        Box(
            Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp)
                .weight(3f),
            contentAlignment = Alignment.Center
        )
        {
            TickWheel(
                totalSeconds = viewState.totalSeconds,
                tickStartColor = countdownColor.tickStartColor,
                tickEndColor = countdownColor.tickEndColor,
                brushColor = countdownColor.brushColor
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = viewState.totalSeconds.toTimeHHMMSS(),
                        color = countdownColor.textColor,
                        fontSize = 38.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier.padding(top = 5.dp),
                        text = buildTimeHintText(seconds),
                        color = countdownColor.textColor,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (viewState.totalSeconds > 0) {

                CircleIcon(
                    imageVector = Icons.Default.Stop,
                    onClick = {
                        viewModel.clear()
                        navController.navigate("timerPicker")
                    },
                    bgCoLor = countdownColor.bgIconColor,
                    iconTintCoLor = countdownColor.iconTintCoLor
                )
                if (viewModel.isPause()) {
                    CircleIcon(
                        imageVector = Icons.Default.PlayArrow,
                        onClick = { viewModel.toggle() },
                        bgCoLor = countdownColor.bgIconColor,
                        iconTintCoLor = countdownColor.iconTintCoLor
                    )
                } else {
                    CircleIcon(
                        imageVector = Icons.Default.Pause,
                        onClick = { viewModel.toggle() },
                        bgCoLor = countdownColor.bgIconColor,
                        iconTintCoLor = countdownColor.iconTintCoLor
                    )
                }

            } else {
                if (!isNotification) {

                    viewModel.notification(seconds)

                    isNotification = true
                    viewModel.clear()
                    navController.navigate("timerPicker")
                }
            }
        }
    }
}

fun buildTimeHintText(totalSeconds: Int): String {
    val hours = totalSeconds.toHours()
    val minutes = totalSeconds.toMinutes()
    val seconds = totalSeconds.toSeconds()
    if (hours == 0 && minutes != 0) {
        return "共${minutes}分${seconds}秒"
    }
    if (minutes == 0) {
        return "共${seconds}秒"
    }
    return "共${hours}时${minutes}分${seconds}秒"
}

