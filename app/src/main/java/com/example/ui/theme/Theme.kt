package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = LuminousCream,
    onPrimary = DeepBurgundy,
    primaryContainer = WarmEarthBrown,
    onPrimaryContainer = LuminousCreamLight,
    secondary = SecondaryRose,
    onSecondary = Color.White,
    secondaryContainer = DeepBurgundy,
    onSecondaryContainer = SecondaryContainer,
    tertiary = SoftSteelBlue,
    onTertiary = Color.White,
    tertiaryContainer = SoftSteelBlueDark,
    onTertiaryContainer = SoftSteelBlueLight,
    background = Color(0xFF1E1412),
    onBackground = Color(0xFFFFEDE9),
    surface = Color(0xFF261917),
    onSurface = Color(0xFFFFEDE9),
    surfaceVariant = Color(0xFF452923),
    onSurfaceVariant = Color(0xFFCAC6B9)
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBrown,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = SecondaryRose,
    onSecondary = Color.White,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = SoftSteelBlueDark,
    onTertiary = Color.White,
    tertiaryContainer = SoftSteelBlueLight,
    onTertiaryContainer = SoftSteelBlueDark,
    background = WarmSurface,
    onBackground = WarmOnSurface,
    surface = WarmSurface,
    onSurface = WarmOnSurface,
    surfaceVariant = WarmSurfaceContainerHigh,
    onSurfaceVariant = WarmOnSurfaceVariant
)

@Composable
fun MYLYTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

