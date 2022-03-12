package com.lianglliu.countdowntimer.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

import androidx.compose.runtime.Composable
import com.lianglliu.countdowntimer.cache.AppThemeCache
import com.lianglliu.countdowntimer.model.AppTheme

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColorLight,
    onPrimary = OnPrimaryColorLight,
    primaryContainer = PrimaryContainerColorLight,
    surface = SurfaceColorLight,
    onSurface = OnSurfaceColorLight,
    background = BackgroundColorLight,
    onSecondaryContainer = onSecondaryContainerLight
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColorDark,
    secondary = BackgroundColorDark,
    onPrimary = OnPrimaryColorDark,
    primaryContainer = PrimaryContainerColorDark,
    surface = SurfaceColorDark,
    onSurface = OnSurfaceColorDark,
    background = BackgroundColorDark,
    onSecondaryContainer = onSecondaryContainerDark
)

@Composable
fun CountdownTimerTheme(
    appTheme: AppTheme = AppThemeCache.currentTheme,
    content: @Composable () -> Unit
) {
    val colors: ColorScheme
    val typography: Typography
    when (appTheme) {
        AppTheme.Light -> {
            colors = LightColorScheme
            typography = LightTypography
        }
        AppTheme.Dark -> {
            colors = DarkColorScheme
            typography = LightTypography
        }
        AppTheme.Gray -> {
            colors = LightColorScheme
            typography = LightTypography
        }
    }

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        content = content
    )
}

