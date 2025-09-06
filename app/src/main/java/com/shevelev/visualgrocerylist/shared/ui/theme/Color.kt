package com.shevelev.visualgrocerylist.shared.ui.theme

import androidx.compose.ui.graphics.Color

interface ThemeColorSet {
    val primary: Color
    val secondary: Color
    val tertiary: Color
    val error: Color
    val onPrimary: Color
    val onSecondary: Color
    val onTertiary: Color
    val onError: Color
    val containerPrimary: Color
    val containerSecondary: Color
    val containerTertiary: Color
    val containerError: Color
    val onContainerPrimary: Color
    val onContainerSecondary: Color
    val onContainerTertiary: Color
    val onContainerError: Color
    val surfaceDim: Color
    val surface: Color
    val surfaceVariant: Color
    val surfaceBright: Color
    val surfaceInverse: Color
    val surfaceContainerLowest: Color
    val surfaceContainerLow: Color
    val surfaceContainer: Color
    val surfaceContainerHigh: Color
    val surfaceContainerHighest: Color
    val inverseOnSurface: Color
    val inversePrimary: Color
    val onSurface: Color
    val onSurfaceVariant: Color
    val outline: Color
    val outlineVariant: Color
    val scrim: Color
    val shadow: Color
    val background: Color
    val onBackground: Color
}

/**
 * From here: https://material-foundation.github.io/material-theme-builder/
 */
object ColorRed {
    object Light : ThemeColorSet {
        override val primary = Color(0xFF8f4c38)
        override val secondary = Color(0xFF77574e)
        override val tertiary = Color(0xFF6c5d2f)
        override val error = Color(0xFFba1a1a)

        override val onPrimary = Color(0xFFffffff)
        override val onSecondary = Color(0xFFffffff)
        override val onTertiary = Color(0xFFffffff)
        override val onError = Color(0xFFffffff)

        override val containerPrimary = Color(0xFFffdbd1)
        override val containerSecondary = Color(0xFFffdbd1)
        override val containerTertiary = Color(0xFFf5e1a7)
        override val containerError = Color(0xFFffdad6)

        override val onContainerPrimary = Color(0xFF723523)
        override val onContainerSecondary = Color(0xFF5d4037)
        override val onContainerTertiary = Color(0xFF534619)
        override val onContainerError = Color(0xFF93000a)

        override val surfaceDim = Color(0xFFe8d6d2)
        override val surface = Color(0xFFfff8f6)
        override val surfaceVariant = Color(0xFFf1dfd9)
        override val surfaceBright = Color(0xFFfff8f6)
        override val surfaceInverse = Color(0xFF392e2b)

        override val surfaceContainerLowest = Color(0xFFffffff)
        override val surfaceContainerLow = Color(0xFFfff1ed)
        override val surfaceContainer = Color(0xFFfceae5)
        override val surfaceContainerHigh = Color(0xFFf7e4e0)
        override val surfaceContainerHighest = Color(0xFFf1dfda)

        override val inverseOnSurface = Color(0xFFffede8)
        override val inversePrimary = Color(0xFFffb5a0)

        override val onSurface = Color(0xFF231917)
        override val onSurfaceVariant = Color(0xFF53433f)

        override val outline = Color(0xFF85736e)
        override val outlineVariant = Color(0xFFd8c2bc)

        override val scrim = Color(0xFF000000)
        override val shadow = Color(0xFF000000)

        override val background = Color(0xFFfff8f6)
        override val onBackground = Color(0xFF231917)
    }

    object Dark : ThemeColorSet {
        override val primary = Color(0xFFffb5a0)
        override val secondary = Color(0xFFe7bdb2)
        override val tertiary = Color(0xFFd8c58d)
        override val error = Color(0xFFffb4ab)

        override val onPrimary = Color(0xFF561f0f)
        override val onSecondary = Color(0xFF442a22)
        override val onTertiary = Color(0xFF3b2f05)
        override val onError = Color(0xFF690005)

        override val containerPrimary = Color(0xFF723523)
        override val containerSecondary = Color(0xFF5d4037)
        override val containerTertiary = Color(0xFF534619)
        override val containerError = Color(0xFF93000a)

        override val onContainerPrimary = Color(0xFFffdbd1)
        override val onContainerSecondary = Color(0xFFffdbd1)
        override val onContainerTertiary = Color(0xFFf5e1a7)
        override val onContainerError = Color(0xFFffdad6)

