package com.droid.datingapp.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.dating.app.ui.components.*
import com.droid.datingapp.model.NotifType
import com.droid.datingapp.model.SampleUsers
import com.droid.datingapp.model.Screen
import com.droid.datingapp.ui.theme.AccentBlue
import com.droid.datingapp.ui.theme.AccentYellow
import com.droid.datingapp.ui.theme.BgCard
import com.droid.datingapp.ui.theme.BgGray
import com.droid.datingapp.ui.theme.BgWhite
import com.droid.datingapp.ui.theme.BorderColor
import com.droid.datingapp.ui.theme.DividerColor
import com.droid.datingapp.ui.theme.PinkPastel
import com.droid.datingapp.ui.theme.PrimaryPink
import com.droid.datingapp.ui.theme.TextDark
import com.droid.datingapp.ui.theme.TextLight
import com.droid.datingapp.ui.theme.TextMedium

// ── Profile Screen ────────────────────────────────────────────────
@Composable
fun ProfileScreen(onNavigate: (String) -> Unit) {
    val me = SampleUsers.profiles.first()
    MainScaffold(Screen.Profile.route, onNavigate) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).background(BgWhite)
            .verticalScroll(rememberScrollState())) {

            // Header with cover photo
            Box(modifier = Modifier.fillMaxWidth().height(240.dp)) {
                AsyncImage(
                    model = me.photoUrls.firstOrNull(), null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(modifier = Modifier.fillMaxSize().background(
                    Brush.verticalGradient(0f to Color.Transparent, 1f to Color(0xCC000000))
                ))
                Row(
                    modifier = Modifier.align(Alignment.TopEnd).padding(12.dp)
                ) {
                    IconButton(onClick = { onNavigate(Screen.Settings.route) }) {
                        Icon(Icons.Filled.Settings, null, tint = Color.White)
                    }
                }
                Column(modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)) {
                    Text("${me.name}, ${me.age}", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.LocationOn, null, tint = Color.White.copy(0.8f), modifier = Modifier.size(14.dp))
                        Text(me.location, fontSize = 12.sp, color = Color.White.copy(0.8f))
                    }
                }
            }

            // Stats row
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileStat("124", "Likes")
                Divider(modifier = Modifier.height(40.dp), color = DividerColor)
                ProfileStat("38", "Matches")
                Divider(modifier = Modifier.height(40.dp), color = DividerColor)
                ProfileStat("12", "Chats")
            }
            Divider(color = DividerColor)

            // Bio
            Column(modifier = Modifier.padding(16.dp)) {
                Text("About Me", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextDark)
                Spacer(Modifier.height(8.dp))
                Text(me.bio, fontSize = 14.sp, color = TextMedium, lineHeight = 22.sp)
            }

            // Interests
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("Interests", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextDark)
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.horizontalScroll(rememberScrollState())) {
                    me.interests.forEach { InterestChip(it, true) {} }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Profile actions
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                ProfileActionRow(Icons.Outlined.Edit, "Edit Profile") {}
                ProfileActionRow(Icons.Outlined.WorkspacePremium, "Premium Membership", isPink = true) {
                    onNavigate(Screen.Payment.route)
                }
                ProfileActionRow(Icons.Outlined.Timer, "Talk Time") { onNavigate(Screen.TalkTime.route) }
                ProfileActionRow(Icons.Outlined.Notifications, "Notifications") { onNavigate(Screen.Notifications.route) }
                ProfileActionRow(Icons.Outlined.Info, "About") { onNavigate(Screen.About.route) }
            }

            Spacer(Modifier.height(16.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                OutlinePinkButton(
                    text = "Log Out",
                    onClick = { }
                )            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ProfileStat(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = PrimaryPink)
        Text(label, fontSize = 12.sp, color = TextLight)
    }
}

