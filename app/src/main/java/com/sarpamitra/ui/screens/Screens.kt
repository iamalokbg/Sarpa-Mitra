package com.sarpamitra.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val BgColor = Color(0xFF1C1C1E)
val CardColor = Color(0xFF2C2C2E)
val RedColor = Color(0xFFFF3B30)
val AmberColor = Color(0xFFFF9500)
val GreenColor = Color(0xFF34C759)
val WhiteColor = Color.White
val GrayColor = Color(0xFF8E8E93)

// ── 1. EMERGENCY LAUNCH ──────────────────────────────────────────────────────
@Composable
fun EmergencyLaunchScreen(
    onSpeakClick: () -> Unit,
    onCameraClick: () -> Unit,
    onHistoryClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🐍",
                fontSize = 64.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "SARPA-MITRA",
                color = WhiteColor,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Snakebite Emergency",
                color = GrayColor,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = onSpeakClick,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(80.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RedColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "🎤  HOLD TO SPEAK",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onCameraClick,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(80.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CardColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "📷  TAP FOR CAMERA",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        TextButton(
            onClick = onHistoryClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text(text = "History", color = GrayColor)
        }
    }
}

// ── 2. SNAKE AVAILABILITY ────────────────────────────────────────────────────
@Composable
fun SnakeAvailabilityScreen(
    onHaveSnake: () -> Unit,
    onNoSnake: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Did you see the snake?",
            color = WhiteColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(48.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clickable { onHaveSnake() },
            colors = CardDefaults.cardColors(containerColor = CardColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "🐍  I saw the snake",
                    color = WhiteColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clickable { onNoSnake() },
            colors = CardDefaults.cardColors(containerColor = CardColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "❓  Snake ran away",
                    color = WhiteColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ── 3. SNAKE PHOTO CAPTURE ───────────────────────────────────────────────────
@Composable
fun SnakePhotoCaptureScreen(
    onPhotoCaptured: () -> Unit,
    onSkip: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "📷 Take Snake Photo",
            color = WhiteColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .border(2.dp, GrayColor, RoundedCornerShape(16.dp))
                .background(CardColor, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Camera Preview\n(CameraX — Phase 4)", color = GrayColor, textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onPhotoCaptured,
            modifier = Modifier.fillMaxWidth().height(64.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RedColor)
        ) {
            Text(text = "CAPTURE", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(12.dp))
        TextButton(onClick = onSkip) {
            Text(text = "Skip — describe symptoms instead", color = GrayColor)
        }
    }
}

// ── 4. BITE SITE CAPTURE ─────────────────────────────────────────────────────
@Composable
fun BiteSiteCaptureScreen(
    onPhotoCaptured: () -> Unit,
    onSkip: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "📷 Photo of Bite Site",
            color = WhiteColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Place bite site in the box below",
            color = GrayColor,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .border(2.dp, AmberColor, RoundedCornerShape(16.dp))
                .background(CardColor, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Bite Site\nCamera Preview", color = GrayColor, textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onPhotoCaptured,
            modifier = Modifier.fillMaxWidth().height(64.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AmberColor)
        ) {
            Text(text = "CAPTURE BITE SITE", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(12.dp))
        TextButton(onClick = onSkip) {
            Text(text = "Skip camera", color = GrayColor)
        }
    }
}

// ── 5. SYMPTOM INPUT ─────────────────────────────────────────────────────────
@Composable
fun SymptomInputScreen(onSubmit: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val symptoms = listOf("Swelling", "Bleeding", "Ptosis\n(drooping eyelid)", "Breathing\ndifficulty", "Pain", "Nausea")
    val selected = remember { mutableStateListOf<String>() }
    var transcript by remember { mutableStateOf("") }
    var isListening by remember { mutableStateOf(false) }
    var statusMsg by remember { mutableStateOf("Hold button to speak symptoms") }

    val voiceManager = remember { com.sarpamitra.voice.VoiceManager(context) }

    val isHindi by remember {
        val prefs = context.getSharedPreferences("sarpa_prefs", android.content.Context.MODE_PRIVATE)
        mutableStateOf(prefs.getBoolean("is_hindi", true))
    }

    LaunchedEffect(isHindi) {
        voiceManager.setLanguage(isHindi)
    }

    var hasMicPermission by remember {
        mutableStateOf(
            androidx.core.content.ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.RECORD_AUDIO
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasMicPermission = granted
        if (!granted) statusMsg = "Microphone permission denied"
    }

    DisposableEffect(Unit) {
        onDispose { voiceManager.destroy() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "What symptoms?", color = WhiteColor, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        if (transcript.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "\"$transcript\"",
                    color = GreenColor,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        Button(
            onClick = {
                if (!hasMicPermission) {
                    permissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
                    return@Button
                }
                if (!isListening) {
                    isListening = true
                    statusMsg = "🎤 Listening..."
                    voiceManager.startListening(
                        onResult = { text ->
                            transcript = text
                            isListening = false
                            statusMsg = "Tap symptoms or submit"
                        },
                        onError = { error ->
                            statusMsg = error
                            isListening = false
                        }
                    )
                } else {
                    voiceManager.stopListening()
                    isListening = false
                    statusMsg = "Hold button to speak symptoms"
                }
            },
            modifier = Modifier.fillMaxWidth().height(80.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isListening) GreenColor else RedColor
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = when {
                    !hasMicPermission -> "🎤  TAP TO GRANT MIC"
                    isListening -> "🎤 LISTENING... (tap to stop)"
                    else -> "🎤  HOLD TO SPEAK"
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Language: ${if (isHindi) "Hindi 🇮🇳" else "English 🇬🇧"}  •  Change in Settings",
            color = GrayColor,
            fontSize = 11.sp
        )
        Text(text = statusMsg, color = GrayColor, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Or tap symptoms:", color = GrayColor, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(12.dp))

        val rows = symptoms.chunked(2)
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                row.forEach { symptom ->
                    val isSelected = selected.contains(symptom)
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(64.dp)
                            .clickable {
                                if (isSelected) selected.remove(symptom)
                                else selected.add(symptom)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) RedColor else CardColor
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = symptom,
                                color = WhiteColor,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth().height(64.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RedColor),
            enabled = selected.isNotEmpty() || transcript.isNotEmpty()
        ) {
            Text(text = "ANALYZE NOW", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}
// ── 6. PROCESSING ────────────────────────────────────────────────────────────
@Composable
fun ProcessingScreen(onComplete: () -> Unit) {
    val stages = listOf("Analyzing bite pattern...", "Cross-referencing symptoms...", "Applying clinical guardrails...", "Generating assessment...")
    var currentStage by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        stages.indices.forEach { i ->
            kotlinx.coroutines.delay(800)
            currentStage = i
        }
        kotlinx.coroutines.delay(1000)
        onComplete()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = RedColor, modifier = Modifier.size(72.dp), strokeWidth = 6.dp)
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = stages.getOrElse(currentStage) { "Processing..." }, color = WhiteColor, fontSize = 18.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(8.dp).background(GreenColor, shape = RoundedCornerShape(4.dp)))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "No internet required", color = GreenColor, fontSize = 12.sp)
        }
    }
}

// ── 7. RESULTS DASHBOARD ─────────────────────────────────────────────────────
@Composable
fun ResultsDashboardScreen(
    onGenerateReferral: () -> Unit,
    onStartMonitoring: () -> Unit,
    onBack: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val voiceManager = remember { com.sarpamitra.voice.VoiceManager(context) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1000)
        voiceManager.speak("Critical severity. Neurotoxic envenomation suspected. Antivenom required. Go to District Hospital immediately.")
    }

    DisposableEffect(Unit) {
        onDispose { voiceManager.destroy() }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = RedColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "⚠️ CRITICAL",
                        color = WhiteColor,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Neurotoxic envenomation suspected",
                        color = WhiteColor,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Confidence: 78%",
                        color = WhiteColor.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "💉 ASV Required",
                        color = AmberColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Polyvalent antivenom • 8-10 vials",
                        color = WhiteColor,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Administer at hospital only. Do NOT give at home.",
                        color = AmberColor,
                        fontSize = 12.sp
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🩹 First Aid",
                        color = WhiteColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    listOf(
                        "✅ Keep patient calm and still",
                        "✅ Immobilize the bitten limb",
                        "✅ Remove rings, watches near bite",
                        "❌ Do NOT cut or suck the bite",
                        "❌ Do NOT apply tourniquet",
                        "❌ Do NOT apply ice or heat"
                    ).forEach { instruction ->
                        Text(
                            text = instruction,
                            color = if (instruction.startsWith("✅")) GreenColor else RedColor,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🏥 Nearest Facilities",
                        color = WhiteColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF3A3A3C)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "⚠️ ASV stock NOT verified.",
                                color = AmberColor,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Go to District Hospital if possible.",
                                color = AmberColor,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    // District Hospital
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "🏛️ District Hospital", color = GreenColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Text(text = "HIGH probability • Recommended", color = GreenColor, fontSize = 12.sp)
                        }
                        Text(text = "12 km", color = GrayColor, fontSize = 13.sp)
                    }
                    Text(
                        text = "Distance approximate. Road conditions may vary.",
                        color = GrayColor,
                        fontSize = 10.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color(0xFF3A3A3C))
                    Spacer(modifier = Modifier.height(8.dp))

                    // Sub-District
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "🏥 Sub-District Hospital", color = AmberColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Medium-High probability", color = AmberColor, fontSize = 12.sp)
                        }
                        Text(text = "4 km", color = GrayColor, fontSize = 13.sp)
                    }
                    Text(
                        text = "Distance approximate. Road conditions may vary.",
                        color = GrayColor,
                        fontSize = 10.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color(0xFF3A3A3C))
                    Spacer(modifier = Modifier.height(8.dp))

                    // PHC
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "🏨 PHC", color = GrayColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Variable ⚠️ May be out of stock", color = GrayColor, fontSize = 12.sp)
                        }
                        Text(text = "4.2 km", color = GrayColor, fontSize = 13.sp)
                    }
                    Text(
                        text = "Distance approximate. Road conditions may vary.",
                        color = GrayColor,
                        fontSize = 10.sp
                    )
                }
            }
        }

        item {
            Button(
                onClick = onGenerateReferral,
                modifier = Modifier.fillMaxWidth().height(64.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RedColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "📄  GENERATE REFERRAL",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {
            Button(
                onClick = onStartMonitoring,
                modifier = Modifier.fillMaxWidth().height(64.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AmberColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "⏱️  START MONITORING",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {
            TextButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Back", color = GrayColor)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// ── 8. REFERRAL SLIP ─────────────────────────────────────────────────────────
@Composable
fun ReferralSlipScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = WhiteColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(text = "SARPA-MITRA REFERRAL", color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Divider(color = Color.Black, modifier = Modifier.padding(vertical = 8.dp))
                Text(text = "Case ID: SM-20260501-001", color = Color.Black, fontSize = 14.sp)
                Text(text = "Severity: CRITICAL", color = Color.Red, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(text = "Syndrome: Neurotoxic", color = Color.Black, fontSize = 14.sp)
                Text(text = "ASV: Polyvalent • 8-10 vials", color = Color.Black, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier.size(120.dp).background(Color.Gray).align(Alignment.CenterHorizontally),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "QR Code\n(Phase 9)", color = Color.White, textAlign = TextAlign.Center, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Distance approximate. Road conditions may vary.", color = Color.Gray, fontSize = 10.sp)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(64.dp), colors = ButtonDefaults.buttonColors(containerColor = RedColor)) {
            Text(text = "📤  SHARE REFERRAL", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onBack) { Text(text = "Back", color = GrayColor) }
    }
}

// ── 9. MONITORING MODE ───────────────────────────────────────────────────────
@Composable
fun MonitoringScreen(
    onNewSymptom: () -> Unit,
    onBack: () -> Unit
) {
    var secondsElapsed by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000)
            secondsElapsed++
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "⏱️ MONITORING", color = WhiteColor, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Time since bite: ${secondsElapsed / 60}m ${secondsElapsed % 60}s",
            color = AmberColor,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(32.dp))
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CardColor), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Watch for:", color = WhiteColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                listOf("Drooping eyelids (ptosis)", "Difficulty swallowing", "Breathing problems", "Increased swelling", "Bleeding").forEach {
                    Text(text = "• $it", color = GrayColor, fontSize = 14.sp)
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onNewSymptom,
            modifier = Modifier.fillMaxWidth().height(80.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RedColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "🚨  NEW SYMPTOM", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(12.dp))
        TextButton(onClick = onBack) { Text(text = "Back", color = GrayColor) }
    }
}

// ── 10. HISTORY ──────────────────────────────────────────────────────────────
@Composable
fun HistoryScreen(
    onBack: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val fakeCases = remember { mutableStateListOf(
        Triple("SM-001", "CRITICAL", "2026-05-01"),
        Triple("SM-002", "MODERATE", "2026-04-28"),
        Triple("SM-003", "MILD", "2026-04-15")
    ) }

    LaunchedEffect(Unit) {
        val db = com.sarpamitra.data.local.AppDatabase.getInstance(context)
        db.patientSessionDao().insert(
            com.sarpamitra.data.local.entity.PatientSession(
                sessionId = "SM-TEST-001",
                timestamp = System.currentTimeMillis(),
                biteLocation = "Left ankle",
                symptomsJson = """["swelling","ptosis"]""",
                snakePhotoPath = null,
                bitePhotoPath = null,
                severity = "CRITICAL",
                asvRequired = true,
                asvType = "polyvalent",
                estimatedVials = 10,
                urgency = "IMMEDIATE",
                confidence = 0.78f,
                reasoning = "Neurotoxic signs present",
                guardrailTriggered = "NEUROTOXIC_OVERRIDE",
                referralGenerated = false,
                syncStatus = "LOCAL"
            )
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Case History", color = WhiteColor, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            TextButton(onClick = onSettingsClick) { Text(text = "⚙️ Settings", color = GrayColor) }
        }
        Spacer(modifier = Modifier.height(16.dp))
        fakeCases.forEach { (id, severity, date) ->
            val color = when (severity) { "CRITICAL" -> RedColor; "MODERATE" -> AmberColor; else -> GreenColor }
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = CardColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(12.dp).background(color, shape = RoundedCornerShape(6.dp)))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = id, color = WhiteColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = "$severity • $date", color = GrayColor, fontSize = 13.sp)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        TextButton(onClick = onBack) { Text(text = "Back", color = GrayColor) }
    }
}

// ── 11. SETTINGS ─────────────────────────────────────────────────────────────
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val prefs = context.getSharedPreferences("sarpa_prefs", android.content.Context.MODE_PRIVATE)

    var hindiSelected by remember {
        mutableStateOf(prefs.getBoolean("is_hindi", true))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Settings", color = WhiteColor, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Language", color = WhiteColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = {
                            hindiSelected = true
                            prefs.edit().putBoolean("is_hindi", true).apply()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (hindiSelected) RedColor else GrayColor
                        )
                    ) { Text(text = "Hindi 🇮🇳") }
                    Button(
                        onClick = {
                            hindiSelected = false
                            prefs.edit().putBoolean("is_hindi", false).apply()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!hindiSelected) RedColor else GrayColor
                        )
                    ) { Text(text = "English 🇬🇧") }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Currently: ${if (hindiSelected) "Hindi" else "English"}",
                    color = GreenColor,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Model Status", color = WhiteColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "gemma-2b-it-cpu-int8.bin • 2.51 GB",
                    color = GreenColor,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = GrayColor)
                ) { Text(text = "Check for Update (USB)") }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        TextButton(onClick = onBack) {
            Text(text = "Back", color = GrayColor)
        }
    }
}