package com.lianglliu.countdowntimer.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lianglliu.countdowntimer.Graph.timerDataRepository
import com.lianglliu.countdowntimer.data.entity.TimerData
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
    private val _state = MutableStateFlow(CountdownViewState())
    val state: StateFlow<CountdownViewState>
        get() = _state

    private val _timerState = MutableStateFlow(TimerViewState())

    val timerState: StateFlow<TimerViewState>
        get() = _timerState

    fun isPause(): Boolean {
        return job.value == null
    }

    private val job = mutableStateOf<Job?>(null)

    init {
        loadTimerViewState()
    }

    fun updateTimer(timerViewState: TimerViewState) {
        _timerState.value = timerViewState
    }

    fun startTimer() {
        _state.value = CountdownViewState(_timerState.value.toDuration())
        toggle()
        saveTimerState()
    }

    fun toggle() {
        if (job.value == null) {
            job.value = viewModelScope.launch {
                while (_state.value.totalSeconds > 0) {
                    delay(1000)
                    _state.value = CountdownViewState(state.value.totalSeconds - 1)
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
                    _state.value = CountdownViewState(timerData.value)
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