        override val surfaceDim = Color(0xFF1a110f)
        override val surface = Color(0xFF1a110f)
        override val surfaceVariant = Color(0xFF504340)
        override val surfaceBright = Color(0xFF423734)
        override val surfaceInverse = Color(0xFFf1dfda)

        override val surfaceContainerLowest = Color(0xFF140c0a)
        override val surfaceContainerLow = Color(0xFF231917)
        override val surfaceContainer = Color(0xFF271d1b)
        override val surfaceContainerHigh = Color(0xFF322825)
        override val surfaceContainerHighest = Color(0xFF3d322f)

        override val inverseOnSurface = Color(0xFF392e2b)
        override val inversePrimary = Color(0xFF8f4c38)

        override val onSurface = Color(0xFFf1dfda)
        override val onSurfaceVariant = Color(0xFFd8c2bc)

        override val outline = Color(0xFFa08c87)
        override val outlineVariant = Color(0xFF53433f)

        override val scrim = Color(0xFF000000)
        override val shadow = Color(0xFF000000)

        override val background = Color(0xFF1a110f)
        override val onBackground = Color(0xFFf1dfda)
    }
}

object ColorGreen {
    object Light : ThemeColorSet {
        override val primary = Color(0xFF4c662b)
        override val secondary = Color(0xFF586249)
        override val tertiary = Color(0xFF386663)
        override val error = Color(0xFFba1a1a)

        override val onPrimary = Color(0xFFffffff)
        override val onSecondary = Color(0xFFffffff)
        override val onTertiary = Color(0xFFffffff)
        override val onError = Color(0xFFffffff)

        override val containerPrimary = Color(0xFFcdeda3)
        override val containerSecondary = Color(0xFFdce7c8)
        override val containerTertiary = Color(0xFFbcece7)
        override val containerError = Color(0xFFffdad6)

        override val onContainerPrimary = Color(0xFF354e16)
        override val onContainerSecondary = Color(0xFF404a33)
        override val onContainerTertiary = Color(0xFF1f4e4b)
        override val onContainerError = Color(0xFF93000a)

        override val surfaceDim = Color(0xFFdadbd0)
        override val surface = Color(0xFFf9faef)
        override val surfaceVariant = Color(0xFFe1e4d7)
        override val surfaceBright = Color(0xFFf9faef)
        override val surfaceInverse = Color(0xFF2f312a)

        override val surfaceContainerLowest = Color(0xFFffffff)
        override val surfaceContainerLow = Color(0xFFf3f4e9)
        override val surfaceContainer = Color(0xFFeeefe3)
        override val surfaceContainerHigh = Color(0xFFe8e9de)
        override val surfaceContainerHighest = Color(0xFFe2e3d8)

        override val inverseOnSurface = Color(0xFFf1f2e6)
        override val inversePrimary = Color(0xFFb1d18a)

        override val onSurface = Color(0xFF1a1c16)
        override val onSurfaceVariant = Color(0xFF44483d)

        override val outline = Color(0xFF75796c)
        override val outlineVariant = Color(0xFFc5c8ba)

        override val scrim = Color(0xFF000000)
        override val shadow = Color(0xFF000000)

        override val background = Color(0xFFf9faef)
        override val onBackground = Color(0xFF1a1c16)
    }

    object Dark : ThemeColorSet {
        override val primary = Color(0xFFb1d18a)
        override val secondary = Color(0xFFbfcbad)
        override val tertiary = Color(0xFFa0d0cb)
        override val error = Color(0xFFffb4ab)

        override val onPrimary = Color(0xFF1f3701)
        override val onSecondary = Color(0xFF2a331e)
        override val onTertiary = Color(0xFF003735)
        override val onError = Color(0xFF690005)

        override val containerPrimary = Color(0xFF354e16)
        override val containerSecondary = Color(0xFF404a33)
        override val containerTertiary = Color(0xFF1f4e4b)
        override val containerError = Color(0xFF93000a)

        override val onContainerPrimary = Color(0xFFcdeda3)
        override val onContainerSecondary = Color(0xFFdce7c8)
        override val onContainerTertiary = Color(0xFFbcece7)
        override val onContainerError = Color(0xFFffdad6)

        override val surfaceDim = Color(0xFF12140e)
        override val surface = Color(0xFF12140e)
        override val surfaceVariant = Color(0xFF44483e)
        override val surfaceBright = Color(0xFF383a32)
        override val surfaceInverse = Color(0xFFe2e3d8)

        override val surfaceContainerLowest = Color(0xFF0c0f09)
        override val surfaceContainerLow = Color(0xFF1a1c16)
        override val surfaceContainer = Color(0xFF1e201a)
        override val surfaceContainerHigh = Color(0xFF282b24)
        override val surfaceContainerHighest = Color(0xFF33362e)

