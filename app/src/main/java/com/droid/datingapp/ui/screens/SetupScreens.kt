package com.droid.datingapp.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.droid.datingapp.model.allInterests
import com.droid.datingapp.model.lookingForOptions
import com.dating.app.ui.components.*
import com.droid.datingapp.ui.theme.BgGray
import com.droid.datingapp.ui.theme.BgWhite
import com.droid.datingapp.ui.theme.BorderColor
import com.droid.datingapp.ui.theme.PinkPastel
import com.droid.datingapp.ui.theme.PrimaryPink
import com.droid.datingapp.ui.theme.TextDark
import com.droid.datingapp.ui.theme.TextMedium

// ── Looking For Screen ────────────────────────────────────────────
@Composable
fun LookingForScreen(onContinue: () -> Unit, onBack: () -> Unit) {
    var selected by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().background(BgWhite)) {
        TopBarWithBack("I'm Looking For...", onBack)
        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("What type of relationship are you looking for?",
                fontSize = 14.sp, color = TextMedium, lineHeight = 20.sp)
            Spacer(Modifier.height(8.dp))
            lookingForOptions.forEach { option ->
                val sel = selected == option
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (sel) PinkPastel else BgGray)
                        .border(2.dp, if (sel) PrimaryPink else Color.Transparent, RoundedCornerShape(14.dp))
                        .clickable { selected = option }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(option, fontWeight = if (sel) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (sel) PrimaryPink else TextDark, fontSize = 15.sp)
                    if (sel) Icon(Icons.Filled.CheckCircle, null, tint = PrimaryPink, modifier = Modifier.size(22.dp))
                }
            }
            Spacer(Modifier.height(24.dp))
            PinkButton("Continue", onClick = onContinue, enabled = selected.isNotEmpty())
        }
    }
}

// ── Interests Screen ──────────────────────────────────────────────
@Composable
fun InterestsScreen(onContinue: () -> Unit, onBack: () -> Unit) {
    val selected = remember { mutableStateListOf<String>() }

    Column(modifier = Modifier.fillMaxSize().background(BgWhite)) {
        TopBarWithBack("Your Interests", onBack)
        Column(modifier = Modifier.weight(1f).padding(horizontal = 24.dp)) {
            Text("Select at least 3 interests to help find your best matches",
                fontSize = 14.sp, color = TextMedium, lineHeight = 20.sp)
            Spacer(Modifier.height(16.dp))
            FlowRow(allInterests, selected) { tag ->
                if (tag in selected) selected.remove(tag) else selected.add(tag)
            }
        }
        Column(modifier = Modifier.padding(24.dp)) {
            Text("${selected.size} selected", color = TextMedium, fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            Spacer(Modifier.height(8.dp))
            PinkButton("Continue", onClick = onContinue, enabled = selected.size >= 3)
        }
    }
}

@Composable
private fun FlowRow(items: List<String>, selected: List<String>, onToggle: (String) -> Unit) {
    val rows = items.chunked(3)
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        rows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { item ->
                    InterestChip(item, selected = item in selected, onClick = { onToggle(item) })
                }
            }
        }
    }
}

// ── Upload Photo Screen ───────────────────────────────────────────
@Composable
fun UploadPhotoScreen(onContinue: () -> Unit, onBack: () -> Unit) {
    var hasPhoto by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize().background(BgWhite)) {
        TopBarWithBack("Upload Your Photo", onBack)
        Column(
            modifier = Modifier.weight(1f).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Add your best photos to get more matches!",
                fontSize = 14.sp, color = TextMedium
            )
            Spacer(Modifier.height(24.dp))

            // Main photo slot
            Box(
                modifier = Modifier.size(200.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(PinkPastel)
                    .border(2.dp, if (hasPhoto) PrimaryPink else BorderColor, RoundedCornerShape(20.dp))
                    .clickable { hasPhoto = true },
                contentAlignment = Alignment.Center
            ) {
                if (hasPhoto) {
                    AsyncImage(
                        "https://images.unsplash.com/photo-1529626455594-4ff0802cfb7e?w=400&q=80",
                        null, contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(20.dp))
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.CameraAlt, null, tint = PrimaryPink, modifier = Modifier.size(48.dp))
                        Spacer(Modifier.height(8.dp))
                        Text("Tap to upload", color = PrimaryPink, fontWeight = FontWeight.Medium)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Extra photo slots grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(170.dp)
            ) {
                items(6) {
                    Box(
                        modifier = Modifier.aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(PinkPastel)
                            .border(1.dp, BorderColor, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Add, null, tint = PrimaryPink.copy(0.5f), modifier = Modifier.size(28.dp))
                    }
                }
            }
        }
        Column(modifier = Modifier.padding(24.dp)) {
            PinkButton("Continue", onClick = onContinue)
        }
    }
}

// ── Enable Location Screen ────────────────────────────────────────
@Composable
fun EnableLocationScreen(onEnable: () -> Unit, onSkip: () -> Unit, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(BgWhite),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarWithBack("Enable Your Location", onBack)
        Spacer(Modifier.height(40.dp))

        // Map pin illustration
        Box(
            modifier = Modifier.size(180.dp)
                .clip(CircleShape)
                .background(PinkPastel),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.LocationOn, null, tint = PrimaryPink, modifier = Modifier.size(80.dp))
        }

        Spacer(Modifier.height(32.dp))
        Text("Allow Location Access", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextDark)
        Spacer(Modifier.height(10.dp))
        Text(
            "We use your location to show you people\nnearby and find your perfect match",
            fontSize = 14.sp, color = TextMedium, textAlign = TextAlign.Center, lineHeight = 22.sp,
            modifier = Modifier.padding(horizontal = 40.dp)
        )
        Spacer(Modifier.weight(1f))
        Column(modifier = Modifier.padding(24.dp)) {
            PinkButton("Allow My Location", onClick = onEnable, icon = Icons.Filled.MyLocation)
            Spacer(Modifier.height(12.dp))
            OutlinePinkButton("Skip for now", onClick = onSkip)
        }
    }
}

// ── Search Friends Screen ─────────────────────────────────────────
@Composable
fun SearchFriendsScreen(onContinue: () -> Unit, onBack: () -> Unit) {
    var phone by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize().background(BgWhite),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarWithBack("Search Friends", onBack)
        Spacer(Modifier.height(32.dp))

        Box(
            modifier = Modifier.size(140.dp).clip(CircleShape).background(PinkPastel),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.People, null, tint = PrimaryPink, modifier = Modifier.size(64.dp))
        }
        Spacer(Modifier.height(20.dp))
        Text("Find Friends from Contacts", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)
        Text("Connect with people you already know",
            fontSize = 14.sp, color = TextMedium, modifier = Modifier.padding(top = 6.dp))
        Spacer(Modifier.height(32.dp))
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            DatingTextField(phone, { phone = it }, "Phone number or email",
                leadingIcon = Icons.Filled.Search, keyboardType = KeyboardType.Phone)
            Spacer(Modifier.height(16.dp))
            PinkButton("Allow Access to Contacts", onClick = onContinue, icon = Icons.Filled.Contacts)
            Spacer(Modifier.height(12.dp))
            OutlinePinkButton("Skip", onClick = onContinue)
        }
    }
}
