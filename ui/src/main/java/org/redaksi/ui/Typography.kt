package org.redaksi.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Lato = FontFamily(
    Font(R.font.lato_regular, FontWeight.Normal)
)

val Lora = FontFamily(
    Font(R.font.lora_medium, FontWeight.Medium),
    Font(R.font.lora_semibold, FontWeight.SemiBold),
    Font(R.font.lora_bold, FontWeight.Bold)
)
val PillarTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Lato,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Lato,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Lato,
        fontSize = 12.sp
    )
)
