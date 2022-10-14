package org.redaksi.ui.compose

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.redaksi.ui.R

val Inter = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal)
)

val Montserrat = FontFamily(
    Font(R.font.montserrat_bold, FontWeight.Bold)
)
val PillarTypography3 = Typography(
    titleLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Inter,
        fontSize = 14.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontSize = 12.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontSize = 16.sp
    )
)

val PillarTypography = androidx.compose.material.Typography(
    h1 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    h2 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    h3 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    h4 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    h5 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    h6 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Inter,
        fontSize = 14.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = Inter,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontFamily = Inter,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = Inter,
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
