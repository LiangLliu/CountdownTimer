package com.lianglliu.countdowntimer.cache

import android.content.Context
import com.lianglliu.countdowntimer.Graph
import com.lianglliu.countdowntimer.model.AppTheme

object AppThemeCache {

    private const val KEY_GROUP = "AppThemeGroup"
    private const val KEY_APP_THEME = "keyAppTheme"

    private val DefaultAppTheme = AppTheme.Light

    private val preferences by lazy {
        Graph.appContext.getSharedPreferences(KEY_GROUP, Context.MODE_PRIVATE)
    }

    var currentTheme = DefaultAppTheme
        private set

    fun init() {
        currentTheme = getAppTheme()
    }

    private fun getAppTheme(): AppTheme {
        val themeType = preferences.getInt(KEY_APP_THEME, DefaultAppTheme.type)
        return AppTheme.values().find { it.type == themeType } ?: DefaultAppTheme
    }

    fun onAppThemeChanged(appTheme: AppTheme) {
        preferences.edit().putInt(KEY_APP_THEME, appTheme.type).apply()
        currentTheme = appTheme
    }
}