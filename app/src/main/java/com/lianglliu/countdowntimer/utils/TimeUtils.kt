package com.lianglliu.countdowntimer.utils

import kotlin.math.floor

fun Int.toHours(): Int {
    val toInt = floor(this.toDouble() / 60 / 60).toInt()
    if (toInt > 99) {
        return 99
    }
    return toInt
}

fun Int.toMinutes(): Int {
    if (floor(this.toDouble() / 60 / 60).toInt() > 99) {
        return 59
    }
    return floor((this % 3600).toDouble() / 60).toInt()
}

fun Int.toSeconds(): Int {
    if (floor(this.toDouble() / 60 / 60).toInt() > 99) {
        return 59
    }
    return this % 60
}

fun Int.toTimeHHMMSS(): String {
    return buildString {
        append("${toHours()}".padStart(2, '0'))
        append(":")
        append("${toMinutes()}".padStart(2, '0'))
        append(":")
        append("${toSeconds()}".padStart(2, '0'))
    }
}

fun Int.toTimeMMSS(): String {
    return buildString {
        append("${toMinutes()}".padStart(2, '0'))
        append(":")
        append("${toSeconds()}".padStart(2, '0'))
    }
}


