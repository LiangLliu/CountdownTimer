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
import com.lianglliu.countdowntimer.ui.components.CircleIcon
import com.lianglliu.countdowntimer.ui.components.picker.MinutesSecondsPicker
import com.lianglliu.countdowntimer.ui.viewmodels.SharedViewModel

@Composable
fun TimerPicker(
    navController: NavController,
    viewModel: SharedViewModel
) {

    val timerState by viewModel.timerState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.Center, // 指定垂直方向居中显示
        horizontalAlignment = Alignment.CenterHorizontally // 指定水平方向居中对齐
    )
    {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(top = 50.dp),
            text = "倒计时",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

        Box(
            modifier = Modifier
                .padding(20.dp)
                .weight(2f), contentAlignment = Alignment.Center
        ) {
            MinutesSecondsPicker(
                value = timerState,
                onValueChange = {
                    viewModel.updateTimer(it)
                },
            )
        }

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleIcon(Icons.Default.PlayArrow, onClick = {
                navController.navigate("countdown")
                viewModel.startTimer()
            })
        }
    }
}