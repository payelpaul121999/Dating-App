package com.dating.app.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.droid.datingapp.ui.theme.AccentRed
import com.droid.datingapp.ui.theme.BgCard
import com.droid.datingapp.ui.theme.BgGray
import com.droid.datingapp.ui.theme.BgWhite
import com.droid.datingapp.ui.theme.BorderColor
import com.droid.datingapp.ui.theme.PinkPastel
import com.droid.datingapp.ui.theme.PrimaryPink
import com.droid.datingapp.ui.theme.PrimaryPinkDark
import com.droid.datingapp.ui.theme.PrimaryPinkLight
import com.droid.datingapp.ui.theme.TextDark
import com.droid.datingapp.ui.theme.TextLight
import com.droid.datingapp.ui.theme.TextMedium
import com.droid.datingapp.ui.theme.TextWhite

val DatingTypography = Typography(
    displayLarge  = TextStyle(fontWeight = FontWeight.Bold,   fontSize = 32.sp, color = TextDark),
    displayMedium = TextStyle(fontWeight = FontWeight.Bold,   fontSize = 28.sp, color = TextDark),
    headlineLarge = TextStyle(fontWeight = FontWeight.Bold,   fontSize = 24.sp, color = TextDark),
    headlineMedium= TextStyle(fontWeight = FontWeight.SemiBold,fontSize = 20.sp,color = TextDark),
    headlineSmall = TextStyle(fontWeight = FontWeight.SemiBold,fontSize = 18.sp,color = TextDark),
    titleLarge    = TextStyle(fontWeight = FontWeight.SemiBold,fontSize = 16.sp,color = TextDark),
    titleMedium   = TextStyle(fontWeight = FontWeight.Medium, fontSize = 14.sp, color = TextDark),
    titleSmall    = TextStyle(fontWeight = FontWeight.Medium, fontSize = 12.sp, color = TextMedium),
    bodyLarge     = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp, color = TextDark),
    bodyMedium    = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp, color = TextMedium),
    bodySmall     = TextStyle(fontWeight = FontWeight.Normal, fontSize = 12.sp, color = TextLight),
    labelLarge    = TextStyle(fontWeight = FontWeight.SemiBold,fontSize = 14.sp,color = TextWhite),
    labelMedium   = TextStyle(fontWeight = FontWeight.Medium, fontSize = 12.sp, color = TextLight),
    labelSmall    = TextStyle(fontWeight = FontWeight.Normal, fontSize = 10.sp, color = TextLight),
)

private val LightColorScheme = lightColorScheme(
    primary           = PrimaryPink,
    onPrimary         = TextWhite,
    primaryContainer  = PinkPastel,
    onPrimaryContainer= PrimaryPinkDark,
    secondary         = PrimaryPinkLight,
    onSecondary       = TextWhite,
    background        = BgWhite,
    onBackground      = TextDark,
    surface           = BgCard,
    onSurface         = TextDark,
    surfaceVariant    = BgGray,
    onSurfaceVariant  = TextMedium,
    outline           = BorderColor,
    error             = AccentRed,
)

@Composable
fun DatingAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography  = DatingTypography,
        content     = content
    )
}
