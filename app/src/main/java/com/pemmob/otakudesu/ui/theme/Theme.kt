package com.pemmob.otakudesu.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = AnimePrimary,
    secondary = AnimeSecondary,
    background = Color(0xFF121418),
    surface = Color(0xFF1C1F24)
)

private val LightColorScheme = lightColorScheme(
    primary = AnimePrimary,
    secondary = AnimeSecondary,
    background = AnimeBackground,
    surface = AnimeSurface
)

@Composable
fun AnimeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AnimeTypography,
        content = content
    )
}