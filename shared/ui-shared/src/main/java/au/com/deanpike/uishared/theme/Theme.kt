package au.com.deanpike.uishared.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// High-Trust Buyer Palette (Light Mode)
val LightPrimary = Color(0xFF1A2B4C)       // Deep Navy Blue (Trust/Stability)
val LightOnPrimary = Color(0xFFFFFFFF)
val LightSecondary = Color(0xFF1E3A34)     // Rich Slate Green (Growth/Real Estate)
val LightOnSecondary = Color(0xFFFFFFFF)
val LightTertiary = Color(0xFFE5A93B)      // Vibrant Gold/Amber (Primary Action/CTA)
val LightOnTertiary = Color(0xFF1A2B4C)
val LightBackground = Color(0xFFF8F9FA)    // Ultra-light Canvas Grey
val LightOnBackground = Color(0xFF1C1B1F)
val LightSurface = Color(0xFFFFFFFF)       // Crisp White Cards
val LightOnSurface = Color(0xFF1C1B1F)
val LightAccentOrange = Color(0xFFD95D39)  // Warm Terracotta (Secondary CTA)

// Premium Buyer Palette (Dark Mode)
val DarkPrimary = Color(0xFF98B4E4)
val DarkOnPrimary = Color(0xFF002D62)
val DarkSecondary = Color(0xFF8AD4B6)
val DarkOnSecondary = Color(0xFF003823)
val DarkTertiary = Color(0xFFF0C271)
val DarkOnTertiary = Color(0xFF452B00)
val DarkBackground = Color(0xFF121316)     // Deep Slate Grey
val DarkOnBackground = Color(0xFFE3E2E6)
val DarkSurface = Color(0xFF1A1C1F)        // Elevated Cards
val DarkOnSurface = Color(0xFFE3E2E6)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    tertiary = LightTertiary,
    onTertiary = LightOnTertiary,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    tertiary = DarkTertiary,
    onTertiary = DarkOnTertiary,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography, // Uses default M3 typography scales
        shapes = MaterialTheme.shapes,         // Uses default M3 shapes scales
        content = content
    )
}