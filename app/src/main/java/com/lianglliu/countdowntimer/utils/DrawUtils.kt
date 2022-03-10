package com.lianglliu.countdowntimer.utils

import android.graphics.Matrix
import android.graphics.SweepGradient
import androidx.compose.ui.draw.CacheDrawScope
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.toArgb
import kotlin.math.cos
import kotlin.math.sin

const val TickWidth = 9f
const val Epsilon = 9f

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

val CacheDrawScope.center: Offset get() = Offset(size.width / 2, size.height / 2)


fun Brush.Companion.sweepGradient(
    startColor: Color,
    endColor: Color,
    center: Offset,
    startAngle: Float
): Brush {
    val matrix = Matrix().also { it.setRotate(startAngle, center.x, center.y) }
    return ShaderBrush(
        SweepGradient(
            center.x,
            center.y,
            startColor.toArgb(),
            endColor.toArgb()
        )
            .also {
                it.setLocalMatrix(matrix)
            })
}

fun Color.toBrush(): Brush = SolidColor(this)
