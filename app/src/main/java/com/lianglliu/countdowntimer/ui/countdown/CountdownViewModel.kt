package com.lianglliu.countdowntimer.ui.countdown

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class CountdownViewModel : ViewModel() {

    private val _state = MutableStateFlow(CountdownViewState(totalSeconds = 125))

    val state: StateFlow<CountdownViewState>
        get() = _state

    fun isPause(): Boolean {
        return job.value == null
    }

    private val job = mutableStateOf<Job?>(null)
    private val endPosition = mutableStateOf<Offset?>(null)

    fun toggle() {
        if (job.value == null) {
            job.value = viewModelScope.launch {
                while (_state.value.totalSeconds > 0) {
                    delay(1000)
                    countDown()
                }
                endPosition.value = null
            }
        } else {
            pause()
        }
    }

    fun clear() {
        pause()
        viewModelScope.launch {
            _state.value = CountdownViewState(0)
            endPosition.value = null
        }
    }

    fun pause() {
        viewModelScope.launch {
            job.value?.cancel()
            job.value = null
        }
    }

    fun countDown() {
        viewModelScope.launch {
            _state.value = CountdownViewState(state.value.totalSeconds - 1)
            endPosition.value = nextEndPosition(state.value.totalSeconds - 1)
        }
    }
}

private fun nextEndPosition(next: Int): Offset {
    val theta = (((next % 60) * 6 - 180) * PI / 180).toFloat()
    val radius = 100f
    return Offset(
        cos(theta) * radius,
        sin(theta) * radius
    )
}

data class CountdownViewState(
    val totalSeconds: Int = 0
)
