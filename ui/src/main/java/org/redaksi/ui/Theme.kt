package org.redaksi.ui

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
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
    error = Color.Red,
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

@Composable
fun PIllarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorPallet: ColorPallet = ColorPallet.MAIN,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    val colors = when (colorPallet) {
        ColorPallet.MAIN -> if (darkTheme) DarkMainColorPalette else LightMainColorPalette
        ColorPallet.WALLPAPER -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            if (darkTheme)
                dynamicDarkColorScheme(context)
            else
                dynamicLightColorScheme(context)
        else
            if (darkTheme)
                DarkMainColorPalette
            else LightMainColorPalette
    }
    androidx.compose.material3.MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
