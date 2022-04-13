package org.redaksi.ui

import androidx.compose.ui.graphics.Color

object PillarColor {
    val primary = Color(0xff7B3334)
    val primaryContainer = Color(0xffA27070)
    val onPrimaryContainer = Color(0x3c000000)

    val secondary = Color(0xffA27070)
    val secondaryVar = Color(0xffF0C4B9)
    val secondaryContainer = Color(0xff7B3334)

    val background = Color(0xffFCF5F3)
    val surface = Color(0xFFFFFFFF)

    val onBackground = Color(0x3c000000)

    val edisiBackground = Color(0xFFFFF6E3)
    val edisiNumber = Color(0xFFF9E397)
    val edisiTitle = Color(0xFFFFECC2)
}

enum class ColorPallet {
    MAIN, WALLPAPER
}
