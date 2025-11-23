// Green-based Material 3 theme
package com.example.mobiledevlab1.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val GreenColorScheme = lightColorScheme(
    primary = Color(0xFF2E7D32),
    onPrimary = Color.White,
    secondary = Color(0xFF66BB6A),
    onSecondary = Color.Black,
    background = Color(0xFFE8F5E9),
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun GreenFuelTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = GreenColorScheme,
        typography = Typography(),
        content = content
    )
}