        override val inverseOnSurface = Color(0xFF2f312a)
        override val inversePrimary = Color(0xFF4c662b)

        override val onSurface = Color(0xFFe2e3d8)
        override val onSurfaceVariant = Color(0xFFc5c8ba)

        override val outline = Color(0xFF8f9285)
        override val outlineVariant = Color(0xFF44483d)

        override val scrim = Color(0xFF000000)
        override val shadow = Color(0xFF000000)

        override val background = Color(0xFF12140e)
        override val onBackground = Color(0xFFe2e3d8)
    }
}

object ColorBlue {
    object Light : ThemeColorSet {
        override val primary = Color(0xFF415f91)
        override val secondary = Color(0xFF565f71)
        override val tertiary = Color(0xFF705575)
        override val error = Color(0xFFba1a1a)

        override val onPrimary = Color(0xFFffffff)
        override val onSecondary = Color(0xFFffffff)
        override val onTertiary = Color(0xFFffffff)
        override val onError = Color(0xFFffffff)

        override val containerPrimary = Color(0xFFd6e3ff)
        override val containerSecondary = Color(0xFFdae2f9)
        override val containerTertiary = Color(0xFFfad8fd)
        override val containerError = Color(0xFFffdad6)

        override val onContainerPrimary = Color(0xFF284777)
        override val onContainerSecondary = Color(0xFF3e4759)
        override val onContainerTertiary = Color(0xFF573e5c)
        override val onContainerError = Color(0xFF93000a)

        override val surfaceDim = Color(0xFFd9d9e0)
        override val surface = Color(0xFFf9f9ff)
        override val surfaceVariant = Color(0xFFe2e2e8)
        override val surfaceBright = Color(0xFFf9f9ff)
        override val surfaceInverse = Color(0xFF2e3036)

        override val surfaceContainerLowest = Color(0xFFffffff)
        override val surfaceContainerLow = Color(0xFFf3f3fa)
        override val surfaceContainer = Color(0xFFededf4)
        override val surfaceContainerHigh = Color(0xFFe7e8ee)
        override val surfaceContainerHighest = Color(0xFFe2e2e9)

        override val inverseOnSurface = Color(0xFFf0f0f7)
        override val inversePrimary = Color(0xFFaac7ff)

        override val onSurface = Color(0xFF191c20)
        override val onSurfaceVariant = Color(0xFF44474e)

        override val outline = Color(0xFF74777f)
        override val outlineVariant = Color(0xFFc4c6d0)
        

        override val scrim = Color(0xFF000000)
        override val shadow = Color(0xFF000000)

        override val background = Color(0xFFf9f9ff)
        override val onBackground = Color(0xFF191c20)
    }

    object Dark : ThemeColorSet {
        override val primary = Color(0xFFaac7ff)
        override val secondary = Color(0xFFbec6dc)
        override val tertiary = Color(0xFFddbce0)
        override val error = Color(0xFFffb4ab)

        override val onPrimary = Color(0xFF0a305f)
        override val onSecondary = Color(0xFF283141)
        override val onTertiary = Color(0xFF3f2844)
        override val onError = Color(0xFF690005)

        override val containerPrimary = Color(0xFF723523)
        override val containerSecondary = Color(0xFF5d4037)
        override val containerTertiary = Color(0xFF534619)
        override val containerError = Color(0xFF93000a)

        override val onContainerPrimary = Color(0xFFD6E3FF)
        override val onContainerSecondary = Color(0xFF3e4759)
        override val onContainerTertiary = Color(0xFF573e5c)
        override val onContainerError = Color(0xFF93000a)

        override val surfaceDim = Color(0xFF111318)
        override val surface = Color(0xFF111318)
        override val surfaceVariant = Color(0xFF45474c)
        override val surfaceBright = Color(0xFF37393e)
        override val surfaceInverse = Color(0xFFe2e2e9)

        override val surfaceContainerLowest = Color(0xFF0c0e13)
        override val surfaceContainerLow = Color(0xFF191c20)
        override val surfaceContainer = Color(0xFF1d2024)
        override val surfaceContainerHigh = Color(0xFF282a2f)
        override val surfaceContainerHighest = Color(0xFF33353a)

        override val inverseOnSurface = Color(0xFF2e3036)
        override val inversePrimary = Color(0xFF415f91)

        override val onSurface = Color(0xFFe2e2e9)
        override val onSurfaceVariant = Color(0xFFc4c6d0)

