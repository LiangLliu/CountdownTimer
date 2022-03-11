package com.lianglliu.countdowntimer.ui.countdown

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    val countdownColor = buildCountdownColor(appTheme)

    Box(
        Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            ),
        contentAlignment = Alignment.Center
    ) {
        TickWheel(
            totalSeconds = viewState.totalSeconds
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

        AnimatedVisibility(
            true,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
        ) {
            if (viewState.totalSeconds > 0) {
                Row {
                    CircleIcon(
                        imageVector = Icons.Default.Stop,
                        onClick = {
                            viewModel.clear()
                            navController.navigate("timerPicker")
                        },
                        bgCoLor = countdownColor.bgColorCenter,
                        iconTintCoLor = countdownColor.iconTintCoLor
                    )
                    if (viewModel.isPause()) {
                        CircleIcon(
                            imageVector = Icons.Default.PlayArrow,
                            onClick = { viewModel.toggle() },
                            bgCoLor = countdownColor.bgColorCenter,
                            iconTintCoLor = countdownColor.iconTintCoLor
                        )
                    } else {
                        CircleIcon(
                            imageVector = Icons.Default.Pause,
                            onClick = { viewModel.toggle() },
                            bgCoLor = countdownColor.bgColorCenter,
                            iconTintCoLor = countdownColor.iconTintCoLor
                        )
                    }
                }
            } else {
                CircleIcon(
                    imageVector = Icons.Default.Stop,
                    onClick = {
                        viewModel.clear()
                        navController.navigate("timerPicker")
                    },
                    bgCoLor = countdownColor.bgColorCenter,
                    iconTintCoLor = countdownColor.iconTintCoLor
                )
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

