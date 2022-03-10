package com.lianglliu.countdowntimer.ui.countdown

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lianglliu.countdowntimer.Graph
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.sin

class CountdownViewModel : ViewModel() {

    private val _job = MutableStateFlow<Job?>(null)
    private var _endPosition = MutableStateFlow<Offset?>(null)
    private var _isDragging = MutableStateFlow(false)
    private var _totalSeconds = MutableStateFlow(10)

    val job: Job?
        get() = _job.value

    val totalSeconds: Int
        get() = _totalSeconds.value

    val seconds: Int
        get() = _totalSeconds.value % 60

    val minutes: Int
        get() = floor(_totalSeconds.value.toDouble() / 60).toInt()

    val hours: Int
        get() = floor(_totalSeconds.value.toDouble() / 60 / 60).toInt()

    val timeLeftDisplay: String
        get() {
            return buildString {
//                append("$hours".padStart(2, '0'))
//                append(":")
                append("$minutes".padStart(2, '0'))
                append(":")
                append("$seconds".padStart(2, '0'))
            }
        }


    fun endDrag() {
        val current = _endPosition.value
        if (current != null) {
            _isDragging.value = false
        } else {
            error("Position was null when it shouldn't have been")
        }
    }

    fun startDrag(startPosition: Offset) {
        _isDragging.value = true
        _endPosition.value = startPosition
        stop()
    }

    fun onDrag(delta: Offset) {
        val prev = _endPosition.value
        val prevSeconds = _totalSeconds.value
        val next = if (prev != null) {
            val prevTheta = prev.theta
            val next = prev + delta
            val nextTheta = next.theta
            val nextMinutes = when {
                prevTheta > 90f && nextTheta < -90f -> minutes + 1
                prevTheta < -90f && nextTheta > 90f -> max(0, minutes - 1)
                else -> minutes
            }
            val nextSeconds = floor((nextMinutes) * 60 + ((next.theta + 180f) / 360f * 60f)).toInt()
            if (nextSeconds != prevSeconds) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    getVibrator().vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK))
                }
            }
            _totalSeconds.value = nextSeconds
            next
        } else {
            delta
        }
        _endPosition.value = next
    }

    fun toggle() {
        if (job == null) {
            _job.value = viewModelScope.launch {
                while (_totalSeconds.value > 0) {
                    delay(1000)
                    countDown()
                }
                _endPosition.value = null
            }
        } else {
            stop()
        }
    }

    fun clear() {
        stop()
        _totalSeconds.value = 0
        _endPosition.value = null
    }

    fun stop() {
        job?.cancel()
        _job.value = null
    }

    fun countDown() {
        val next = _totalSeconds.value - 1
        val theta = (((next % 60) * 6 - 180) * PI / 180).toFloat()
        val radius = 100f
        _totalSeconds.value = next
        _endPosition.value = Offset(
            cos(theta) * radius,
            sin(theta) * radius
        )
    }
}

private fun getVibrator(): Vibrator {
    return Graph.appContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
}
