package com.droid.datingapp.model

import kotlinx.serialization.Serializable

// ── Navigation ────────────────────────────────────────────────────
sealed class Screen(val route: String) {
    object Splash         : Screen("splash")
    object Onboarding     : Screen("onboarding")
    object Login          : Screen("login")
    object Register       : Screen("register")
    object PhoneEntry     : Screen("phone_entry")
    object OtpVerify      : Screen("otp_verify")
    object EmailEntry     : Screen("email_entry")
    object EmailOtp       : Screen("email_otp")
    object ForgotPassword : Screen("forgot_password")
    object CreatePassword : Screen("create_password")
    object Congratulations: Screen("congratulations")
    object LookingFor     : Screen("looking_for")
    object Interests      : Screen("interests")
    object UploadPhoto    : Screen("upload_photo")
    object EnableLocation : Screen("enable_location")
    object SearchFriends  : Screen("search_friends")
    object Home           : Screen("home")
    object SwipeCard      : Screen("swipe_card")
    object Match          : Screen("match/{userId}") {
        fun createRoute(userId: String) = "match/$userId"
    }
    object Filter         : Screen("filter")
    object Matches        : Screen("matches")
    object Messages       : Screen("messages")
    object Chat           : Screen("chat/{userId}") {
        fun createRoute(userId: String) = "chat/$userId"
    }
    object EnableNotif    : Screen("enable_notif")
    object Notifications  : Screen("notifications")
    object Sorry          : Screen("sorry")
    object Profile        : Screen("profile")
    object TalkTime       : Screen("talk_time")
    object Payment        : Screen("payment")
    object Settings       : Screen("settings")
    object About          : Screen("about")
}

// ── User / Profile ────────────────────────────────────────────────
@Serializable
data class UserProfile(
    val id: String,
    val name: String,
    val age: Int,
    val distance: Int,       // km
    val bio: String = "",
    val photoUrls: List<String> = emptyList(),
    val interests: List<String> = emptyList(),
    val lookingFor: String = "Relationship",
    val location: String = "",
    val isPremium: Boolean = false,
    val isOnline: Boolean = false,
    val lastSeen: String = "",
    val height: String = "",
    val education: String = "",
    val work: String = ""
)

// ── Message ───────────────────────────────────────────────────────
@Serializable
data class Message(
    val id: String,
    val senderId: String,
    val text: String,
    val timestamp: String,
    val isRead: Boolean = false,
    val type: MessageType = MessageType.TEXT
)

enum class MessageType { TEXT, IMAGE, EMOJI }

// ── Chat preview ──────────────────────────────────────────────────
data class ChatPreview(
    val user: UserProfile,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int = 0
)

// ── Notification ──────────────────────────────────────────────────
data class AppNotification(
    val id: String,
    val title: String,
    val body: String,
    val time: String,
    val type: NotifType = NotifType.LIKE
)

enum class NotifType { LIKE, MATCH, MESSAGE, SUPER_LIKE, VISIT }

// ── Looking-for options ───────────────────────────────────────────
val lookingForOptions = listOf(
    "Relationship", "Friendship", "Casual Dating", "Marriage", "Something Fun", "Not sure yet"
)

// ── Interest tags ─────────────────────────────────────────────────
val allInterests = listOf(
    "Travel", "Music", "Movies", "Cooking", "Fitness", "Reading",
    "Gaming", "Photography", "Art", "Dancing", "Yoga", "Hiking",
    "Coffee", "Wine", "Dogs", "Cats", "Sports", "Foodie",
    "Netflix", "Meditation", "Fashion", "Technology", "Nature", "Volunteering"
)

// ── Sample data ───────────────────────────────────────────────────
object SampleUsers {
    val profiles = listOf(
        UserProfile(
            id = "1", name = "Jessica Parker", age = 23, distance = 2,
            bio = "Love adventures and good coffee ☕ Looking for someone to explore the city with!",
            photoUrls = listOf(
                "https://images.unsplash.com/photo-1529626455594-4ff0802cfb7e?w=600&q=80",
                "https://images.unsplash.com/photo-1488716820095-cbe80883c496?w=600&q=80",
            ),
            interests = listOf("Travel", "Coffee", "Yoga", "Photography"),
            location = "Mumbai", isOnline = true, height = "5'6\"", education = "MBA"
        ),
        UserProfile(
            id = "2", name = "Priya Sharma", age = 25, distance = 5,
            bio = "Foodie & travel enthusiast 🌍 Let's go on an adventure!",
            photoUrls = listOf(
                "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?w=600&q=80",
                "https://images.unsplash.com/photo-1502823403499-6ccfcf4fb453?w=600&q=80",
            ),
            interests = listOf("Cooking", "Travel", "Music", "Fitness"),
            location = "Delhi", isOnline = false, height = "5'4\""
        ),
        UserProfile(
            id = "3", name = "Ananya Gupta", age = 22, distance = 3,
            bio = "Artist by heart, engineer by degree 🎨",
            photoUrls = listOf(
                "https://images.unsplash.com/photo-1531746020798-e6953c6e8e04?w=600&q=80",
            ),
            interests = listOf("Art", "Gaming", "Movies", "Reading"),
            location = "Bangalore", isOnline = true
        ),
        UserProfile(
            id = "4", name = "Sneha Patel", age = 27, distance = 8,
            bio = "Yoga lover & nature enthusiast 🌿 Swipe right if you love sunsets!",
            photoUrls = listOf(
                "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=600&q=80",
            ),
            interests = listOf("Yoga", "Nature", "Meditation", "Dogs"),
            location = "Pune", isPremium = true
        ),
        UserProfile(
            id = "5", name = "Meera Nair", age = 24, distance = 1,
            bio = "Dance is my therapy 💃 Looking for a partner in crime!",
            photoUrls = listOf(
                "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=600&q=80",
            ),
            interests = listOf("Dancing", "Music", "Fashion", "Foodie"),
            location = "Chennai", isOnline = true
        ),
    )

    val chatPreviews = listOf(
        ChatPreview(profiles[0], "Hey! How are you? 😊", "2m ago", 2),
        ChatPreview(profiles[1], "That sounds amazing!", "15m ago", 0),
        ChatPreview(profiles[2], "Let's meet this weekend?", "1h ago", 1),
        ChatPreview(profiles[3], "I love that place too! 🌿", "3h ago", 0),
        ChatPreview(profiles[4], "You're so funny 😄", "Yesterday", 0),
    )

    val notifications = listOf(
        AppNotification("1", "Jessica liked you!", "She's 2 km away", "2m ago", NotifType.LIKE),
        AppNotification("2", "It's a Match! 🎉", "You and Priya matched", "1h ago", NotifType.MATCH),
        AppNotification("3", "Ananya super liked you!", "Don't miss this!", "3h ago", NotifType.SUPER_LIKE),
        AppNotification("4", "New message", "Sneha: Hey! 👋", "Yesterday", NotifType.MESSAGE),
        AppNotification("5", "Meera visited your profile", "Check her out!", "2d ago", NotifType.VISIT),
    )
}
