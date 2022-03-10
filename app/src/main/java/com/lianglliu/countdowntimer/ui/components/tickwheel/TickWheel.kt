package com.lianglliu.countdowntimer.ui.components.tickwheel

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.lerp
import com.lianglliu.countdowntimer.ui.theme.darkRed
import com.lianglliu.countdowntimer.ui.theme.lightOrange
import com.lianglliu.countdowntimer.utils.center
import com.lianglliu.countdowntimer.utils.drawTick
import com.lianglliu.countdowntimer.utils.sweepGradient
import com.lianglliu.countdowntimer.utils.toBrush
import com.lianglliu.countdowntimer.utils.toMinutes
import com.lianglliu.countdowntimer.utils.toSeconds
import kotlin.math.PI
import kotlin.math.abs

const val RadiusA = 0.36f
const val RadiusB = 0.40f
const val RadiusC = 0.48f
const val RadiusD = 0.75f
const val RadiusE = 1.4f

@Composable
fun TickWheel(
    ticks: Int = 60,
    startColor: Color = lightOrange,
    endColor: Color = darkRed,
    totalSeconds: Int,
    content: @Composable () -> Unit
) {
    val minutes = totalSeconds.toMinutes()
    val seconds = totalSeconds.toSeconds()

    val minuteTransition by animateFloatAsState(minutes.toFloat())
    Box(
        Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .drawWithCache {
                val unitRadius = size.width / 1.7f
                val a = unitRadius * RadiusA
                val b = unitRadius * RadiusB
                val c = unitRadius * RadiusC
                val d = unitRadius * RadiusD
                val e = unitRadius * RadiusE

                val offBrush = Color.White
                    .copy(alpha = 0.1f)
                    .toBrush()

                val sweep = Brush.sweepGradient(
                    startColor,
                    endColor,
                    center,
                    // use a little over 180deg so that the first "tick" mark isn't split down the middle
                    -182f
                )
                onDrawBehind {
                    val endAngle = seconds * 6 - 180
//                    val minutes = minutes
                    for (i in 0 until ticks) {
                        val angle = i * (360 / ticks) - 180 // -180 to 180
                        val theta = angle * PI.toFloat() / 180f // radians
                        val onBrush = if (angle < endAngle) sweep else offBrush
                        val up = minutes >= minuteTransition
                        val t = 1 - abs(minutes - minuteTransition)
                        if (up) {
                            if (minutes > 1) {
                                drawTick(sweep, theta, lerp(b, a, t), lerp(c, b, t), 1 - t)
                            }
                            if (minutes > 0) {
                                drawTick(sweep, theta, lerp(c, b, t), lerp(d, c, t), 1f)
                            }
                            drawTick(onBrush, theta, lerp(d, c, t), lerp(e, d, t), t)
                        } else {
                            if (minutes > 0) {
                                drawTick(sweep, theta, lerp(a, b, t), lerp(b, c, t), t)
                            }
                            drawTick(onBrush, theta, lerp(b, c, t), lerp(c, d, t), 1f)
                            drawTick(offBrush, theta, lerp(c, d, t), lerp(d, e, t), 1 - t)
                        }
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}


