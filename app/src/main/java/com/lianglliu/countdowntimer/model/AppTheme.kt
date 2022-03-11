package com.lianglliu.countdowntimer.model

enum class AppTheme(val type: Int) {

    Light(0), Dark(1), Gray(2);

    fun nextTheme(): AppTheme {
        val values = values()
        return values.getOrElse(type + 1) {
            values[0]
        }
    }
}