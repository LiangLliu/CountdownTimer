package com.lianglliu.countdowntimer

import android.app.Application

class CountdownTimerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}