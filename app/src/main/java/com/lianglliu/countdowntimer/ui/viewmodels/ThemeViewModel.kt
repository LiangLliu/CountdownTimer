package com.lianglliu.countdowntimer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lianglliu.countdowntimer.cache.AppThemeCache
import com.lianglliu.countdowntimer.model.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ThemeViewModel : ViewModel() {

    val appTheme = MutableStateFlow(AppThemeCache.currentTheme)

    init {
        viewModelScope.launch {
            appTheme.value = AppThemeCache.currentTheme
        }
    }

    fun switchToNextTheme() {
        val nextTheme = appTheme.value.nextTheme()
        AppThemeCache.onAppThemeChanged(nextTheme)
        appTheme.value = nextTheme
    }

    private fun switchToDark() {
        AppThemeCache.onAppThemeChanged(AppTheme.Dark)
        appTheme.value = AppTheme.Dark
    }

    private fun switchToLight() {
        AppThemeCache.onAppThemeChanged(AppTheme.Light)
        appTheme.value = AppTheme.Light
    }

    fun switchToggle() {
        if (appTheme.value == AppTheme.Light) {
            switchToDark()
        } else {
            switchToLight()
        }
    }
}