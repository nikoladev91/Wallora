package com.example.wallora.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

@Composable
fun WallpapersAppTheme(
    content: @Composable () -> Unit
) {
    val accentContentColor =
        if (WalloraAccent.luminance() > 0.45f) {
            Color.Black
        } else {
            Color.White
        }

    val colorScheme = darkColorScheme(
        primary = WalloraAccent,
        onPrimary = accentContentColor,

        secondary = WalloraAccent,
        onSecondary = accentContentColor,

        background = WalloraBackground,
        onBackground = Color.White,

        surface = WalloraSurface,
        onSurface = Color.White,

        surfaceVariant = WalloraSurface,
        onSurfaceVariant = Color.White.copy(alpha = 0.75f),

        outline = Color.White.copy(alpha = 0.35f)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}