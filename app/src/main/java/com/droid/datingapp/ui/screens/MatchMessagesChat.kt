package com.dating.app.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.dating.app.ui.components.*
import com.droid.datingapp.model.ChatPreview
import com.droid.datingapp.model.SampleUsers
import com.droid.datingapp.model.Screen
import com.droid.datingapp.ui.theme.AccentGreen
import com.droid.datingapp.ui.theme.BgGray
import com.droid.datingapp.ui.theme.BgWhite
import com.droid.datingapp.ui.theme.DividerColor
import com.droid.datingapp.ui.theme.PrimaryPink
import com.droid.datingapp.ui.theme.PrimaryPinkDark
import com.droid.datingapp.ui.theme.TextDark
import com.droid.datingapp.ui.theme.TextLight
import com.droid.datingapp.ui.theme.TextMedium

// ── Match Celebration Screen ──────────────────────────────────────
@Composable
fun MatchScreen(userId: String, onMessage: () -> Unit, onKeepSwiping: () -> Unit) {
    val user = SampleUsers.profiles.find { it.id == userId } ?: SampleUsers.profiles.first()
    val currentUser = SampleUsers.profiles.last()

    Box(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(listOf(Color(0xFF1A0A12), PrimaryPinkDark, Color(0xFF1A0A12)))
        ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
            Spacer(Modifier.height(32.dp))
            Icon(Icons.Filled.Favorite, null, tint = LyfeGoldAlt, modifier = Modifier.size(48.dp))
            Spacer(Modifier.height(8.dp))
            Text("It's a Match!", fontSize = 38.sp, fontWeight = FontWeight.Black, color = Color.White)
            Text("Jake!", fontSize = 38.sp, fontWeight = FontWeight.Black, color = LyfeGoldAlt)
            Spacer(Modifier.height(8.dp))
            Text("You and ${user.name} have liked each other",
                fontSize = 15.sp, color = Color.White.copy(0.8f))
            Spacer(Modifier.height(32.dp))

            // Overlapping photos
            Box(modifier = Modifier.width(220.dp).height(120.dp)) {
                AsyncImage(
                    model = currentUser.photoUrls.firstOrNull(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(110.dp).clip(CircleShape)
                        .border(3.dp, PrimaryPink, CircleShape)
                        .align(Alignment.CenterStart)
                )
                AsyncImage(
                    model = user.photoUrls.firstOrNull(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(110.dp).clip(CircleShape)
                        .border(3.dp, Color.White, CircleShape)
                        .align(Alignment.CenterEnd)
                )
                Box(
                    modifier = Modifier.size(36.dp).clip(CircleShape)
                        .background(pinkGradient).align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Favorite, null, tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }

            Spacer(Modifier.height(40.dp))
            PinkButton("Send a Message", onClick = onMessage, icon = Icons.Filled.Chat)
            Spacer(Modifier.height(12.dp))
            OutlinedButton(
                onClick = onKeepSwiping,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(26.dp),
                border = BorderStroke(1.dp, Color.White.copy(0.4f))
            ) {
                Text("Keep Swiping", color = Color.White, fontWeight = FontWeight.Medium)
            }
        }
    }
}

private val LyfeGoldAlt = Color(0xFFFFD700)

// ── Filter Screen ─────────────────────────────────────────────────
@Composable
fun FilterScreen(onApply: () -> Unit, onBack: () -> Unit) {
    var ageRange by remember { mutableStateOf(18f..35f) }
    var distance by remember { mutableFloatStateOf(25f) }
    var selectedGender by remember { mutableStateOf("Women") }

    Column(modifier = Modifier.fillMaxSize().background(BgWhite)) {
        TopBarWithBack("Filters", onBack) {
            Text("Reset", color = PrimaryPink, fontSize = 14.sp, fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { })
        }
        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            FilterSection("Gender") {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    listOf("Women", "Men", "Everyone").forEach { g ->
                        val sel = selectedGender == g
                        Box(
                            modifier = Modifier.clip(RoundedCornerShape(20.dp))
                                .background(if (sel) pinkGradient else Brush.linearGradient(listOf(
                                    BgGray, BgGray
                                )))
                                .clickable { selectedGender = g }
                                .padding(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Text(g, color = if (sel) Color.White else TextMedium, fontSize = 13.sp,
                                fontWeight = if (sel) FontWeight.SemiBold else FontWeight.Normal)
                        }
                    }
                }
            }
            FilterSection("Age Range: ${ageRange.start.toInt()} - ${ageRange.endInclusive.toInt()}") {
                RangeSlider(
                    value = ageRange, onValueChange = { ageRange = it },
                    valueRange = 18f..60f,
                    colors = SliderDefaults.colors(activeTrackColor = PrimaryPink, thumbColor = PrimaryPink)
                )
            }
            FilterSection("Distance: ${distance.toInt()} km") {
                Slider(
                    value = distance, onValueChange = { distance = it },
                    valueRange = 1f..100f,
                    colors = SliderDefaults.colors(activeTrackColor = PrimaryPink, thumbColor = PrimaryPink)
                )
            }
            FilterSection("Interests") {
                val tags = listOf("Travel", "Music", "Fitness", "Cooking", "Art", "Gaming")
                val sel = remember { mutableStateListOf<String>() }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.horizontalScroll(rememberScrollState())) {
                    tags.forEach { t ->
                        InterestChip(t, t in sel) { if (t in sel) sel.remove(t) else sel.add(t) }
                    }
                }
            }
        }
        Column(modifier = Modifier.padding(24.dp)) {
            PinkButton("Apply Filters", onClick = onApply)
        }
    }
}

