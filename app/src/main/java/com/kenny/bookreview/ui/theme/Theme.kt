package com.kenny.bookreview.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Indigo200,
    primaryVariant = Indigo700,
    secondary = BlueGray200,
    error = Color.Red,
)

private val LightColorPalette = lightColors(
    primary = Indigo500,
    primaryVariant = Indigo700,
    secondary = BlueGray200,
    error = Color.Red,
)

@Composable
fun BookReviewTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}