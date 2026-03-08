package com.dating.app.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.*
import com.droid.datingapp.model.Screen
import com.droid.datingapp.ui.theme.BgGray
import com.droid.datingapp.ui.theme.BgWhite
import com.droid.datingapp.ui.theme.BorderColor
import com.droid.datingapp.ui.theme.GradientPink1
import com.droid.datingapp.ui.theme.GradientPink2
import com.droid.datingapp.ui.theme.GradientPink3
import com.droid.datingapp.ui.theme.InputBg
import com.droid.datingapp.ui.theme.PinkPastel
import com.droid.datingapp.ui.theme.PrimaryPink
import com.droid.datingapp.ui.theme.TextDark
import com.droid.datingapp.ui.theme.TextLight
import com.droid.datingapp.ui.theme.TextMedium

// ── Pink gradient brush ───────────────────────────────────────────
val pinkGradient = Brush.horizontalGradient(listOf(GradientPink1, GradientPink2))
val pinkGradientVert = Brush.verticalGradient(listOf(GradientPink1, GradientPink2))
val pinkGradientDiag = Brush.linearGradient(listOf(GradientPink1, GradientPink3))

// ── App Logo ──────────────────────────────────────────────────────
@Composable
fun AppLogo(modifier: Modifier = Modifier, size: Dp = 72.dp) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(pinkGradient),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Logo",
            tint = Color.White,
            modifier = Modifier.size(size * 0.5f)
        )
    }
}

// ── Primary gradient button ───────────────────────────────────────
@Composable
fun PinkButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(if (enabled) pinkGradient else Brush.horizontalGradient(listOf(Color.LightGray, Color.LightGray)))
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon?.let {
                Icon(imageVector = it, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
            }
            Text(text = text, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

// ── Outlined / ghost button ───────────────────────────────────────
@Composable
fun OutlinePinkButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(26.dp))
            .border(2.dp, pinkGradient, RoundedCornerShape(26.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = PrimaryPink, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

// ── Text field ────────────────────────────────────────────────────
@Composable
fun DatingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    leadingIcon: ImageVector? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = TextLight, fontSize = 14.sp) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = BorderColor,
            focusedBorderColor = PrimaryPink,
            unfocusedContainerColor = InputBg,
            focusedContainerColor = Color.White,
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        leadingIcon = leadingIcon?.let { { Icon(it, null, tint = TextLight, modifier = Modifier.size(20.dp)) } },
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        null, tint = TextLight
                    )
                }
            }
        } else trailingIcon
    )
}

// ── Social login row ──────────────────────────────────────────────
@Composable
fun SocialLoginRow(onGoogle: () -> Unit = {}, onFacebook: () -> Unit = {}, onApple: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SocialBtn("G", Color(0xFFDB4437), onGoogle)
        Spacer(Modifier.width(16.dp))
        SocialBtn("f", Color(0xFF1877F2), onFacebook)
        Spacer(Modifier.width(16.dp))
        SocialBtn("", Color(0xFF000000), onApple, Icons.Filled.Settings)
    }
}

@Composable
fun SocialBtn(label: String, color: Color, onClick: () -> Unit, icon: ImageVector? = null) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .border(1.dp, BorderColor, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (icon != null) {
            Icon(icon, null, tint = color, modifier = Modifier.size(22.dp))
        } else {
            Text(label, fontWeight = FontWeight.Bold, color = color, fontSize = 18.sp)
        }
    }
}

// ── OTP input row ─────────────────────────────────────────────────
@Composable
fun OtpRow(otp: List<String>, onOtpChange: (Int, String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        otp.forEachIndexed { i, digit ->
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(2.dp, if (digit.isNotEmpty()) PrimaryPink else BorderColor, RoundedCornerShape(12.dp))
                    .background(InputBg),
                contentAlignment = Alignment.Center
            ) {
                Text(digit, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextDark)
            }
        }
    }
}

// ── Divider with text ─────────────────────────────────────────────
@Composable
fun DividerWithText(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Divider(modifier = Modifier.weight(1f), color = BorderColor)
        Text(text, modifier = Modifier.padding(horizontal = 12.dp), color = TextLight, fontSize = 12.sp)
       Divider(modifier = Modifier.weight(1f), color = BorderColor)
    }
}

// ── Interest chip ─────────────────────────────────────────────────
@Composable
fun InterestChip(label: String, selected: Boolean, onClick: () -> Unit) {
    val bg = if (selected) pinkGradient else Brush.linearGradient(listOf(BgGray, BgGray))
    val textColor = if (selected) Color.White else TextMedium
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .border(if (selected) 0.dp else 1.dp, BorderColor, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(label, color = textColor, fontSize = 13.sp, fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal)
    }
}

// ── Bottom navigation bar ─────────────────────────────────────────
data class BottomNavItem(val label: String, val icon: ImageVector, val selectedIcon: ImageVector, val route: String)

val bottomNavItems = listOf(
    BottomNavItem("Home",     Icons.Outlined.Home,         Icons.Filled.Home,         Screen.Home.route),
    BottomNavItem("Matches",  Icons.Outlined.FavoriteBorder,Icons.Filled.Favorite,    Screen.Matches.route),
    BottomNavItem("Messages", Icons.Outlined.ChatBubbleOutline,Icons.Filled.ChatBubble, Screen.Messages.route),
    BottomNavItem("Profile",  Icons.Outlined.Person,       Icons.Filled.Person,       Screen.Profile.route),
)

@Composable
fun BottomNav(currentRoute: String, onNavigate: (String) -> Unit) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier.shadow(8.dp)
    ) {
        bottomNavItems.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.icon,
                        contentDescription = item.label,
                        tint = if (selected) PrimaryPink else TextLight
                    )
                },
                label = { Text(item.label, fontSize = 11.sp, color = if (selected) PrimaryPink else TextLight) },
                colors = NavigationBarItemDefaults.colors(indicatorColor = PinkPastel)
            )
        }
    }
}

// ── Screen scaffold with bottom nav ───────────────────────────────
@Composable
fun MainScaffold(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = { BottomNav(currentRoute, onNavigate) },
        containerColor = BgWhite
    ) { padding -> content(padding) }
}

// ── Back-arrow top bar ────────────────────────────────────────────
@Composable
fun TopBarWithBack(title: String, onBack: () -> Unit, actions: @Composable RowScope.() -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.Filled.ArrowBackIosNew, null, tint = TextDark)
        }
        Spacer(Modifier.weight(1f))
        Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextDark)
        Spacer(Modifier.weight(1f))
        actions()
    }
}

// ── Gradient text helper ──────────────────────────────────────────
fun Modifier.pinkGradientBg() = this.background(
    Brush.linearGradient(listOf(GradientPink1, GradientPink2))
)
