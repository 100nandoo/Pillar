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
val PillarTypography3 = Typography(
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
    ),
    headlineMedium = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontSize = 12.sp
    )
)

val PillarTypography = androidx.compose.material.Typography(
    h1 = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    h2 = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    h3 = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    h4 = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    h5 = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    h6 = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Lato,
        fontSize = 14.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = Lato,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontFamily = Lato,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = Lato,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily.Serif,
        fontSize = 12.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Serif,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontFamily = FontFamily.Serif,
        fontSize = 12.sp
    )

)
