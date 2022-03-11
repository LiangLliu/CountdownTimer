package com.lianglliu.countdowntimer

import android.app.Application
import com.lianglliu.countdowntimer.cache.AppThemeCache

class CountdownTimerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
        AppThemeCache.init()
    }
}