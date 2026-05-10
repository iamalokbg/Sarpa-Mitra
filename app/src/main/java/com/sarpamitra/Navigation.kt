package com.sarpamitra

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarpamitra.ui.screens.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    object CaseDetail : Screen("case_detail/{caseId}/{severity}/{syndrome}/{timestamp}") {
        fun createRoute(caseId: String, severity: String, syndrome: String, timestamp: String) =
            "case_detail/$caseId/$severity/$syndrome/$timestamp"
    }
}

@androidx.compose.runtime.Composable
fun SarpaMitraNavigation() {
    val navController = rememberNavController()
    val viewModel: TriageViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.EmergencyLaunch.route
    ) {
        composable(Screen.EmergencyLaunch.route) {
            val context = androidx.compose.ui.platform.LocalContext.current
            var showActiveSessionDialog by remember { mutableStateOf(false) }
            var activeSession by remember { mutableStateOf<com.sarpamitra.data.local.entity.PatientSession?>(null) }
            val scope = rememberCoroutineScope()

            fun checkAndNavigate(onNoActiveSession: () -> Unit) {
                scope.launch {
                    val twoHoursAgo = System.currentTimeMillis() - (2 * 60 * 60 * 1000)
                    val db = com.sarpamitra.data.local.AppDatabase.getInstance(context)
                    val session = withContext(Dispatchers.IO) {
                        db.patientSessionDao().getActiveSession(twoHoursAgo)
                    }
                    if (session != null) {
                        activeSession = session
                        showActiveSessionDialog = true
                    } else {
                        onNoActiveSession()
                    }
                }
            }

            if (showActiveSessionDialog && activeSession != null) {
                val session = activeSession!!
                val dateLabel = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
                    .format(java.util.Date(session.timestamp))
                AlertDialog(
                    onDismissRequest = { showActiveSessionDialog = false },
                    title = {
                        Text(
                            text = "⚠️ Active Case Found",
                            color = Color(0xFFFF9500),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    text = {
                        Text(
                            text = "Case ${session.sessionId}\nStarted at: $dateLabel\nSeverity: ${session.severity}\n\nResume this case or delete to start new.",
                            color = Color.White
                        )
                    },
                    confirmButton = {
                        // RESUME
                        Button(
                            onClick = {
                                showActiveSessionDialog = false
                                activeSession = null
                                navController.navigate(Screen.Monitoring.route)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9500))
                        ) { Text("▶️ Resume Case") }
                    },
                    dismissButton = {
                        Row {
                            TextButton(
                                onClick = {
                                    scope.launch {
                                        withContext(Dispatchers.IO) {
                                            com.sarpamitra.data.local.AppDatabase.getInstance(context)
                                                .patientSessionDao()
                                                .deleteSession(session.sessionId)
                                        }
                                        showActiveSessionDialog = false
                                        activeSession = null
                                        navController.navigate(Screen.SymptomInput.route)
                                    }
                                }
                            ) { Text("🗑️ Delete & New", color = Color(0xFFFF3B30)) }
                            TextButton(
                                onClick = { showActiveSessionDialog = false }
                            ) { Text("Cancel", color = Color(0xFF8E8E93)) }
                        }
                    },
                    containerColor = Color(0xFF2C2C2E)
                )
            }

            EmergencyLaunchScreen(
                onSpeakClick = { checkAndNavigate { navController.navigate(Screen.SymptomInput.route) } },
                onCameraClick = { checkAndNavigate { navController.navigate(Screen.SnakePhotoCapture.route) } },
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
                title = "📷 Photograph the Bite Wound",
                overlayColor = Color(0xFFFF3B30),
                onPhotoCaptured = { path ->
                    viewModel.setBitePhoto(path)
                    navController.navigate(Screen.SymptomInput.route)
                },
                onSkip = { navController.navigate(Screen.SymptomInput.route) },
                onValidatePhoto = { path -> viewModel.validateWoundPhoto(path) }
            )
        }

        composable(Screen.BiteSiteCapture.route) {
            CameraXScreen(
                title = "📷 Photograph the Bite Site",
                overlayColor = Color(0xFFFF9500),
                onPhotoCaptured = { path ->
                    viewModel.setBitePhoto(path)
                    navController.navigate(Screen.SymptomInput.route)
                },
                onSkip = { navController.navigate(Screen.SymptomInput.route) },
                onValidatePhoto = { path -> viewModel.validateWoundPhoto(path) }
            )
        }

        composable(Screen.SymptomInput.route) {
            SymptomInputScreen(
                onSubmit = { symptoms, transcript, age ->
                    viewModel.setSymptoms(symptoms)
                    viewModel.setTranscript(transcript)
                    age?.let { viewModel.setAgeYears(it) }
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
                onSettingsClick = { navController.navigate(Screen.Settings.route) },
                onCaseClick = { caseId, severity, syndrome, timestamp ->
                    navController.navigate(
                        Screen.CaseDetail.createRoute(caseId, severity, syndrome, timestamp)
                    )
                }
            )
        }

        composable(
            route = Screen.CaseDetail.route,
            arguments = listOf(
                androidx.navigation.navArgument("caseId") { type = androidx.navigation.NavType.StringType },
                androidx.navigation.navArgument("severity") { type = androidx.navigation.NavType.StringType },
                androidx.navigation.navArgument("syndrome") { type = androidx.navigation.NavType.StringType },
                androidx.navigation.navArgument("timestamp") { type = androidx.navigation.NavType.StringType }
            )
        ) { backStackEntry ->
            CaseDetailScreen(
                caseId = backStackEntry.arguments?.getString("caseId") ?: "",
                severity = backStackEntry.arguments?.getString("severity") ?: "",
                syndrome = backStackEntry.arguments?.getString("syndrome") ?: "",
                timestamp = backStackEntry.arguments?.getString("timestamp") ?: "",
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                onBenchmark = { navController.navigate(Screen.Benchmark.route) }
            )
        }

        composable(Screen.Benchmark.route) {
            BenchmarkScreen(onBack = { navController.popBackStack() })
        }
    }
}