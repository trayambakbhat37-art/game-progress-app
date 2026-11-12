package com.example.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val PixelBlueScheme = darkColorScheme(
    primary = PixelSkyCyan,
    onPrimary = Color(0xFF002845),
    primaryContainer = PixelBluePrimary,
    onPrimaryContainer = Color(0xFFE0F2FE),
    secondary = PixelGoldStar,
    onSecondary = Color(0xFF3E2D00),
    secondaryContainer = Color(0xFF5B4300),
    onSecondaryContainer = PixelGoldLight,
    tertiary = PixelHeartRed,
    onTertiary = Color.White,
    background = PixelBackgroundDark,
    onBackground = TextPureWhite,
    surface = PixelSurfaceCard,
    onSurface = TextPureWhite,
    surfaceVariant = PixelSurfaceElevated,
    onSurfaceVariant = TextSoftWhite,
    outline = PixelBorderBlue
)

@Composable
fun GameTacticsTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = PixelBlueScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            window?.let {
                it.statusBarColor = colorScheme.background.toArgb()
                it.navigationBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(it, view).apply {
                    isAppearanceLightStatusBars = false
                    isAppearanceLightNavigationBars = false
                }
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
