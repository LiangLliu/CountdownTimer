package com.lianglliu.countdowntimer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.lianglliu.countdowntimer.navigate.NavigateApp
import com.lianglliu.countdowntimer.ui.theme.CountdownTimerTheme
import com.lianglliu.countdowntimer.ui.viewmodels.SharedViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountdownTimerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavigateApp(sharedViewModel = sharedViewModel)
                }
            }
        }
    }

    private val sharedViewModel: SharedViewModel by viewModels()
}



