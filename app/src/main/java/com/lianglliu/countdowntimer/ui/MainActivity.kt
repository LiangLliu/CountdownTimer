package com.lianglliu.countdowntimer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lianglliu.countdowntimer.model.AppTheme
import com.lianglliu.countdowntimer.navigate.NavigateApp
import com.lianglliu.countdowntimer.ui.theme.CountdownTimerTheme
import com.lianglliu.countdowntimer.ui.viewmodels.SharedViewModel
import com.lianglliu.countdowntimer.ui.viewmodels.ThemeViewModel
import com.lianglliu.countdowntimer.ui.weigets.SetSystemBarsColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val appViewModel = viewModel<ThemeViewModel>()
            val appTheme by appViewModel.appTheme.collectAsState()

            val switchToggle = remember {
                {
                    appViewModel.switchToggle()
                }
            }

            CountdownTimerTheme(appTheme) {
                SetSystemBarsColor(
                    statusBarColor = Color.Transparent,
                    navigationBarColor = MaterialTheme.colorScheme.background,
                    statusBarDarkIcons = appTheme == AppTheme.Light || appTheme == AppTheme.Gray,
                    navigationDarkIcons = appTheme != AppTheme.Dark
                )

                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.padding(top = 30.dp, bottom = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(top = 10.dp, end = 20.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "深色模式",
                                color = if (appTheme == AppTheme.Dark) Color.White else Color.Black
                            )
                            Switch(
                                checked = appTheme == AppTheme.Dark,
                                onCheckedChange = { switchToggle() }
                            )
                        }

                        NavigateApp(
                            appTheme = appTheme,
                            sharedViewModel = sharedViewModel,
                        )
                    }
                }

                if (appTheme == AppTheme.Gray) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawRect(
                            color = Color.LightGray,
                            blendMode = BlendMode.Saturation
                        )
                    }
                }
            }
        }
    }

    private val sharedViewModel: SharedViewModel by viewModels()
}



