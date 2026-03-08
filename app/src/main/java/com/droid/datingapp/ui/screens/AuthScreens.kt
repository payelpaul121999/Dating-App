package com.droid.datingapp.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.dating.app.ui.components.*
import com.droid.datingapp.ui.theme.BgWhite
import com.droid.datingapp.ui.theme.BorderColor
import com.droid.datingapp.ui.theme.InputBg
import com.droid.datingapp.ui.theme.PinkPastel
import com.droid.datingapp.ui.theme.PrimaryPink
import com.droid.datingapp.ui.theme.TextDark
import com.droid.datingapp.ui.theme.TextLight
import com.droid.datingapp.ui.theme.TextMedium

// ── Shared header used in auth screens ────────────────────────────
@Composable
private fun AuthHeader(title: String, subtitle: String, onBack: (() -> Unit)? = null) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp)) {
        if (onBack != null) {
            IconButton(onClick = onBack, modifier = Modifier.offset(x = (-12).dp)) {
                Icon(Icons.Filled.ArrowBackIosNew, null, tint = TextDark)
            }
        }
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            AppLogo(size = 40.dp)
            Spacer(Modifier.width(10.dp))
            Column {
                Text("Welcome Back", fontSize = 11.sp, color = TextLight)
                Text("Your love story continues...", fontSize = 12.sp, color = PrimaryPink, fontWeight = FontWeight.Medium)
            }
        }
        Spacer(Modifier.height(24.dp))
        Text(title, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = TextDark)
        Text(subtitle, fontSize = 14.sp, color = TextMedium, modifier = Modifier.padding(top = 4.dp))
    }
}

// ── Login Screen ──────────────────────────────────────────────────
@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    onForgot: () -> Unit,
    onBack: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().background(BgWhite)
            .verticalScroll(rememberScrollState())
    ) {
        // Pink top curve
        Box(
            modifier = Modifier.fillMaxWidth().height(180.dp)
                .background(Brush.verticalGradient(listOf(PinkPastel, BgWhite))),
            contentAlignment = Alignment.Center
        ) {
            AppLogo(size = 80.dp)
        }

        AuthHeader("Log In", "Welcome back! Sign in to continue", onBack = onBack)

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            DatingTextField(value = email, onValueChange = { email = it },
                placeholder = "Email address", leadingIcon = Icons.Filled.Email,
                keyboardType = KeyboardType.Email)
            Spacer(Modifier.height(12.dp))
            DatingTextField(value = password, onValueChange = { password = it },
                placeholder = "Password", leadingIcon = Icons.Filled.Lock, isPassword = true)
            Spacer(Modifier.height(8.dp))
            Text("Forgot Password?", color = PrimaryPink, fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.End).clickable { onForgot() })
            Spacer(Modifier.height(20.dp))
            PinkButton("Log In", onClick = onLogin)
            Spacer(Modifier.height(16.dp))
            DividerWithText("or login with")
            Spacer(Modifier.height(16.dp))
            SocialLoginRow()
            Spacer(Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Don't have an account? ", color = TextMedium, fontSize = 14.sp)
                Text("Sign Up", color = PrimaryPink, fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onRegister() })
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

// ── Register Screen ───────────────────────────────────────────────
@Composable
fun RegisterScreen(onRegister: () -> Unit, onLogin: () -> Unit, onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().background(BgWhite)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(150.dp)
                .background(Brush.verticalGradient(listOf(PinkPastel, BgWhite))),
            contentAlignment = Alignment.Center
        ) { AppLogo(size = 70.dp) }

        AuthHeader("Create Account", "Join millions finding their perfect match", onBack = onBack)

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            DatingTextField(name, { name = it }, "Full Name", leadingIcon = Icons.Filled.Person)
            Spacer(Modifier.height(12.dp))
            DatingTextField(email, { email = it }, "Email address",
                leadingIcon = Icons.Filled.Email, keyboardType = KeyboardType.Email)
            Spacer(Modifier.height(12.dp))
            DatingTextField(dob, { dob = it }, "Date of Birth (DD/MM/YYYY)",
                leadingIcon = Icons.Filled.CalendarToday)
            Spacer(Modifier.height(12.dp))
            DatingTextField(password, { password = it }, "Password",
                leadingIcon = Icons.Filled.Lock, isPassword = true)
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                var checked by remember { mutableStateOf(false) }
                Checkbox(checked = checked, onCheckedChange = { checked = it },
                    colors = CheckboxDefaults.colors(checkedColor = PrimaryPink))
                Text("I agree to the ", fontSize = 12.sp, color = TextMedium)
                Text("Terms & Privacy Policy", fontSize = 12.sp, color = PrimaryPink,
                    fontWeight = FontWeight.SemiBold)
            }
            Spacer(Modifier.height(20.dp))
            PinkButton("Create Account", onClick = onRegister)
            Spacer(Modifier.height(16.dp))
            DividerWithText("or sign up with")
            Spacer(Modifier.height(16.dp))
            SocialLoginRow()
            Spacer(Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Already have an account? ", color = TextMedium, fontSize = 14.sp)
                Text("Log In", color = PrimaryPink, fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable { onLogin() })
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

// ── Phone Entry Screen ────────────────────────────────────────────
@Composable
fun PhoneEntryScreen(onContinue: () -> Unit, onBack: () -> Unit) {
    var phone by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().background(BgWhite)) {
        AuthHeader("My Mobile Number", "We'll send a verification code to this number", onBack = onBack)
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.height(56.dp).width(72.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, BorderColor, RoundedCornerShape(12.dp))
                        .background(InputBg),
                    contentAlignment = Alignment.Center
                ) { Text("+91", fontWeight = FontWeight.SemiBold, color = TextDark) }
                Spacer(Modifier.width(8.dp))
                DatingTextField(phone, { phone = it }, "Phone number",
                    keyboardType = KeyboardType.Phone)
            }
            Spacer(Modifier.height(24.dp))
            PinkButton("Continue", onClick = onContinue)
        }
    }
}