@Composable
private fun FilterSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = TextDark)
        Spacer(Modifier.height(10.dp))
        content()
        Divider(modifier = Modifier.padding(top = 12.dp), color = DividerColor)
    }
}

// ── Matches Screen ────────────────────────────────────────────────
@Composable
fun MatchesScreen(onNavigate: (String) -> Unit) {
    MainScaffold(Screen.Matches.route, onNavigate) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).background(BgWhite)) {
            Text("Matches", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextDark,
                modifier = Modifier.padding(20.dp))

            // New matches row
            Text("New Matches", fontSize = 13.sp, color = TextMedium,
                modifier = Modifier.padding(horizontal = 20.dp))
            Spacer(Modifier.height(12.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(SampleUsers.profiles) { user ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box {
                            AsyncImage(
                                model = user.photoUrls.firstOrNull(),
                                contentDescription = user.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(72.dp).clip(CircleShape)
                                    .border(2.dp, PrimaryPink, CircleShape)
                            )
                            if (user.isOnline) {
                                Box(Modifier.size(14.dp).clip(CircleShape).background(AccentGreen)
                                    .border(2.dp, Color.White, CircleShape)
                                    .align(Alignment.BottomEnd))
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(user.name.split(" ").first(), fontSize = 11.sp, color = TextMedium, maxLines = 1)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            Divider(color = DividerColor)

            // Messages list
            Text("Messages", fontSize = 13.sp, color = TextMedium,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp))

            LazyColumn {
                items(SampleUsers.chatPreviews) { chat ->
                    ChatPreviewRow(chat) {
                        onNavigate(Screen.Chat.createRoute(chat.user.id))
                    }
                }
            }
        }
    }
}

// ── Messages Screen ───────────────────────────────────────────────
@Composable
fun MessagesScreen(onNavigate: (String) -> Unit) {
    MainScaffold(Screen.Messages.route, onNavigate) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).background(BgWhite)) {
            Row(modifier = Modifier.fillMaxWidth().padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Messages", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Spacer(Modifier.weight(1f))
                Icon(Icons.Filled.Search, null, tint = TextMedium)
            }
            LazyColumn {
                items(SampleUsers.chatPreviews) { chat ->
                    ChatPreviewRow(chat) {
                        onNavigate(Screen.Chat.createRoute(chat.user.id))
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatPreviewRow(chat: ChatPreview, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            AsyncImage(
                model = chat.user.photoUrls.firstOrNull(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(56.dp).clip(CircleShape)
            )
            if (chat.user.isOnline) {
                Box(Modifier.size(12.dp).clip(CircleShape).background(AccentGreen)
                    .border(2.dp, Color.White, CircleShape).align(Alignment.BottomEnd))
            }
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(chat.user.name, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = TextDark)
            Text(chat.lastMessage, fontSize = 13.sp, color = TextLight, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(chat.time, fontSize = 11.sp, color = TextLight)
            if (chat.unreadCount > 0) {
                Spacer(Modifier.height(4.dp))
                Box(
                    modifier = Modifier.size(20.dp).clip(CircleShape).background(PrimaryPink),
                    contentAlignment = Alignment.Center
                ) {
                    Text(chat.unreadCount.toString(), fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// ── Chat Screen ───────────────────────────────────────────────────
@Composable
fun ChatScreen(userId: String, onBack: () -> Unit) {
    val user = SampleUsers.profiles.find { it.id == userId } ?: SampleUsers.profiles.first()
    var message by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            Pair(false, "Hey! I noticed we matched 😊"),
            Pair(true,  "Hi! Yes, I loved your profile!"),
            Pair(false, "You seem so interesting. Coffee sometime? ☕"),
            Pair(true,  "I'd love that! When are you free?"),
            Pair(false, "This weekend? I know a great place 🌸"),
        )
    }

    Column(modifier = Modifier.fillMaxSize().background(BgWhite)) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBackIosNew, null, tint = TextDark) }
            AsyncImage(
                model = user.photoUrls.firstOrNull(), contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(42.dp).clip(CircleShape)
            )
            Spacer(Modifier.width(10.dp))
            Column {
                Text(user.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextDark)
                Text(if (user.isOnline) "Online now" else "Last seen recently",
                    fontSize = 11.sp, color = if (user.isOnline) AccentGreen else TextLight
                )
            }
            Spacer(Modifier.weight(1f))
            IconButton(onClick = {}) { Icon(Icons.Filled.Videocam, null, tint = PrimaryPink) }
            IconButton(onClick = {}) { Icon(Icons.Filled.Phone, null, tint = PrimaryPink) }
        }
       Divider(color = DividerColor)

        // Messages
        LazyColumn(
            modifier = Modifier.weight(1f).padding(horizontal = 16.dp, vertical = 8.dp),
            reverseLayout = false
        ) {
            items(messages) { (isMe, text) ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
                ) {
                    if (!isMe) {
                        AsyncImage(
                            model = user.photoUrls.firstOrNull(), null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(32.dp).clip(CircleShape).align(Alignment.Bottom)
                        )
                        Spacer(Modifier.width(8.dp))
                    }
                    Box(
                        modifier = Modifier
                            .widthIn(max = 260.dp)
                            .clip(RoundedCornerShape(
                                topStart = 18.dp, topEnd = 18.dp,
                                bottomStart = if (isMe) 18.dp else 4.dp,
                                bottomEnd = if (isMe) 4.dp else 18.dp
                            ))
                            .background(if (isMe) pinkGradient else Brush.linearGradient(listOf(
                                BgGray, BgGray
                            )))
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        Text(text, color = if (isMe) Color.White else TextDark, fontSize = 14.sp)
                    }
                }
            }
        }

        // Input
        Divider(color = DividerColor)
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) { Icon(Icons.Outlined.AddCircleOutline, null, tint = PrimaryPink) }
            Box(
                modifier = Modifier.weight(1f).clip(RoundedCornerShape(24.dp))
                    .background(BgGray)
            ) {
                BasicTextField(
                    value = message, onValueChange = { message = it },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                    decorationBox = { inner ->
                        if (message.isEmpty()) Text("Type a message...", color = TextLight, fontSize = 14.sp)
                        inner()
                    }
                )
            }
            Spacer(Modifier.width(8.dp))
            Box(
                modifier = Modifier.size(44.dp).clip(CircleShape)
                    .background(if (message.isNotEmpty()) pinkGradient else Brush.linearGradient(listOf(
                        BgGray, BgGray
                    )))
                    .clickable {
                        if (message.isNotEmpty()) {
                            messages.add(Pair(true, message))
                            message = ""
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Send, null,
                    tint = if (message.isNotEmpty()) Color.White else TextLight,
                    modifier = Modifier.size(20.dp))
            }
        }
    }
}