@Composable
private fun ProfileActionRow(icon: ImageVector, label: String, isPink: Boolean = false, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = if (isPink) PrimaryPink else TextMedium, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(14.dp))
        Text(label, fontSize = 15.sp, color = if (isPink) PrimaryPink else TextDark,
            fontWeight = if (isPink) FontWeight.SemiBold else FontWeight.Normal, modifier = Modifier.weight(1f))
        Icon(Icons.Filled.ChevronRight, null, tint = TextLight)
    }
    Divider(color = DividerColor)
}

// ── Enable Notifications Screen ───────────────────────────────────
@Composable
fun EnableNotificationsScreen(onEnable: () -> Unit, onSkip: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(BgWhite),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
            Box(
                modifier = Modifier.size(120.dp).clip(CircleShape).background(PinkPastel),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Notifications, null, tint = PrimaryPink, modifier = Modifier.size(60.dp))
            }
            Spacer(Modifier.height(24.dp))
            Text("Enable Notifications", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(Modifier.height(10.dp))
            Text("Get notified when someone likes you,\nsends a message or you get a match!",
                fontSize = 14.sp, color = TextMedium, textAlign = TextAlign.Center, lineHeight = 22.sp)
            Spacer(Modifier.height(32.dp))
            PinkButton("I Want to Be Notified!", onClick = onEnable, icon = Icons.Filled.NotificationsActive)
            Spacer(Modifier.height(12.dp))
            Text("Maybe Later", color = TextLight, fontSize = 14.sp,
                modifier = Modifier.clickable { onSkip() })
        }
    }
}

// ── Notifications Screen ──────────────────────────────────────────
@Composable
fun NotificationsScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(BgWhite)) {
        TopBarWithBack("Notifications", onBack)
        LazyColumn {
            items(SampleUsers.notifications) { notif ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(if (notif.type == NotifType.MATCH) PinkPastel else BgWhite)
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(
                                brush = when (notif.type) {
                                    NotifType.LIKE -> Brush.linearGradient(listOf(PinkPastel, PinkPastel))
                                    NotifType.MATCH -> pinkGradient
                                    NotifType.SUPER_LIKE -> Brush.linearGradient(
                                        listOf(AccentBlue.copy(0.2f), AccentBlue.copy(0.1f))
                                    )
                                    NotifType.MESSAGE -> Brush.linearGradient(
                                        listOf(BgGray, BgGray)
                                    )
                                    NotifType.VISIT -> Brush.linearGradient(
                                        listOf(AccentYellow.copy(0.2f), AccentYellow.copy(0.1f))
                                    )
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (notif.type) {
                                NotifType.LIKE      -> Icons.Filled.Favorite
                                NotifType.MATCH     -> Icons.Filled.Celebration
                                NotifType.SUPER_LIKE-> Icons.Filled.Star
                                NotifType.MESSAGE   -> Icons.Filled.ChatBubble
                                NotifType.VISIT     -> Icons.Filled.RemoveRedEye
                            },
                            contentDescription = null,
                            tint = when (notif.type) {
                                NotifType.LIKE      -> PrimaryPink
                                NotifType.MATCH     -> Color.White
                                NotifType.SUPER_LIKE-> AccentBlue
                                NotifType.MESSAGE   -> PrimaryPink
                                NotifType.VISIT     -> AccentYellow
                            },
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(notif.title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextDark)
                        Text(notif.body, fontSize = 12.sp, color = TextMedium)
                    }
                    Text(notif.time, fontSize = 10.sp, color = TextLight)
                }
                Divider(color = DividerColor)
            }
        }
    }
}

// ── Sorry Screen ──────────────────────────────────────────────────
@Composable
fun SorryScreen(onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(BgWhite),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
            Box(
                modifier = Modifier.size(120.dp).clip(CircleShape).background(PinkPastel),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.HeartBroken, null, tint = PrimaryPink, modifier = Modifier.size(60.dp))
            }
            Spacer(Modifier.height(24.dp))
            Text("Oops! 💔", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = PrimaryPink)
            Spacer(Modifier.height(8.dp))
            Text("No profiles found in your area.\nTry expanding your search distance.",
                fontSize = 15.sp, color = TextMedium, textAlign = TextAlign.Center, lineHeight = 22.sp)
            Spacer(Modifier.height(32.dp))
            PinkButton("Start Swiping", onClick = onRetry)
        }
    }
}

