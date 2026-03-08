package com.dating.app.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.dating.app.ui.components.*
import com.droid.datingapp.model.SampleUsers
import com.droid.datingapp.model.Screen
import com.droid.datingapp.model.UserProfile
import com.droid.datingapp.ui.theme.AccentBlue
import com.droid.datingapp.ui.theme.AccentGreen
import com.droid.datingapp.ui.theme.AccentYellow
import com.droid.datingapp.ui.theme.BgWhite
import com.droid.datingapp.ui.theme.BoostPurple
import com.droid.datingapp.ui.theme.DislikeRed
import com.droid.datingapp.ui.theme.LikeGreen
import com.droid.datingapp.ui.theme.PinkPastel
import com.droid.datingapp.ui.theme.PinkSoft
import com.droid.datingapp.ui.theme.PrimaryPink
import com.droid.datingapp.ui.theme.SuperBlue
import com.droid.datingapp.ui.theme.TextDark
import com.droid.datingapp.ui.theme.TextLight

// ── Home Screen ───────────────────────────────────────────────────
@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    onFilter: () -> Unit
) {
    val profiles = remember { SampleUsers.profiles.toMutableStateList() }

    MainScaffold(
        currentRoute = Screen.Home.route,
        onNavigate = onNavigate
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).background(BgWhite)
        ) {
            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppLogo(size = 36.dp)
                Spacer(Modifier.weight(1f))
                Text("Discover", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Spacer(Modifier.weight(1f))
                IconButton(onClick = onFilter) {
                    Icon(Icons.Filled.Tune, null, tint = PrimaryPink)
                }
            }

            // Cards
            if (profiles.isEmpty()) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.SearchOff, null, tint = PinkSoft, modifier = Modifier.size(80.dp))
                        Spacer(Modifier.height(16.dp))
                        Text("No more profiles nearby", color = TextLight, fontSize = 16.sp)
                        Spacer(Modifier.height(12.dp))
                        PinkButton("Expand Search", onClick = {}, modifier = Modifier.width(200.dp))
                    }
                }
            } else {
                Box(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                    profiles.reversed().forEachIndexed { i, profile ->
                        val isTop = i == profiles.size - 1
                        if (isTop) {
                            SwipeableCard(
                                profile = profile,
                                onLike = {
                                    profiles.remove(profile)
                                    onNavigate(Screen.Match.createRoute(profile.id))
                                },
                                onDislike = { profiles.remove(profile) },
                                onSuperLike = { profiles.remove(profile) }
                            )
                        } else {
                            // Background cards
                            val offset = (profiles.size - 1 - i) * 8
                            Card(
                                modifier = Modifier.fillMaxSize()
                                    .padding(top = offset.dp, start = (offset / 2).dp, end = (offset / 2).dp),
                                shape = RoundedCornerShape(24.dp),
                                colors = CardDefaults.cardColors(containerColor = PinkPastel)
                            ) {}
                        }
                    }
                }
            }

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ActionBtn(Icons.Filled.Close, DislikeRed, 56.dp) {
                    if (profiles.isNotEmpty()) profiles.removeAt(profiles.size - 1)
                }
                ActionBtn(Icons.Filled.Star, AccentYellow, 44.dp) {}
                ActionBtn(Icons.Filled.Favorite, LikeGreen, 64.dp) {
                    if (profiles.isNotEmpty()) {
                        val p = profiles.last()
                        profiles.removeAt(profiles.size - 1)
                        onNavigate(Screen.Match.createRoute(p.id))
                    }
                }
                ActionBtn(Icons.Filled.Bolt, SuperBlue, 44.dp) {}
                ActionBtn(Icons.Filled.WorkspacePremium, BoostPurple, 44.dp) {}
            }
        }
    }
}

@Composable
fun ActionBtn(icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, size: Dp, onClick: () -> Unit) {
    Box(
        modifier = Modifier.size(size).clip(CircleShape)
            .shadow(6.dp, CircleShape)
            .background(Color.White)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, null, tint = color, modifier = Modifier.size(size * 0.45f))
    }
}

// ── Swipeable Card ────────────────────────────────────────────────
@Composable
fun SwipeableCard(
    profile: UserProfile,
    onLike: () -> Unit,
    onDislike: () -> Unit,
    onSuperLike: () -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    val rotation = (offsetX / 25f).coerceIn(-15f, 15f)
    val likeAlpha = (offsetX / 150f).coerceIn(0f, 1f)
    val dislikeAlpha = (-offsetX / 150f).coerceIn(0f, 1f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
            .rotate(rotation)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        when {
                            offsetX > 300f  -> onLike()
                            offsetX < -300f -> onDislike()
                            offsetY < -300f -> onSuperLike()
                            else -> { offsetX = 0f; offsetY = 0f }
                        }
                    }
                ) { _, drag ->
                    offsetX += drag.x
                    offsetY += drag.y
                }
            }
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Box(Modifier.fillMaxSize()) {
                // Profile photo
                AsyncImage(
                    model = profile.photoUrls.firstOrNull(),
                    contentDescription = profile.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Gradient overlay
                Box(
                    modifier = Modifier.fillMaxSize().background(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.55f to Color.Transparent,
                            1f to Color(0xDD000000)
                        )
                    )
                )
                // Like / Dislike stamps
                if (likeAlpha > 0.1f) {
                    LikeStamp(alpha = likeAlpha, isLike = true)
                }
                if (dislikeAlpha > 0.1f) {
                    LikeStamp(alpha = dislikeAlpha, isLike = false)
                }
                // Info
                Column(
                    modifier = Modifier.align(Alignment.BottomStart).padding(20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("${profile.name}, ${profile.age}", fontSize = 24.sp,
                            fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(Modifier.width(8.dp))
                        if (profile.isPremium) {
                            Icon(Icons.Filled.Verified, null, tint = AccentBlue, modifier = Modifier.size(22.dp))
                        }
                        if (profile.isOnline) {
                            Spacer(Modifier.width(6.dp))
                            Box(Modifier.size(10.dp).clip(CircleShape).background(AccentGreen))
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.LocationOn, null, tint = Color.White.copy(0.8f), modifier = Modifier.size(14.dp))
                        Text("${profile.distance} km away", fontSize = 13.sp, color = Color.White.copy(0.8f))
                    }
                    Spacer(Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        profile.interests.take(3).forEach { interest ->
                            Box(
                                modifier = Modifier.clip(RoundedCornerShape(12.dp))
                                    .background(Color.White.copy(0.2f))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(interest, fontSize = 11.sp, color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LikeStamp(alpha: Float, isLike: Boolean) {
    Box(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        contentAlignment = if (isLike) Alignment.TopStart else Alignment.TopEnd
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(3.dp, if (isLike) LikeGreen else DislikeRed, RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                if (isLike) "LIKE" else "NOPE",
                color = if (isLike) LikeGreen else DislikeRed,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.graphicsLayer(alpha = alpha)
            )
        }
    }
}
