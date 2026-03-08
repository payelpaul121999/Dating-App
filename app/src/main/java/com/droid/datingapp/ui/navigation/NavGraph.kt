package com.droid.datingapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.droid.datingapp.model.Screen
import com.dating.app.ui.screens.*
import com.droid.datingapp.ui.screens.AboutScreen
import com.droid.datingapp.ui.screens.CongratulationsScreen
import com.droid.datingapp.ui.screens.CreatePasswordScreen
import com.droid.datingapp.ui.screens.EnableLocationScreen
import com.droid.datingapp.ui.screens.EnableNotificationsScreen
import com.droid.datingapp.ui.screens.ForgotPasswordScreen
import com.droid.datingapp.ui.screens.InterestsScreen
import com.droid.datingapp.ui.screens.LoginScreen
import com.droid.datingapp.ui.screens.LookingForScreen
import com.droid.datingapp.ui.screens.NotificationsScreen
import com.droid.datingapp.ui.screens.OtpScreen
import com.droid.datingapp.ui.screens.PaymentScreen
import com.droid.datingapp.ui.screens.PhoneEntryScreen
import com.droid.datingapp.ui.screens.ProfileScreen
import com.droid.datingapp.ui.screens.RegisterScreen
import com.droid.datingapp.ui.screens.SearchFriendsScreen
import com.droid.datingapp.ui.screens.SettingsScreen
import com.droid.datingapp.ui.screens.SorryScreen
import com.droid.datingapp.ui.screens.TalkTimeScreen
import com.droid.datingapp.ui.screens.UploadPhotoScreen

@Composable
fun DatingNavGraph(navController: NavHostController) {

    fun navigate(route: String) = navController.navigate(route) { launchSingleTop = true }
    fun back() = navController.popBackStack()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route) {
            SplashScreen { navigate(Screen.Onboarding.route) }
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onLogin = { navigate(Screen.Login.route) },
                onRegister = { navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLogin = { navigate(Screen.Home.route) },
                onRegister = { navigate(Screen.Register.route) },
                onForgot = { navigate(Screen.ForgotPassword.route) },
                onBack = { back() }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegister = { navigate(Screen.PhoneEntry.route) },
                onLogin = { navigate(Screen.Login.route) },
                onBack = { back() }
            )
        }

        composable(Screen.PhoneEntry.route) {
            PhoneEntryScreen(
                onContinue = { navigate(Screen.OtpVerify.route) },
                onBack = { back() }
            )
        }

        composable(Screen.OtpVerify.route) {
            OtpScreen(
                title = "Verification Code",
                subtitle = "Enter the 6-digit code sent to your mobile number",
                onVerify = { navigate(Screen.EmailEntry.route) },
                onBack = { back() }
            )
        }

        composable(Screen.EmailEntry.route) {
            // Re-using phone entry style for email confirmation
            PhoneEntryScreen(
                onContinue = { navigate(Screen.EmailOtp.route) },
                onBack = { back() }
            )
        }

        composable(Screen.EmailOtp.route) {
            OtpScreen(
                title = "Verify Email",
                subtitle = "Enter the 6-digit code sent to your email",
                onVerify = { navigate(Screen.Congratulations.route) },
                onBack = { back() }
            )
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onSend = { navigate(Screen.OtpVerify.route) },
                onBack = { back() }
            )
        }

        composable(Screen.CreatePassword.route) {
            CreatePasswordScreen(
                onSave = { navigate(Screen.Congratulations.route) },
                onBack = { back() }
            )
        }

        composable(Screen.Congratulations.route) {
            CongratulationsScreen {
                navigate(Screen.LookingFor.route)
            }
        }

        composable(Screen.LookingFor.route) {
            LookingForScreen(
                onContinue = { navigate(Screen.Interests.route) },
                onBack = { back() }
            )
        }

        composable(Screen.Interests.route) {
            InterestsScreen(
                onContinue = { navigate(Screen.UploadPhoto.route) },
                onBack = { back() }
            )
        }

        composable(Screen.UploadPhoto.route) {
            UploadPhotoScreen(
                onContinue = { navigate(Screen.EnableLocation.route) },
                onBack = { back() }
            )
        }

        composable(Screen.EnableLocation.route) {
            EnableLocationScreen(
                onEnable = { navigate(Screen.SearchFriends.route) },
                onSkip = { navigate(Screen.SearchFriends.route) },
                onBack = { back() }
            )
        }

        composable(Screen.SearchFriends.route) {
            SearchFriendsScreen(
                onContinue = { navigate(Screen.EnableNotif.route) },
                onBack = { back() }
            )
        }

        composable(Screen.EnableNotif.route) {
            EnableNotificationsScreen(
                onEnable = { navigate(Screen.Home.route) },
                onSkip = { navigate(Screen.Home.route) }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigate = { navigate(it) },
                onFilter = { navigate(Screen.Filter.route) }
            )
        }

        composable(Screen.Filter.route) {
            FilterScreen(
                onApply = { back() },
                onBack = { back() }
            )
        }

        composable(Screen.Match.route) { backStack ->
            val userId = backStack.arguments?.getString("userId") ?: ""
            MatchScreen(
                userId = userId,
                onMessage = { navigate(Screen.Chat.createRoute(userId)) },
                onKeepSwiping = { back() }
            )
        }

        composable(Screen.Matches.route) {
            MatchesScreen(onNavigate = { navigate(it) })
        }

        composable(Screen.Messages.route) {
            MessagesScreen(onNavigate = { navigate(it) })
        }

        composable(Screen.Chat.route) { backStack ->
            val userId = backStack.arguments?.getString("userId") ?: ""
            ChatScreen(userId = userId, onBack = { back() })
        }

        composable(Screen.Profile.route) {
            ProfileScreen(onNavigate = { navigate(it) })
        }

        composable(Screen.Settings.route) {
            SettingsScreen(onBack = { back() })
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen(onBack = { back() })
        }

        composable(Screen.TalkTime.route) {
            TalkTimeScreen(
                onBack = { back() },
                onNavigatePayment = { navigate(Screen.Payment.route) }
            )
        }

        composable(Screen.Payment.route) {
            PaymentScreen(
                onBack = { back() },
                onPay = { back() }
            )
        }

        composable(Screen.About.route) {
            AboutScreen(onBack = { back() })
        }

        composable(Screen.Sorry.route) {
            SorryScreen(onRetry = { back() })
        }
    }
}
