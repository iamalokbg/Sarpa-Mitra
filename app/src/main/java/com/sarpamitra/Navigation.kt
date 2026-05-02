package com.sarpamitra

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarpamitra.ui.screens.*
import com.sarpamitra.ui.screens.CameraXScreen
import com.sarpamitra.ui.screens.RedColor
import com.sarpamitra.ui.screens.AmberColor

sealed class Screen(val route: String) {
    object EmergencyLaunch : Screen("emergency_launch")
    object SnakeAvailability : Screen("snake_availability")
    object SnakePhotoCapture : Screen("snake_photo_capture")
    object BiteSiteCapture : Screen("bite_site_capture")
    object SymptomInput : Screen("symptom_input")
    object Processing : Screen("processing")
    object ResultsDashboard : Screen("results_dashboard")
    object ReferralSlip : Screen("referral_slip")
    object Monitoring : Screen("monitoring")
    object History : Screen("history")
    object Settings : Screen("settings")
}

@Composable
fun SarpaMitraNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.EmergencyLaunch.route
    ) {
        composable(Screen.EmergencyLaunch.route) {
            EmergencyLaunchScreen(
                onSpeakClick = { navController.navigate(Screen.SnakeAvailability.route) },
                onCameraClick = { navController.navigate(Screen.SnakeAvailability.route) },
                onHistoryClick = { navController.navigate(Screen.History.route) }
            )
        }
        composable(Screen.SnakeAvailability.route) {
            SnakeAvailabilityScreen(
                onHaveSnake = { navController.navigate(Screen.SnakePhotoCapture.route) },
                onNoSnake = { navController.navigate(Screen.BiteSiteCapture.route) }
            )
        }

        // --- Updated Camera Routes ---
        composable(Screen.SnakePhotoCapture.route) {
            CameraXScreen(
                title = "📷 Photograph the Snake",
                overlayColor = RedColor,
                onPhotoCaptured = { path ->
                    // Logic to store the path can be added here later
                    navController.navigate(Screen.SymptomInput.route)
                },
                onSkip = { navController.navigate(Screen.SymptomInput.route) }
            )
        }

        composable(Screen.BiteSiteCapture.route) {
            CameraXScreen(
                title = "📷 Photograph the Bite Site",
                overlayColor = AmberColor,
                onPhotoCaptured = { path ->
                    // Logic to store the path can be added here later
                    navController.navigate(Screen.SymptomInput.route)
                },
                onSkip = { navController.navigate(Screen.SymptomInput.route) }
            )
        }
        // -----------------------------

        composable(Screen.SymptomInput.route) {
            SymptomInputScreen(
                onSubmit = { navController.navigate(Screen.Processing.route) }
            )
        }
        composable(Screen.Processing.route) {
            ProcessingScreen(
                onComplete = { navController.navigate(Screen.ResultsDashboard.route) }
            )
        }
        composable(Screen.ResultsDashboard.route) {
            ResultsDashboardScreen(
                onGenerateReferral = { navController.navigate(Screen.ReferralSlip.route) },
                onStartMonitoring = { navController.navigate(Screen.Monitoring.route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.ReferralSlip.route) {
            ReferralSlipScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Monitoring.route) {
            MonitoringScreen(
                onNewSymptom = { navController.navigate(Screen.SymptomInput.route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.History.route) {
            HistoryScreen(
                onBack = { navController.popBackStack() },
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}