// ── OTP Verification Screen ───────────────────────────────────────
@Composable
fun OtpScreen(title: String, subtitle: String, onVerify: () -> Unit, onBack: () -> Unit) {
    val otp = remember { mutableStateListOf("", "", "", "", "", "") }
    Column(modifier = Modifier.fillMaxSize().background(BgWhite)) {
        AuthHeader(title, subtitle, onBack = onBack)
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OtpRow(otp = otp.toList(), onOtpChange = { i, v -> if (v.length <= 1) otp[i] = v })
            Spacer(Modifier.height(16.dp))
            Row {
                Text("Didn't receive code? ", color = TextMedium, fontSize = 13.sp)
                Text("Resend", color = PrimaryPink, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(Modifier.height(32.dp))
            PinkButton("Verify", onClick = onVerify)
        }
    }
}

// ── Forgot Password Screen ────────────────────────────────────────
@Composable
fun ForgotPasswordScreen(onSend: () -> Unit, onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().background(BgWhite)) {
        AuthHeader("Forgot Password", "Enter your email and we'll send a reset link", onBack = onBack)
        Column(modifier = Modifier.padding(24.dp)) {
            DatingTextField(email, { email = it }, "Email address",
                leadingIcon = Icons.Filled.Email, keyboardType = KeyboardType.Email)
            Spacer(Modifier.height(24.dp))
            PinkButton("Send Reset Link", onClick = onSend)
        }
    }
}

// ── Create New Password Screen ────────────────────────────────────
@Composable
fun CreatePasswordScreen(onSave: () -> Unit, onBack: () -> Unit) {
    var pass by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().background(BgWhite)) {
        AuthHeader("Create New Password", "Your new password must be different from the old one", onBack = onBack)
        Column(modifier = Modifier.padding(24.dp)) {
            DatingTextField(pass, { pass = it }, "New Password",
                leadingIcon = Icons.Filled.Lock, isPassword = true)
            Spacer(Modifier.height(12.dp))
            DatingTextField(confirm, { confirm = it }, "Confirm Password",
                leadingIcon = Icons.Filled.Lock, isPassword = true)
            Spacer(Modifier.height(24.dp))
            PinkButton("Save Password", onClick = onSave)
        }
    }
}

// ── Congratulations Screen ────────────────────────────────────────
@Composable
fun CongratulationsScreen(onContinue: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(listOf(PinkPastel, BgWhite))
        ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Box(
                modifier = Modifier.size(100.dp).clip(CircleShape)
                    .background(pinkGradient),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Check, null, tint = Color.White, modifier = Modifier.size(54.dp))
            }
            Spacer(Modifier.height(24.dp))
            Text("Congratulations!", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = PrimaryPink)
            Spacer(Modifier.height(8.dp))
            Text("Your account has been created\nsuccessfully. Let's find your match!",
                fontSize = 15.sp, color = TextMedium, textAlign = TextAlign.Center, lineHeight = 22.sp)
            Spacer(Modifier.height(32.dp))
            PinkButton("Get Started", onClick = onContinue)
            Spacer(Modifier.height(12.dp))
            DividerWithText("or sign in with")
            Spacer(Modifier.height(12.dp))
            SocialLoginRow()
        }
    }
}
