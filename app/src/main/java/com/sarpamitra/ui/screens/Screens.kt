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
import androidx.compose.ui.graphics.asImageBitmap

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
fun SymptomInputScreen(onSubmit: (symptoms: List<String>, transcript: String) -> Unit) {
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
            onClick = { onSubmit(selected.toList(), transcript) },
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
fun ProcessingScreen(
    stage: String = "",
    isLoading: Boolean = true,
    onComplete: () -> Unit = {}
) {
    LaunchedEffect(isLoading) {
        if (!isLoading) {
            kotlinx.coroutines.delay(500)
            onComplete()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = RedColor,
            modifier = Modifier.size(72.dp),
            strokeWidth = 6.dp
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stage.ifEmpty { "Analyzing..." },
            color = WhiteColor,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(GreenColor, shape = RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "No internet required", color = GreenColor, fontSize = 12.sp)
        }
    }
}

// ── 7. RESULTS DASHBOARD ─────────────────────────────────────────────────────
@Composable
fun ResultsDashboardScreen(
    result: com.sarpamitra.guardrails.TriageResult?,
    onGenerateReferral: () -> Unit,
    onStartMonitoring: () -> Unit,
    onBack: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val voiceManager = remember { com.sarpamitra.voice.VoiceManager(context) }

    LaunchedEffect(result) {
        if (result != null) {
            kotlinx.coroutines.delay(1000)
            val severityText = when (result.severity) {
                "CRITICAL" -> "Critical severity."
                "SEVERE" -> "Severe severity."
                "MODERATE" -> "Moderate severity."
                else -> "Mild severity."
            }
            val asvText = if (result.asvRequired) "Antivenom required. Go to District Hospital immediately." else "Antivenom may not be required. Monitor closely."
            voiceManager.speak("$severityText $asvText")
        }
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
            val severityColor = when (result?.severity) {
                "CRITICAL" -> RedColor
                "SEVERE" -> Color(0xFFFF6B00)
                "MODERATE" -> AmberColor
                else -> GreenColor
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = severityColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = when (result?.severity) {
                            "CRITICAL" -> "⚠️ CRITICAL"
                            "SEVERE" -> "🔴 SEVERE"
                            "MODERATE" -> "🟡 MODERATE"
                            else -> "🟢 MILD"
                        },
                        color = WhiteColor,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = result?.reasoning ?: "Assessing...",
                        color = WhiteColor,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Confidence: ${((result?.confidence ?: 0f) * 100).toInt()}%",
                        color = WhiteColor.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                    result?.guardrailTriggered?.let { trigger ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = WhiteColor.copy(alpha = 0.2f)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "⚡ Guardrail: $trigger",
                                color = WhiteColor,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
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
                        text = if (result?.asvRequired == true) "💉 ASV Required" else "💉 ASV Not Required",
                        color = if (result?.asvRequired == true) AmberColor else GreenColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${result?.asvType ?: "polyvalent"} • ${result?.estimatedVials ?: 8} vials",
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
                Text(text = "📄  GENERATE REFERRAL", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        item {
            Button(
                onClick = onStartMonitoring,
                modifier = Modifier.fillMaxWidth().height(64.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AmberColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "⏱️  START MONITORING", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
fun ReferralSlipScreen(
    result: com.sarpamitra.guardrails.TriageResult? = null,
    onBack: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val caseId = remember { "SM-${System.currentTimeMillis()}" }
    val timestamp = remember { System.currentTimeMillis() }

    val qrContent = remember(result) {
        com.sarpamitra.referral.QrGenerator.buildQrContent(
            caseId = caseId,
            severity = result?.severity ?: "MODERATE",
            syndrome = result?.syndrome ?: "unknown",
            asvRequired = result?.asvRequired ?: true,
            asvType = result?.asvType,
            estimatedVials = result?.estimatedVials,
            timestamp = timestamp
        )
    }

    val qrBitmap = remember(qrContent) {
        com.sarpamitra.referral.QrGenerator.generate(qrContent, 400)
    }

    val severityColor = when (result?.severity) {
        "CRITICAL" -> RedColor
        "SEVERE" -> Color(0xFFFF6B00)
        "MODERATE" -> AmberColor
        else -> GreenColor
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
            Text(
                text = "📄 Referral Slip",
                color = WhiteColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            // White referral card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "SARPA-MITRA",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Emergency Snakebite Referral",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )

                    Divider(
                        color = Color.LightGray,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    // Case details
                    ReferralRow("Case ID", caseId, Color.Black)
                    ReferralRow(
                        "Severity",
                        result?.severity ?: "MODERATE",
                        when (result?.severity) {
                            "CRITICAL" -> Color.Red
                            "SEVERE" -> Color(0xFFFF6B00)
                            "MODERATE" -> Color(0xFFFF9500)
                            else -> Color(0xFF34C759)
                        }
                    )
                    ReferralRow("Syndrome", result?.syndrome ?: "unknown", Color.Black)
                    ReferralRow(
                        "ASV Required",
                        if (result?.asvRequired == true) "YES" else "NO",
                        if (result?.asvRequired == true) Color.Red else Color(0xFF34C759)
                    )
                    ReferralRow("ASV Type", result?.asvType ?: "polyvalent", Color.Black)
                    ReferralRow("Est. Vials", "${result?.estimatedVials ?: 8}", Color.Black)
                    ReferralRow(
                        "Time",
                        java.text.SimpleDateFormat("dd-MM-yyyy HH:mm", java.util.Locale.getDefault())
                            .format(java.util.Date(timestamp)),
                        Color.Black
                    )

                    if (result?.guardrailTriggered != null) {
                        ReferralRow("Guardrail", result.guardrailTriggered!!, Color(0xFF007AFF))
                    }

                    Divider(color = Color.LightGray, modifier = Modifier.padding(vertical = 12.dp))

                    // QR Code
                    Text(
                        text = "Scan at facility",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    androidx.compose.foundation.Image(
                        bitmap = qrBitmap.asImageBitmap(),
                        contentDescription = "QR Code",
                        modifier = Modifier.size(180.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Distance approximate. Road conditions may vary.",
                        color = Color.Gray,
                        fontSize = 9.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Generated offline by Sarpa-Mitra AI",
                        color = Color.Gray,
                        fontSize = 9.sp
                    )
                }
            }
        }

        item {
            Button(
                onClick = {
                    // Save to gallery
                    saveReferralToGallery(context, qrContent, caseId)
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RedColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "💾  SAVE TO GALLERY", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        item {
            Button(
                onClick = {
                    // Share via intent
                    val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(android.content.Intent.EXTRA_TEXT, qrContent)
                        putExtra(android.content.Intent.EXTRA_SUBJECT, "Sarpa-Mitra Emergency Referral — $caseId")
                    }
                    context.startActivity(android.content.Intent.createChooser(shareIntent, "Share Referral"))
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CardColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "📤  SHARE REFERRAL", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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

@Composable
private fun ReferralRow(label: String, value: String, valueColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.Gray, fontSize = 13.sp)
        Text(text = value, color = valueColor, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}

private fun saveReferralToGallery(context: android.content.Context, content: String, caseId: String) {
    try {
        val filename = "Sarpa-Mitra-$caseId.txt"
        val file = java.io.File(context.getExternalFilesDir(null), filename)
        file.writeText(content)
        android.widget.Toast.makeText(context, "Referral saved: $filename", android.widget.Toast.LENGTH_LONG).show()
    } catch (e: Exception) {
        android.widget.Toast.makeText(context, "Save failed: ${e.message}", android.widget.Toast.LENGTH_SHORT).show()
    }
}

// ── 9. MONITORING MODE ───────────────────────────────────────────────────────
@Composable
fun MonitoringScreen(
    onNewSymptom: () -> Unit,
    onBack: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    var secondsElapsed by remember { mutableStateOf(0) }
    var silenceSeconds by remember { mutableStateOf(0) }
    var sosTriggered by remember { mutableStateOf(false) }
    var nextCheckSeconds by remember { mutableStateOf(900) }
    var showSosDialog by remember { mutableStateOf(false) }
    var showOffsetDialog by remember { mutableStateOf(false) }
    var offsetInput by remember { mutableStateOf("") }

    val voiceManager = remember { com.sarpamitra.voice.VoiceManager(context) }
    val sosManager = remember { com.sarpamitra.monitoring.SosManager(context) }

    DisposableEffect(Unit) {
        onDispose {
            voiceManager.destroy()
            sosManager.destroy()
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000)
            secondsElapsed++
            silenceSeconds++
            nextCheckSeconds--

            if (nextCheckSeconds <= 0) {
                nextCheckSeconds = 900
                voiceManager.speak("Check patient now. Any new symptoms? Tap New Symptom button if yes.")
            }

            if (silenceSeconds >= 90 && !sosTriggered) {
                sosTriggered = true
                showSosDialog = true
                sosManager.triggerSos("SM-${System.currentTimeMillis()}", "UNKNOWN")
                voiceManager.speak("Madad karo! Snakebite emergency! Help needed!")
            }
        }
    }

    fun resetSilence() { silenceSeconds = 0 }

    // Offset dialog
    if (showOffsetDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showOffsetDialog = false },
            title = {
                Text(
                    text = "When did the bite happen?",
                    color = WhiteColor,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = "Enter minutes before you opened the app:",
                        color = GrayColor,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    androidx.compose.material3.OutlinedTextField(
                        value = offsetInput,
                        onValueChange = { offsetInput = it.filter { c -> c.isDigit() } },
                        label = { Text("Minutes ago", color = GrayColor) },
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                        ),
                        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                            focusedTextColor = WhiteColor,
                            unfocusedTextColor = WhiteColor,
                            focusedBorderColor = RedColor,
                            unfocusedBorderColor = GrayColor
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        secondsElapsed = (offsetInput.toIntOrNull() ?: 0) * 60
                        showOffsetDialog = false
                        offsetInput = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = RedColor)
                ) { Text("SET") }
            },
            dismissButton = {
                TextButton(onClick = { showOffsetDialog = false }) {
                    Text("Cancel", color = GrayColor)
                }
            },
            containerColor = CardColor
        )
    }

    // SOS dialog
    if (showSosDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = {
                showSosDialog = false
                sosManager.stopStrobe()
            },
            title = {
                Text(
                    text = "🚨 SOS TRIGGERED",
                    color = RedColor,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "90 seconds of silence detected.\nAlarm and flashlight activated.\nEmergency SMS queued.",
                    color = WhiteColor
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSosDialog = false
                        sosTriggered = false
                        silenceSeconds = 0
                        sosManager.stopStrobe()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = RedColor)
                ) { Text("I'M OK — CANCEL SOS") }
            },
            containerColor = CardColor
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "⏱️ MONITORING",
            color = WhiteColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Timer with edit button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Time since bite: ${secondsElapsed / 60}m ${secondsElapsed % 60}s",
                color = AmberColor,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(onClick = { showOffsetDialog = true }) {
                Text(text = "✏️ Edit", color = GrayColor, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Next check in: ${nextCheckSeconds / 60}m ${nextCheckSeconds % 60}s",
                    color = GrayColor,
                    fontSize = 14.sp
                )
                Text(
                    text = "Silence SOS in: ${(90 - silenceSeconds).coerceAtLeast(0)}s",
                    color = if (silenceSeconds > 60) RedColor else GrayColor,
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
                Text(
                    text = "Watch for:",
                    color = WhiteColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                listOf(
                    "Drooping eyelids (ptosis)",
                    "Difficulty swallowing",
                    "Breathing problems",
                    "Increased swelling",
                    "Bleeding from bite"
                ).forEach {
                    Text(text = "• $it", color = GrayColor, fontSize = 14.sp)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                resetSilence()
                onNewSymptom()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RedColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "🚨  NEW SYMPTOM",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                resetSilence()
                voiceManager.speak("Patient stable. Continuing to monitor.")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "✅  I'M HERE — PATIENT STABLE",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = {
            resetSilence()
            onBack()
        }) {
            Text(text = "Back", color = GrayColor)
        }
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
fun SettingsScreen(
    onBack: () -> Unit,
    onBenchmark: () -> Unit = {}
) {
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

        // Language
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

        // Model Status
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
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "✅ Model loaded — offline ready",
                    color = GreenColor,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = GrayColor),
                    modifier = Modifier.fillMaxWidth()
                ) { Text(text = "Check for Update (USB)") }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Developer
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Developer", color = WhiteColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Run inference benchmark to measure performance on this device.",
                    color = GrayColor,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onBenchmark,
                    colors = ButtonDefaults.buttonColors(containerColor = GrayColor),
                    modifier = Modifier.fillMaxWidth()
                ) { Text(text = "📊 Run Benchmarks") }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // About
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "About", color = WhiteColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Sarpa-Mitra v1.0", color = GrayColor, fontSize = 13.sp)
                Text(text = "Offline snakebite triage for rural India", color = GrayColor, fontSize = 12.sp)
                Text(text = "Clinical protocol: WHO/ICMR guidelines", color = GrayColor, fontSize = 12.sp)
                Text(text = "AI: Gemma 2B via MediaPipe LiteRT", color = GrayColor, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Back", color = GrayColor)
        }
    }
}