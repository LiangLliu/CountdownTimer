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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lianglliu.countdowntimer.ui.components.CircleIcon
import com.lianglliu.countdowntimer.ui.components.tickwheel.TickWheel
import com.lianglliu.countdowntimer.ui.theme.bgColorCenter
import com.lianglliu.countdowntimer.ui.theme.bgColorEdge
import com.lianglliu.countdowntimer.ui.viewmodels.SharedViewModel
import com.lianglliu.countdowntimer.utils.toHours
import com.lianglliu.countdowntimer.utils.toMinutes
import com.lianglliu.countdowntimer.utils.toSeconds
import com.lianglliu.countdowntimer.utils.toTimeHHMMSS

@Composable
fun Countdown(
    navController: NavController,
    viewModel: SharedViewModel
) {
    val viewState by viewModel.state.collectAsState()
    val seconds by rememberSaveable { mutableStateOf(viewState.totalSeconds) }

    Box(
        Modifier
            .fillMaxSize()
            .background(Brush.radialGradient(listOf(bgColorCenter, bgColorEdge))),
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
                    color = Color.White,
                    fontSize = 38.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = buildTimeHintText(seconds),
                    color = Color.White,
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
                    CircleIcon(Icons.Default.Stop, onClick = {
                        viewModel.clear()
                        navController.navigate("timerPicker")
                    })
                    if (viewModel.isPause()) {
                        CircleIcon(Icons.Default.PlayArrow, onClick = { viewModel.toggle() })
                    } else {
                        CircleIcon(Icons.Default.Pause, onClick = { viewModel.toggle() })
                    }
                }
            } else {
                CircleIcon(Icons.Default.Stop, onClick = {
                    viewModel.clear()
                    navController.navigate("timerPicker")
                })
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