        override val outline = Color(0xFF8e9099)
        override val outlineVariant = Color(0xFF44474e)

        override val scrim = Color(0xFF000000)
        override val shadow = Color(0xFF000000)

        override val background = Color(0xFF111318)
        override val onBackground = Color(0xFFe2e2e9)
    }
}

object ColorYellow {
    object Light : ThemeColorSet {
        override val primary = Color(0xFF6d5e0f)
        override val secondary = Color(0xFF665e40)
        override val tertiary = Color(0xFF43664e)
        override val error = Color(0xFFba1a1a)

        override val onPrimary = Color(0xFFffffff)
        override val onSecondary = Color(0xFFffffff)
        override val onTertiary = Color(0xFFffffff)
        override val onError = Color(0xFFffffff)

        override val containerPrimary = Color(0xFFf8e287)
        override val containerSecondary = Color(0xFFeee2bc)
        override val containerTertiary = Color(0xFFc5ecce)
        override val containerError = Color(0xFFffdad6)

        override val onContainerPrimary = Color(0xFF534600)
        override val onContainerSecondary = Color(0xFF4e472a)
        override val onContainerTertiary = Color(0xFF2c4e38)
        override val onContainerError = Color(0xFF93000a)

        override val surfaceDim = Color(0xFFe0d9cc)
        override val surface = Color(0xFFfff9ee)
        override val surfaceVariant = Color(0xFFe7e2d6)
        override val surfaceBright = Color(0xFFfff9ee)
        override val surfaceInverse = Color(0xFF333027)

        override val surfaceContainerLowest = Color(0xFFffffff)
        override val surfaceContainerLow = Color(0xFFfaf3e5)
        override val surfaceContainer = Color(0xFFf4eddf)
        override val surfaceContainerHigh = Color(0xFFeee8da)
        override val surfaceContainerHighest = Color(0xFFe8e2d4)

        override val inverseOnSurface = Color(0xFFf7f0e2)
        override val inversePrimary = Color(0xFFdbc66e)

        override val onSurface = Color(0xFF1e1b13)
        override val onSurfaceVariant = Color(0xFF4b4739)

        override val outline = Color(0xFF7c7767)
        override val outlineVariant = Color(0xFFcdc6b4)

        override val scrim = Color(0xFF000000)
        override val shadow = Color(0xFF000000)

        override val background = Color(0xFFfff9ee)
        override val onBackground = Color(0xFF1e1b13)
    }

    object Dark : ThemeColorSet {
        override val primary = Color(0xFFdbc66e)
        override val secondary = Color(0xFFd1c6a1)
        override val tertiary = Color(0xFFa9d0b3)
        override val error = Color(0xFFffb4ab)

        override val onPrimary = Color(0xFF3a3000)
        override val onSecondary = Color(0xFF363016)
        override val onTertiary = Color(0xFF143723)
        override val onError = Color(0xFF690005)

        override val containerPrimary = Color(0xFF534600)
        override val containerSecondary = Color(0xFF4e472a)
        override val containerTertiary = Color(0xFF2c4e38)
        override val containerError = Color(0xFF93000a)

        override val onContainerPrimary = Color(0xFFf8e287)
        override val onContainerSecondary = Color(0xFFeee2bc)
        override val onContainerTertiary = Color(0xFFc5ecce)
        override val onContainerError = Color(0xFFffdad6)

        override val surfaceDim = Color(0xFF15130b)
        override val surface = Color(0xFF15130b)
        override val surfaceVariant = Color(0xFF4a473e)
        override val surfaceBright = Color(0xFF3c3930)
        override val surfaceInverse = Color(0xFFe8e2d4)

        override val surfaceContainerLowest = Color(0xFF100e07)
        override val surfaceContainerLow = Color(0xFF1e1b13)
        override val surfaceContainer = Color(0xFF222017)
        override val surfaceContainerHigh = Color(0xFF2d2a21)
        override val surfaceContainerHighest = Color(0xFF38352b)

        override val inverseOnSurface = Color(0xFF333027)
        override val inversePrimary = Color(0xFF6d5e0f)

        override val onSurface = Color(0xFFe8e2d4)
        override val onSurfaceVariant = Color(0xFFcdc6b4)

        override val outline = Color(0xFF969080)
        override val outlineVariant = Color(0xFF4b4739)

        override val scrim = Color(0xFF000000)
        override val shadow = Color(0xFF000000)

        override val background = Color(0xFF15130b)
        override val onBackground = Color(0xFFe8e2d4)
    }
}
