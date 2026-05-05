package com.sarpamitra

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarpamitra.ui.screens.*

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

    object Benchmark : Screen("benchmark")
}

@Composable
fun SarpaMitraNavigation() {
    val navController = rememberNavController()
    val viewModel: TriageViewModel = viewModel()
    val state by viewModel.state.collectAsState()

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
        composable(Screen.SnakePhotoCapture.route) {
            CameraXScreen(
                title = "📷 Photograph the Snake",
                overlayColor = RedColor,
                onPhotoCaptured = { path ->
                    viewModel.setSnakePhoto(path)
                    navController.navigate(Screen.SymptomInput.route)
                },
                onSkip = { navController.navigate(Screen.SymptomInput.route) }
            )
        }
        composable(Screen.Benchmark.route) {
            BenchmarkScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.BiteSiteCapture.route) {
            CameraXScreen(
                title = "📷 Photograph the Bite Site",
                overlayColor = AmberColor,
                onPhotoCaptured = { path ->
                    viewModel.setBitePhoto(path)
                    navController.navigate(Screen.SymptomInput.route)
                },
                onSkip = { navController.navigate(Screen.SymptomInput.route) }
            )
        }
        composable(Screen.SymptomInput.route) {
            SymptomInputScreen(
                onSubmit = { symptoms, transcript ->
                    viewModel.setSymptoms(symptoms)
                    viewModel.setTranscript(transcript)
                    viewModel.runTriage()
                    navController.navigate(Screen.Processing.route)
                }
            )
        }
        composable(Screen.Processing.route) {
            ProcessingScreen(
                stage = state.stage,
                isLoading = state.isLoading,
                onComplete = {
                    if (!state.isLoading && state.result != null) {
                        navController.navigate(Screen.ResultsDashboard.route)
                    }
                }
            )
        }
        composable(Screen.ResultsDashboard.route) {
            ResultsDashboardScreen(
                result = state.result,
                onGenerateReferral = { navController.navigate(Screen.ReferralSlip.route) },
                onStartMonitoring = { navController.navigate(Screen.Monitoring.route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.ReferralSlip.route) {
            ReferralSlipScreen(
                result = state.result,
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
                onBack = { navController.popBackStack() },
                onBenchmark = { navController.navigate(Screen.Benchmark.route) }
            )
        }
    }
}