// ── Settings Screen ───────────────────────────────────────────────
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(BgWhite)) {
        TopBarWithBack("Settings", onBack)
        Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(horizontal = 16.dp)) {
            SettingsSection("Account") {
                SettingsToggle("ID", "123456", Icons.Outlined.Badge)
                SettingsToggle("Membership", "Free", Icons.Outlined.WorkspacePremium)
                SettingsToggle("Edit Profile", "", Icons.Outlined.Edit)
                SettingsToggle("Change Password", "", Icons.Outlined.Lock)
            }
            SettingsSection("Preferences") {
                var notif by remember { mutableStateOf(true) }
                var location by remember { mutableStateOf(true) }
                var darkMode by remember { mutableStateOf(false) }
                SettingsSwitch("Push Notifications", notif, Icons.Outlined.Notifications) { notif = it }
                SettingsSwitch("Location Services", location, Icons.Outlined.LocationOn) { location = it }
                SettingsSwitch("Display Settings", darkMode, Icons.Outlined.DarkMode) { darkMode = it }
            }
            SettingsSection("Support") {
                SettingsToggle("Privacy Policy", "", Icons.Outlined.Policy)
                SettingsToggle("Terms of Service", "", Icons.Outlined.Description)
                SettingsToggle("Contact Support", "", Icons.Outlined.Support)
            }
            Spacer(Modifier.height(16.dp))
            PinkButton("Log Out", onClick = {})
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SettingsSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextLight,
            modifier = Modifier.padding(bottom = 8.dp))
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BgCard)
        ) {
            Column(modifier = Modifier.padding(horizontal = 4.dp)) { content() }
        }
    }
}

@Composable
private fun SettingsToggle(label: String, value: String, icon: ImageVector) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = PrimaryPink, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(12.dp))
        Text(label, fontSize = 14.sp, color = TextDark, modifier = Modifier.weight(1f))
        if (value.isNotEmpty()) Text(value, fontSize = 13.sp, color = TextLight)
        Spacer(Modifier.width(4.dp))
        Icon(Icons.Filled.ChevronRight, null, tint = TextLight, modifier = Modifier.size(18.dp))
    }
    Divider(modifier = Modifier.padding(start = 44.dp), color = DividerColor)
}

@Composable
private fun SettingsSwitch(label: String, checked: Boolean, icon: ImageVector, onToggle: (Boolean) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = PrimaryPink, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(12.dp))
        Text(label, fontSize = 14.sp, color = TextDark, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = PrimaryPink))
    }
    Divider(modifier = Modifier.padding(start = 44.dp), color = DividerColor)
}

// ── About Screen ──────────────────────────────────────────────────
@Composable
fun AboutScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(BgWhite).verticalScroll(rememberScrollState())) {
        TopBarWithBack("About", onBack)
        Box(modifier = Modifier.fillMaxWidth().padding(32.dp),
            contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AppLogo(size = 80.dp)
                Spacer(Modifier.height(12.dp))
                Text("Dating App", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Text("Version 1.0.0", fontSize = 13.sp, color = TextLight)
            }
        }
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            listOf("Privacy Policy", "Terms of Service", "Open Source Licenses",
                "Rate Us", "Share App", "Contact Us").forEach { item ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 14.dp).clickable {},
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(item, fontSize = 15.sp, color = TextDark, modifier = Modifier.weight(1f))
                    Icon(Icons.Filled.ChevronRight, null, tint = TextLight)
                }
                Divider(color = DividerColor)
            }
        }
    }
}

