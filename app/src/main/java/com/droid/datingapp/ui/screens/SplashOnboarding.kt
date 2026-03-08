package com.dating.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.dating.app.ui.components.*
import com.droid.datingapp.ui.theme.BgWhite
import com.droid.datingapp.ui.theme.PinkSoft
import com.droid.datingapp.ui.theme.PrimaryPink
import com.droid.datingapp.ui.theme.TextMedium
import kotlinx.coroutines.delay

// ── Splash Screen ─────────────────────────────────────────────────
@Composable
fun SplashScreen(onFinished: () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
        delay(2500L)
        onFinished()
    }

    Box(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(listOf(Color(0xFFFFF0F5), Color(0xFFFFCCE8), Color(0xFFFFAAD5)))
        ),
        contentAlignment = Alignment.Center
    ) {
        // decorative hearts
        HeartDecor()

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(800)) + scaleIn(tween(800))
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AppLogo(size = 100.dp)
                Spacer(Modifier.height(20.dp))
                Text(
                    "Find Your\nPerfect Match",
                    fontSize = 30.sp, fontWeight = FontWeight.Bold,
                    color = PrimaryPink, textAlign = TextAlign.Center, lineHeight = 38.sp
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Connect with people who share\nyour passion for life",
                    fontSize = 15.sp, color = TextMedium,
                    textAlign = TextAlign.Center, lineHeight = 22.sp
                )
            }
        }
    }
}

@Composable
private fun HeartDecor() {
    Box(Modifier.fillMaxSize()) {
        listOf(
            Pair(0.1f to 0.12f, 40.dp), Pair(0.8f to 0.08f, 28.dp),
            Pair(0.05f to 0.7f, 20.dp), Pair(0.88f to 0.65f, 36.dp),
            Pair(0.45f to 0.05f, 24.dp), Pair(0.7f to 0.85f, 18.dp),
        ).forEach { (pos, size) ->
            Icon(
                Icons.Filled.Favorite, null,
                tint = PrimaryPink.copy(alpha = 0.15f),
                modifier = Modifier.fillMaxSize(pos.first).size(size)
                    .align(Alignment.TopStart)
                    .offset(
                        x = (pos.first * 360).dp,
                        y = (pos.second * 680).dp
                    )
            )
        }
    }
}

// ── Onboarding Screen ─────────────────────────────────────────────
private data class OnboardingPage(val image: String, val title: String, val subtitle: String)

private val pages = listOf(
    OnboardingPage(
        "https://images.unsplash.com/photo-1543807535-eceef0bc6599?w=600&q=80",
        "Let's Try It",
        "Swipe right to like, swipe left to pass.\nFind someone who makes your heart skip a beat."
    ),
    OnboardingPage(
        "https://images.unsplash.com/photo-1516589178581-6cd7833ae3b2?w=600&q=80",
        "It's a Match!",
        "When two hearts connect, magic happens.\nStart chatting and see where it leads."
    ),
    OnboardingPage(
        "https://images.unsplash.com/photo-1529333166437-7750a6dd5a70?w=600&q=80",
        "Real Connections",
        "Verified profiles, safe messaging and\ngenuine people looking for love."
    ),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(onLogin: () -> Unit, onRegister: () -> Unit) {
    val pagerState = rememberPagerState { pages.size }

    Column(modifier = Modifier.fillMaxSize().background(BgWhite)) {
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            val p = pages[page]
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = p.image, contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier.fillMaxSize().background(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.5f to Color.Transparent,
                            1f to Color(0xF5FFFFFF)
                        )
                    )
                )
                Column(
                    modifier = Modifier.align(Alignment.BottomCenter).padding(horizontal = 32.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(p.title, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = PrimaryPink)
                    Spacer(Modifier.height(10.dp))
                    Text(p.subtitle, fontSize = 15.sp, color = TextMedium, textAlign = TextAlign.Center, lineHeight = 22.sp)
                }
            }
        }

        // Pager dots
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pages.size) { i ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (i == pagerState.currentPage) 20.dp else 8.dp, 8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(if (i == pagerState.currentPage) PrimaryPink else PinkSoft)
                )
            }
        }

        Column(modifier = Modifier.padding(horizontal = 24.dp).padding(bottom = 32.dp)) {
            PinkButton("Sign Up", onClick = onRegister)
            Spacer(Modifier.height(12.dp))
            OutlinePinkButton("Sign In", onClick = onLogin)
            Spacer(Modifier.height(12.dp))
            DividerWithText("or continue with")
            Spacer(Modifier.height(12.dp))
            SocialLoginRow()
        }
    }
}
