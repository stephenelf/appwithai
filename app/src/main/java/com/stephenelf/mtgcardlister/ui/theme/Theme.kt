package com.stephenelf.mtgcardlister.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Define the dark color scheme based on the provided image
private val DarkColorScheme = darkColorScheme(
    primary = DarkGreen,
    onPrimary = TextLight,
    primaryContainer = DarkGreen,
    onPrimaryContainer = TextLight,
    secondary = LightGreen,
    onSecondary = TextDark,
    secondaryContainer = LightGreen,
    onSecondaryContainer = TextDark,
    tertiary = LightGreen,
    onTertiary = TextDark,
    tertiaryContainer = LightGreen,
    onTertiaryContainer = TextDark,
    error = Color(0xFFB00020), // Standard error color
    onError = Color.White,
    background = DarkGreen, // Main background color
    onBackground = TextLight,
    surface = SurfaceDark, // Card and other elevated surfaces
    onSurface = TextLight,
    surfaceVariant = SearchBarBackground, // For search bar, etc.
    onSurfaceVariant = TextLight,
    outline = TextLight.copy(alpha = 0.5f), // Border colors
    scrim = Color.Black.copy(alpha = 0.4f)
)

// A light color scheme is not explicitly needed based on the dark design,
// but it's good practice to provide one or remove if not used.
private val LightColorScheme = lightColorScheme(
    primary = LightGreen,
    onPrimary = TextDark,
    primaryContainer = LightGreen,
    onPrimaryContainer = TextDark,
    secondary = DarkGreen,
    onSecondary = TextLight,
    secondaryContainer = DarkGreen,
    onSecondaryContainer = TextLight,
    tertiary = DarkGreen,
    onTertiary = TextLight,
    tertiaryContainer = DarkGreen,
    onTertiaryContainer = TextLight,
    error = Color(0xFFB00020),
    onError = Color.White,
    background = Color.White,
    onBackground = TextDark,
    surface = Color.White,
    onSurface = TextDark,
    surfaceVariant = Color(0xFFF0F0F0),
    onSurfaceVariant = TextDark,
    outline = TextDark.copy(alpha = 0.5f),
    scrim = Color.Black.copy(alpha = 0.4f)
)

@Composable
fun MTGCardsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Default to system dark theme
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb() // Set status bar color
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Defined in Type.kt
        content = content
    )
}