// ── Talk Time Screen ──────────────────────────────────────────────
@Composable
fun TalkTimeScreen(onBack: () -> Unit, onNavigatePayment: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(BgWhite)) {
        TopBarWithBack("Talk Time", onBack)
        Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(16.dp)) {
            // Balance card
            Box(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(pinkGradient)
                    .padding(20.dp)
            ) {
                Column {
                    Text("Your Balance", fontSize = 13.sp, color = Color.White.copy(0.8f))
                    Text("₹ 50", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(Modifier.height(8.dp))
                    Text("Talk time remaining: 25 minutes", fontSize = 12.sp, color = Color.White.copy(0.8f))
                }
            }
            Spacer(Modifier.height(20.dp))
            Text("Recent Activity", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextDark)
            Spacer(Modifier.height(12.dp))
            listOf(
                Triple("Jessica Parker", "5 min call", "₹10"),
                Triple("Priya Sharma",   "12 min call", "₹24"),
                Triple("Ananya Gupta",   "3 min call", "₹6"),
            ).forEach { (name, duration, cost) ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(42.dp).clip(CircleShape).background(PinkPastel),
                        contentAlignment = Alignment.Center) {
                        Icon(Icons.Filled.Phone, null, tint = PrimaryPink, modifier = Modifier.size(20.dp))
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextDark)
                        Text(duration, fontSize = 12.sp, color = TextLight)
                    }
                    Text(cost, fontWeight = FontWeight.Bold, color = PrimaryPink)
                }
                Divider(color = DividerColor)
            }
            Spacer(Modifier.height(20.dp))
            PinkButton("Add Talk Time", onClick = onNavigatePayment, icon = Icons.Filled.Add)
        }
    }
}

// ── Payment Screen ────────────────────────────────────────────────
@Composable
fun PaymentScreen(onBack: () -> Unit, onPay: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(BgWhite)) {
        TopBarWithBack("Payment", onBack)
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(16.dp)) {
            // Plan cards
            Text("Choose a Plan", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextDark)
            Spacer(Modifier.height(16.dp))
            listOf(
                Triple("Basic",   "₹99 / month",  listOf("10 likes/day", "5 super likes", "See who liked you")),
                Triple("Gold",    "₹299 / month", listOf("Unlimited likes", "15 super likes", "Rewind last swipe", "Passport")),
                Triple("Platinum","₹499 / month", listOf("Everything in Gold", "Message before match", "Priority likes", "1 free boost/week")),
            ).forEachIndexed { i, (name, price, features) ->
                var selected by remember { mutableStateOf(i == 1) }
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
                        .clickable { selected = true }
                        .border(2.dp, if (i == 1) PrimaryPink else BorderColor, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = if (i == 1) PinkPastel else BgWhite)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextDark)
                                if (i == 1) {
                                    Spacer(Modifier.width(8.dp))
                                    Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(
                                        PrimaryPink
                                    )
                                        .padding(horizontal = 6.dp, vertical = 2.dp)) {
                                        Text("Popular", fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                            Text(price, color = PrimaryPink, fontWeight = FontWeight.SemiBold)
                            features.forEach { Text("• $it", fontSize = 12.sp, color = TextMedium) }
                        }
                        RadioButton(selected = i == 1, onClick = {},
                            colors = RadioButtonDefaults.colors(selectedColor = PrimaryPink))
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Text("Payment Method", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextDark)
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf("Credit Card", "UPI", "Wallet").forEachIndexed { i, m ->
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(12.dp))
                            .background(if (i == 0) PinkPastel else BgGray)
                            .border(if (i == 0) 2.dp else 0.dp, PrimaryPink, RoundedCornerShape(12.dp))
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        Text(m, fontSize = 13.sp, color = if (i == 0) PrimaryPink else TextMedium,
                            fontWeight = if (i == 0) FontWeight.SemiBold else FontWeight.Normal)
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = BgCard)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.CreditCard, null, tint = TextMedium)
                    Spacer(Modifier.width(12.dp))
                    Text("•••• •••• •••• 4242", color = TextDark, fontSize = 14.sp, modifier = Modifier.weight(1f))
                    Text("Add New", color = PrimaryPink, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
        Column(modifier = Modifier.padding(16.dp)) {
            PinkButton("Pay Now - ₹299", onClick = onPay, icon = Icons.Filled.Lock)
        }
    }
}
