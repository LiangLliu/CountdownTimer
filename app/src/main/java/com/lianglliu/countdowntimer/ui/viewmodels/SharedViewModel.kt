package com.lianglliu.countdowntimer.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lianglliu.countdowntimer.Graph
import com.lianglliu.countdowntimer.Graph.timerDataRepository
import com.lianglliu.countdowntimer.R
import com.lianglliu.countdowntimer.data.entity.TimerData
import com.lianglliu.countdowntimer.ui.countdown.buildTimeHintText
import com.lianglliu.countdowntimer.utils.createNotificationChannel
import com.lianglliu.countdowntimer.utils.foregroundStartService
import com.lianglliu.countdowntimer.utils.setOneTimeNotification
import com.lianglliu.countdowntimer.utils.toHours
import com.lianglliu.countdowntimer.utils.toMinutes
import com.lianglliu.countdowntimer.utils.toSeconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    private val _countdownState = MutableStateFlow(CountdownViewState())
    val countdownState: StateFlow<CountdownViewState>
        get() = _countdownState

    private val _timerState = MutableStateFlow(TimerViewState())

    val timerState: StateFlow<TimerViewState>
        get() = _timerState

    fun isPause(): Boolean {
        return job.value == null
    }

    fun notification(totalSeconds: Int) {
//        countdownNotification(totalSeconds)
//        Graph.appContext.foregroundStartService("倒计时${buildTimeHintText(totalSeconds)}")
        Graph.appContext.foregroundStartService("Start")
    }

    private val job = mutableStateOf<Job?>(null)

    init {
        createNotificationChannel(context = Graph.appContext)
        setOneTimeNotification()

        loadTimerViewState()
    }

    fun updateTimer(timerViewState: TimerViewState) {
        _timerState.value = timerViewState
    }

    fun startTimer() {
        _countdownState.value = CountdownViewState(_timerState.value.toDuration())
        toggle()
        saveTimerState()
    }

    fun toggle() {
        if (job.value == null) {
            job.value = viewModelScope.launch {
                while (_countdownState.value.totalSeconds > 0) {
                    delay(1000)
                    _countdownState.value =
                        CountdownViewState(countdownState.value.totalSeconds - 1)
                }
            }
        } else {
            stop()
        }
    }

    fun clear() {
        stop()
    }

    private fun stop() {
        viewModelScope.launch {
            job.value?.cancel()
            job.value = null
        }
    }

    private fun saveTimerState() {
        viewModelScope
            .launch(Dispatchers.IO) {
                val timerData = TimerData(name = "DURATION", value = _timerState.value.toDuration())
                timerDataRepository.insert(timerData)
            }
    }

    private fun loadTimerViewState() {
        viewModelScope
            .launch(Dispatchers.IO) {
                val timerData = timerDataRepository.findByName("DURATION")
                if (timerData != null) {
                    val duration = timerData.value
                    _timerState.value = TimerViewState(
                        hours = duration.toHours(),
                        minutes = duration.toMinutes(),
                        seconds = duration.toSeconds()
                    )
                    _countdownState.value = CountdownViewState(timerData.value)
                }
            }
    }
}

data class CountdownViewState(
    val totalSeconds: Int = 0
)

data class TimerViewState(
    val hours: Int = 0,
    val minutes: Int = 0,
    val seconds: Int = 0
) {
    fun toDuration(): Int {
        return hours * 3600 + minutes * 60 + seconds
    }
}

private fun countdownNotification(totalSeconds: Int) {
    val notificationId = 2
    val notificationCompat = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("计时结束")
        .setContentText("倒计时${buildTimeHintText(totalSeconds)}")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    with(NotificationManagerCompat.from(Graph.appContext)) {
        notify(notificationId, notificationCompat.build())

    }
}