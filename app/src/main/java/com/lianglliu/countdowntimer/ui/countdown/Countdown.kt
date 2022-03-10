package com.lianglliu.countdowntimer.ui.countdown

import android.graphics.Matrix
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.CacheDrawScope
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lianglliu.countdowntimer.ui.components.CircleIcon
import com.lianglliu.countdowntimer.ui.theme.bgColorCenter
import com.lianglliu.countdowntimer.ui.theme.bgColorEdge
import com.lianglliu.countdowntimer.ui.theme.darkRed
import com.lianglliu.countdowntimer.ui.theme.lightOrange
import kotlin.math.*

@Composable
fun Countdown(
    viewModel: CountdownViewModel = viewModel(),
) {

    Box(
        Modifier
            .fillMaxSize()
            .background(Brush.radialGradient(listOf(bgColorCenter, bgColorEdge))),
        contentAlignment = Alignment.Center
    ) {
        TickWheel(
            Modifier.fillMaxWidth(),
            viewModel = viewModel,
            ticks = 60,
            startColor = lightOrange,
            endColor = darkRed,
        ) {
            Text(
                viewModel.timeLeftDisplay,
                color = Color.White,
                fontSize = 48.sp,
                textAlign = TextAlign.Center
            )
        }
        AnimatedVisibility(
            viewModel.totalSeconds > 0,
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
        ) {
            Row {
//                IconButton(onClick = { state.toggle() }) {
//                    Icon(
//                        if (state.isCountingDown) Icons.Default.Pause else Icons.Default.PlayArrow,
//                        contentDescription = if (state.isCountingDown) "Pause" else "Start",
//                    )
//                }
//                IconButton(onClick = { state.clear() }) {
//                    Icon(Icons.Default.Clear, contentDescription = "Clear")
//                }

                CircleIcon(Icons.Default.Stop, onClick = { viewModel.clear() })

                CircleIcon(Icons.Default.Pause, onClick = { viewModel.toggle() })
            }
        }
    }
}


const val TickWidth = 9f
const val Epsilon = 9f
const val RadiusA = 0.36f
const val RadiusB = 0.40f
const val RadiusC = 0.48f
const val RadiusD = 0.75f
const val RadiusE = 1.4f

@Composable
fun TickWheel(
    modifier: Modifier,
    ticks: Int,
    startColor: Color,
    endColor: Color,
    viewModel: CountdownViewModel,
    content: @Composable () -> Unit
) {
    var origin by remember { mutableStateOf(Offset.Zero) }
    val minuteTransition by animateFloatAsState(viewModel.minutes.toFloat())
    Box(
        modifier
            .aspectRatio(1f)
            .onSizeChanged { origin = it.center.toOffset() }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        viewModel.startDrag(offset - origin)
                    },
                    onDragEnd = {
                        viewModel.endDrag()
                    },
                    onDragCancel = {
                        viewModel.endDrag()
                    },
                    onDrag = { change, amount ->
                        viewModel.onDrag(amount)
                        change.consumeAllChanges()
                    }
                )
            }
            .drawWithCache {
                val unitRadius = size.width / 2f
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
                    val endAngle = viewModel.seconds * 6 - 180
                    val minutes = viewModel.minutes
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

fun DrawScope.drawTick(
    brush: Brush,
    theta: Float,
    startRadius: Float,
    endRadius: Float,
    alpha: Float
) {
    drawLine(
        brush,
        center + Offset(
            cos(theta) * (startRadius + Epsilon),
            sin(theta) * (startRadius + Epsilon)
        ),
        center + Offset(
            cos(theta) * (endRadius - Epsilon),
            sin(theta) * (endRadius - Epsilon)
        ),
        TickWidth,
        StrokeCap.Round,
        alpha = alpha.coerceIn(0f, 1f)
    )
}

val Offset.theta: Float get() = (atan2(y.toDouble(), x.toDouble()) * 180.0 / PI).toFloat()

fun Color.toBrush(): Brush = SolidColor(this)

fun Brush.Companion.sweepGradient(
    startColor: Color,
    endColor: Color,
    center: Offset,
    startAngle: Float
): Brush {
    val matrix = Matrix().also { it.setRotate(startAngle, center.x, center.y) }
    return ShaderBrush(android.graphics.SweepGradient(
        center.x,
        center.y,
        startColor.toArgb(),
        endColor.toArgb()
    )
        .also {
            it.setLocalMatrix(matrix)
        })
}

val CacheDrawScope.center: Offset get() = Offset(size.width / 2, size.height / 2)

