package com.shevelev.visualgrocerylist.shared.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

fun createColorScheme(colorSet: ThemeColorSet): ColorScheme =
    ColorScheme(
        primary = colorSet.primary,
        onPrimary = colorSet.onPrimary,
        primaryContainer = colorSet.containerPrimary,
        onPrimaryContainer = colorSet.onContainerPrimary,
        inversePrimary = colorSet.inversePrimary,
        secondary = colorSet.secondary,
        onSecondary = colorSet.onSecondary,
        secondaryContainer = colorSet.containerSecondary,
        onSecondaryContainer = colorSet.onContainerSecondary,
        tertiary = colorSet.tertiary,
        onTertiary = colorSet.onTertiary,
        tertiaryContainer = colorSet.containerTertiary,
        onTertiaryContainer = colorSet.onContainerTertiary,
        background = colorSet.background,
        onBackground = colorSet.onBackground,
        surface = colorSet.surface,
        onSurface = colorSet.onSurface,
        surfaceVariant = colorSet.surfaceVariant,
        onSurfaceVariant = colorSet.onSurfaceVariant,
        surfaceTint = colorSet.primary,
        inverseSurface = colorSet.surfaceInverse,
        inverseOnSurface = colorSet.inverseOnSurface,
        error = colorSet.error,
        onError = colorSet.onError,
        errorContainer = colorSet.containerError,
        onErrorContainer = colorSet.onContainerError,
        outline = colorSet.outline,
        outlineVariant = colorSet.outlineVariant,
        scrim = colorSet.scrim,
        surfaceBright = colorSet.surfaceBright,
        surfaceContainer = colorSet.surfaceContainer,
        surfaceContainerHigh = colorSet.surfaceContainerHigh,
        surfaceContainerHighest = colorSet.surfaceContainerHighest,
        surfaceContainerLow = colorSet.surfaceContainerLow,
        surfaceContainerLowest = colorSet.surfaceContainerLowest,
        surfaceDim = colorSet.surfaceDim,
    )

@Composable
fun VisualGroceryListTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> createColorScheme(ColorBlue.Dark)
        else -> createColorScheme(ColorBlue.Light)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}