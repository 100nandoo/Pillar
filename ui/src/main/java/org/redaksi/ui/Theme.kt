package org.redaksi.ui

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import org.redaksi.ui.PillarColor.background
import org.redaksi.ui.PillarColor.onBackground
import org.redaksi.ui.PillarColor.onPrimaryContainer
import org.redaksi.ui.PillarColor.primary
import org.redaksi.ui.PillarColor.secondary
import org.redaksi.ui.PillarColor.secondaryContainer
import org.redaksi.ui.PillarColor.secondaryVar
import androidx.compose.material3.MaterialTheme as MaterialTheme3
import androidx.compose.material3.Typography as Typography3

private val DarkMainColorPalette = darkColorScheme(
    primary = primary,
    primaryContainer = primary,
    onPrimaryContainer = onPrimaryContainer,
    secondary = secondary,
    secondaryContainer = secondaryContainer,
    onSecondaryContainer = Color.White,
    background = background,
    surface = background,
    surfaceVariant = background,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = onBackground,
    onSurface = onBackground,
    onSurfaceVariant = onBackground,
    error = Color.Red
)

private val LightMainColorPalette = lightColorScheme(
    primary = primary,
    primaryContainer = primary,
    onPrimaryContainer = onPrimaryContainer,
    secondary = secondary,
    secondaryContainer = secondaryContainer,
    onSecondaryContainer = Color.White,
    background = background,
    surface = background,
    surfaceVariant = background,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = onBackground,
    onSurface = onBackground,
    onSurfaceVariant = onBackground,
    onError = Color.Red
)

private val darkColors = Colors(
    primary = primary,
    primaryVariant = primary,
    secondary = secondary,
    secondaryVariant = secondaryVar,
    background = background,
    surface = background,
    error = Color.Red,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = onBackground,
    onSurface = onBackground,
    onError = Color.Red,
    isLight = false
)

private val lightColors = Colors(
    primary = primary,
    primaryVariant = primary,
    secondary = secondary,
    secondaryVariant = secondaryVar,
    background = background,
    surface = background,
    error = Color.Red,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = onBackground,
    onSurface = onBackground,
    onError = Color.Red,
    isLight = true
)

@Composable
fun PillarTheme(
    typography3: Typography3 = PillarTypography3,
    typography: Typography = PillarTypography,
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorPallet: ColorPallet = ColorPallet.MAIN,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val colorScheme = when (colorPallet) {
        ColorPallet.MAIN -> if (darkTheme) DarkMainColorPalette else LightMainColorPalette
        ColorPallet.WALLPAPER -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        } else if (darkTheme) {
            DarkMainColorPalette
        } else LightMainColorPalette
    }

    val colors = if (darkTheme) darkColors else lightColors

    MaterialTheme3(
        colorScheme = colorScheme,
        content = content,
        typography = typography3
    )

    MaterialTheme(
        colors = colors,
        content = content,
        typography = typography
    )
}
