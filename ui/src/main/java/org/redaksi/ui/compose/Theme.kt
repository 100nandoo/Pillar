package org.redaksi.ui.compose

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme as MaterialTheme3
import androidx.compose.material3.Typography as Typography3
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import org.redaksi.ui.compose.PillarColor.background
import org.redaksi.ui.compose.PillarColor.onBackground
import org.redaksi.ui.compose.PillarColor.onPrimaryContainer
import org.redaksi.ui.compose.PillarColor.primary
import org.redaksi.ui.compose.PillarColor.secondary
import org.redaksi.ui.compose.PillarColor.secondaryContainer

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

@Composable
fun PillarTheme(
    typography3: Typography3 = PillarTypography3,
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
        } else {
            LightMainColorPalette
        }
    }

    MaterialTheme3(
        colorScheme = colorScheme,
        content = content,
        typography = typography3
    )
}
