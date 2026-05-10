package com.sarpamitra.ui.screens

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
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
            Text(text = "🐍", fontSize = 64.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "SARPA-MITRA", color = WhiteColor, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text(text = "Snakebite Emergency", color = GrayColor, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = onSpeakClick,
                modifier = Modifier.fillMaxWidth(0.8f).height(80.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RedColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "🎤  HOLD TO SPEAK", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onCameraClick,
                modifier = Modifier.fillMaxWidth(0.8f).height(80.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CardColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "📷  TAP FOR CAMERA", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
        TextButton(
            onClick = onHistoryClick,
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
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
        modifier = Modifier.fillMaxSize().background(BgColor).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Can you photograph the wound?",
            color = WhiteColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "A photo helps assess swelling and tissue damage",
            color = GrayColor,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(48.dp))
        Card(
            modifier = Modifier.fillMaxWidth().height(120.dp).clickable { onHaveSnake() },
            colors = CardDefaults.cardColors(containerColor = RedColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "📷  Photograph the wound", color = WhiteColor, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth().height(120.dp).clickable { onNoSnake() },
            colors = CardDefaults.cardColors(containerColor = CardColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "🎤  Describe symptoms only", color = WhiteColor, fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
        modifier = Modifier.fillMaxSize().background(BgColor).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "📷 Photograph the Wound", color = WhiteColor, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .border(2.dp, GrayColor, RoundedCornerShape(16.dp))
                .background(CardColor, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Camera Preview", color = GrayColor, textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onPhotoCaptured,
            modifier = Modifier.fillMaxWidth().height(64.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RedColor)
        ) {
            Text(text = "CAPTURE WOUND PHOTO", fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
        modifier = Modifier.fillMaxSize().background(BgColor).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "📷 Photo of Bite Site", color = WhiteColor, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Place bite site in the box below", color = GrayColor, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .border(2.dp, AmberColor, RoundedCornerShape(16.dp))
                .background(CardColor, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Bite Site Camera Preview", color = GrayColor, textAlign = TextAlign.Center)
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
fun SymptomInputScreen(
    onSubmit: (symptoms: List<String>, transcript: String, age: Int?) -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val prefs = context.getSharedPreferences("sarpa_prefs", android.content.Context.MODE_PRIVATE)
    val isHindi = prefs.getBoolean("is_hindi", true)

    val symptoms = listOf(
        "Swelling", "Bleeding", "Ptosis\n(drooping eyelid)",
        "Breathing\ndifficulty", "Pain", "Nausea"
    )
    val selected = remember { mutableStateListOf<String>() }
    var transcript by remember { mutableStateOf("") }
    var isListening by remember { mutableStateOf(false) }
    var statusMsg by remember { mutableStateOf(if (isHindi) "बोलने के लिए बटन दबाएं" else "Hold button to speak symptoms") }
    var patientAge by remember { mutableStateOf("") }

    val voiceManager = remember { com.sarpamitra.voice.VoiceManager(context) }

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
        if (!granted) statusMsg = if (isHindi) "माइक की अनुमति नहीं" else "Microphone permission denied"
    }

    DisposableEffect(Unit) { onDispose { voiceManager.destroy() } }

    Column(
        modifier = Modifier.fillMaxSize().background(BgColor).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = if (isHindi) "लक्षण क्या हैं?" else "What symptoms?",
            color = WhiteColor, fontSize = 24.sp, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (transcript.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "\"$transcript\"", color = GreenColor, fontSize = 14.sp, modifier = Modifier.padding(12.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        Button(
            onClick = {
                if (!hasMicPermission) { permissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO); return@Button }
                if (!isListening) {
                    isListening = true
                    statusMsg = if (isHindi) "🎤 सुन रहा है..." else "🎤 Listening..."
                    voiceManager.setLanguage(isHindi)
                    voiceManager.startListening(
                        onResult = { text -> transcript = text; isListening = false; statusMsg = if (isHindi) "लक्षण चुनें या सबमिट करें" else "Tap symptoms or submit" },
                        onError = { error -> statusMsg = error; isListening = false }
                    )
                } else {
                    voiceManager.stopListening(); isListening = false
                    statusMsg = if (isHindi) "बोलने के लिए बटन दबाएं" else "Hold button to speak symptoms"
                }
            },
            modifier = Modifier.fillMaxWidth().height(80.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if (isListening) GreenColor else RedColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = when {
                    !hasMicPermission -> if (isHindi) "🎤  माइक की अनुमति दें" else "🎤  TAP TO GRANT MIC"
                    isListening -> if (isHindi) "🎤 सुन रहा है... (रोकने के लिए दबाएं)" else "🎤 LISTENING... (tap to stop)"
                    else -> if (isHindi) "🎤  बोलने के लिए दबाएं" else "🎤  HOLD TO SPEAK"
                },
                fontSize = 16.sp, fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Language: ${if (isHindi) "Hindi 🇮🇳" else "English 🇬🇧"}  •  Change in Settings", color = GrayColor, fontSize = 11.sp)
        Text(text = statusMsg, color = GrayColor, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = if (isHindi) "या लक्षण चुनें:" else "Or tap symptoms:", color = GrayColor, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(12.dp))

        val hindiSymptoms = listOf("सूजन", "खून बहना", "पलकें झुकना\n(ptosis)", "सांस लेने में\nतकलीफ", "दर्द", "मतली")
        val displaySymptoms = if (isHindi) hindiSymptoms else symptoms

        displaySymptoms.chunked(2).forEachIndexed { rowIndex, row ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                row.forEachIndexed { colIndex, symptom ->
                    val originalSymptom = symptoms[rowIndex * 2 + colIndex]
                    val isSelected = selected.contains(originalSymptom)
                    Card(
                        modifier = Modifier.weight(1f).height(64.dp).clickable {
                            if (isSelected) selected.remove(originalSymptom) else selected.add(originalSymptom)
                        },
                        colors = CardDefaults.cardColors(containerColor = if (isSelected) RedColor else CardColor),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = symptom, color = WhiteColor, fontSize = 13.sp, textAlign = TextAlign.Center)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Age selector
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = if (isHindi) "मरीज की उम्र (वैकल्पिक)" else "Patient age (optional)",
                    color = GrayColor, fontSize = 13.sp, fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    data class AgeGroup(val label: String, val hindiLabel: String, val value: String)
                    listOf(
                        AgeGroup("Child\n<12", "बच्चा\n<12", "8"),
                        AgeGroup("Teen\n12-17", "किशोर\n12-17", "15"),
                        AgeGroup("Adult\n18-60", "वयस्क\n18-60", "35"),
                        AgeGroup("Elder\n60+", "बुजुर्ग\n60+", "65")
                    ).forEach { group ->
                        Card(
                            modifier = Modifier.weight(1f).height(56.dp).clickable {
                                patientAge = if (patientAge == group.value) "" else group.value
                            },
                            colors = CardDefaults.cardColors(
                                containerColor = if (patientAge == group.value) AmberColor else Color(0xFF3A3A3C)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(
                                    text = if (isHindi) group.hindiLabel else group.label,
                                    color = WhiteColor, fontSize = 11.sp, textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                if (patientAge.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (isHindi) "✓ उम्र चुनी: ~$patientAge वर्ष" else "✓ Age selected: ~$patientAge years",
                        color = AmberColor, fontSize = 11.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onSubmit(selected.toList(), transcript, patientAge.toIntOrNull()) },
            modifier = Modifier.fillMaxWidth().height(64.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RedColor),
            enabled = selected.isNotEmpty() || transcript.isNotEmpty()
        ) {
            Text(text = if (isHindi) "अभी विश्लेषण करें" else "ANALYZE NOW", fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
        if (!isLoading) { kotlinx.coroutines.delay(500); onComplete() }
    }
    Column(
        modifier = Modifier.fillMaxSize().background(BgColor).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = RedColor, modifier = Modifier.size(72.dp), strokeWidth = 6.dp)
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = stage.ifEmpty { "Analyzing..." }, color = WhiteColor, fontSize = 18.sp, textAlign = TextAlign.Center)
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
    result: com.sarpamitra.guardrails.TriageResult?,
    onGenerateReferral: () -> Unit,
    onStartMonitoring: () -> Unit,
    onBack: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val prefs = context.getSharedPreferences("sarpa_prefs", android.content.Context.MODE_PRIVATE)
    val isHindi = prefs.getBoolean("is_hindi", true)
    val voiceManager = remember { com.sarpamitra.voice.VoiceManager(context) }

    LaunchedEffect(result?.severity) {
        if (result != null) {
            kotlinx.coroutines.delay(3000)
            val severityText = when (result.severity) {
                "CRITICAL" -> if (isHindi) "गंभीर स्थिति।" else "Critical severity."
                "SEVERE" -> if (isHindi) "खतरनाक स्थिति।" else "Severe severity."
                "MODERATE" -> if (isHindi) "मध्यम स्थिति।" else "Moderate severity."
                else -> if (isHindi) "हल्की स्थिति।" else "Mild severity."
            }
            val asvText = if (result.asvRequired) {
                if (isHindi) "एंटीवेनम जरूरी है। जिला अस्पताल जाएं।" else "Antivenom required. Go to District Hospital immediately."
            } else {
                if (isHindi) "एंटीवेनम की जरूरत नहीं। निगरानी जारी रखें।" else "Antivenom may not be required. Monitor closely."
            }
            voiceManager.speak("$severityText $asvText")
        }
    }

    DisposableEffect(Unit) { onDispose { voiceManager.destroy() } }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(BgColor).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            val severityColor = when (result?.severity) {
                "CRITICAL" -> RedColor; "SEVERE" -> Color(0xFFFF6B00); "MODERATE" -> AmberColor; else -> GreenColor
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = severityColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = when (result?.severity) { "CRITICAL" -> "⚠️ CRITICAL"; "SEVERE" -> "🔴 SEVERE"; "MODERATE" -> "🟡 MODERATE"; else -> "🟢 MILD" },
                        color = WhiteColor, fontSize = 28.sp, fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = result?.reasoning ?: "Assessing...", color = WhiteColor, fontSize = 14.sp, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Confidence: ${((result?.confidence ?: 0f) * 100).toInt()}%", color = WhiteColor.copy(alpha = 0.8f), fontSize = 12.sp)
                    result?.guardrailTriggered?.let { trigger ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(colors = CardDefaults.cardColors(containerColor = WhiteColor.copy(alpha = 0.2f)), shape = RoundedCornerShape(8.dp)) {
                            Text(
                                text = "⚡ Guardrail: $trigger",
                                color = WhiteColor, fontSize = 11.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CardColor), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (result?.asvRequired == true) "💉 ASV Required" else "💉 ASV Not Required",
                        color = if (result?.asvRequired == true) AmberColor else GreenColor,
                        fontSize = 18.sp, fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "${result?.asvType ?: "polyvalent"} • ${result?.estimatedVials ?: 8} vials", color = WhiteColor, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isHindi) "केवल अस्पताल में दें। घर पर नहीं।" else "Administer at hospital only. Do NOT give at home.",
                        color = AmberColor, fontSize = 12.sp
                    )
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CardColor), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = if (isHindi) "🩹 प्राथमिक उपचार" else "🩹 First Aid", color = WhiteColor, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (isHindi) {
                        listOf(
                            "✅ मरीज को शांत और स्थिर रखें",
                            "✅ काटे हुए अंग को हिलाएं नहीं",
                            "✅ काटने की जगह के पास के गहने हटाएं",
                            "✅ मरीज को लिटाकर रखें — चलने न दें",
                            "❌ टूर्निकेट (रस्सी) न बांधें — खतरनाक है",
                            "❌ काटने की जगह न काटें, न चूसें",
                            "❌ बर्फ या गर्मी न लगाएं",
                            "❌ कुछ भी खाने-पीने को न दें",
                            "❌ बिना डॉक्टर के ऑक्सीजन न दें"
                        ).forEach {
                            Text(text = it, color = if (it.startsWith("✅")) GreenColor else RedColor, fontSize = 13.sp, modifier = Modifier.padding(vertical = 2.dp))
                        }
                    } else {
                        listOf(
                            "✅ Keep patient calm and still",
                            "✅ Immobilize the bitten limb — no movement",
                            "✅ Remove rings, watches near bite site",
                            "✅ Keep patient lying down — do NOT let them walk",
                            "❌ Do NOT apply tourniquet — dangerous",
                            "❌ Do NOT cut or suck the bite",
                            "❌ Do NOT apply ice or heat",
                            "❌ Do NOT give food or water",
                            "❌ Do NOT give oxygen without bag-mask if breathing slows"
                        ).forEach {
                            Text(text = it, color = if (it.startsWith("✅")) GreenColor else RedColor, fontSize = 13.sp, modifier = Modifier.padding(vertical = 2.dp))
                        }
                    }
                    if (result?.syndrome == "neurotoxic" || result?.severity == "CRITICAL") {
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = RedColor.copy(alpha = 0.2f)), shape = RoundedCornerShape(8.dp)) {
                            Text(
                                text = if (isHindi) "⚠️ पलकें झुकने पर सांस रुक सकती है। तुरंत अस्पताल जाएं।" else "⚠️ If eyelids droop, respiratory arrest may follow within minutes. Go NOW.",
                                color = RedColor, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CardColor), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = if (isHindi) "🧪 अस्पताल में बताएं" else "🧪 Tell the Doctor", color = WhiteColor, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isHindi)
                            "• काटने का समय बताएं\n• कोई घरेलू उपचार किया हो तो बताएं\n• टूर्निकेट बांधा हो तो अस्पताल में ही खोलें\n• ब्लड क्लॉटिंग टेस्ट के लिए कहें"
                        else
                            "• Tell exact time of bite\n• Mention any home treatment given\n• If tourniquet applied — remove ONLY at hospital with ASV ready\n• Ask for Whole Blood Clotting Time test (WBCT)",
                        color = GrayColor, fontSize = 13.sp
                    )
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CardColor), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = if (isHindi) "🏥 नजदीकी अस्पताल" else "🏥 Nearest Facilities", color = WhiteColor, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF3A3A3C)), shape = RoundedCornerShape(8.dp)) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = if (isHindi) "⚠️ ASV स्टॉक की पुष्टि नहीं।" else "⚠️ ASV stock NOT verified.", color = AmberColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text(text = if (isHindi) "जिला अस्पताल जाएं।" else "Go to District Hospital if possible.", color = AmberColor, fontSize = 12.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = if (isHindi) "🏛️ जिला अस्पताल" else "🏛️ District Hospital", color = GreenColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Text(text = if (isHindi) "उच्च संभावना • अनुशंसित" else "HIGH probability • Recommended", color = GreenColor, fontSize = 12.sp)
                        }
                        Text(text = "12 km", color = GrayColor, fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color(0xFF3A3A3C))
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = if (isHindi) "🏥 उप-जिला अस्पताल" else "🏥 Sub-District Hospital", color = AmberColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Text(text = if (isHindi) "मध्यम-उच्च संभावना" else "Medium-High probability", color = AmberColor, fontSize = 12.sp)
                        }
                        Text(text = "4 km", color = GrayColor, fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color(0xFF3A3A3C))
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "🏨 PHC", color = GrayColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Text(text = if (isHindi) "⚠️ स्टॉक अनिश्चित" else "Variable ⚠️ May be out of stock", color = GrayColor, fontSize = 12.sp)
                        }
                        Text(text = "4.2 km", color = GrayColor, fontSize = 13.sp)
                    }
                }
            }
        }

        item {
            Button(onClick = onGenerateReferral, modifier = Modifier.fillMaxWidth().height(64.dp), colors = ButtonDefaults.buttonColors(containerColor = RedColor), shape = RoundedCornerShape(16.dp)) {
                Text(text = if (isHindi) "📄  रेफरल बनाएं" else "📄  GENERATE REFERRAL", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        item {
            Button(onClick = onStartMonitoring, modifier = Modifier.fillMaxWidth().height(64.dp), colors = ButtonDefaults.buttonColors(containerColor = AmberColor), shape = RoundedCornerShape(16.dp)) {
                Text(text = if (isHindi) "⏱️  निगरानी शुरू करें" else "⏱️  START MONITORING", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        item {
            TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text(text = if (isHindi) "वापस" else "Back", color = GrayColor)
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
    val photoCount = remember {
        com.sarpamitra.monitoring.SwellingPhotoManager.getPhotoCount(context, "SM-CURRENT")
    }

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

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(BgColor).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "📄 Referral Slip", color = WhiteColor, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "SARPA-MITRA", color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Emergency Snakebite Referral", color = Color.Gray, fontSize = 12.sp)
                    Divider(color = Color.LightGray, modifier = Modifier.padding(vertical = 12.dp))

                    ReferralRow("Case ID", caseId, Color.Black)
                    ReferralRow("Severity", result?.severity ?: "MODERATE", when (result?.severity) { "CRITICAL" -> Color.Red; "SEVERE" -> Color(0xFFFF6B00); "MODERATE" -> Color(0xFFFF9500); else -> Color(0xFF34C759) })
                    ReferralRow("Syndrome", result?.syndrome ?: "unknown", Color.Black)
                    ReferralRow("ASV Required", if (result?.asvRequired == true) "YES" else "NO", if (result?.asvRequired == true) Color.Red else Color(0xFF34C759))
                    ReferralRow("ASV Type", result?.asvType ?: "polyvalent", Color.Black)
                    ReferralRow("Est. Vials", "${result?.estimatedVials ?: 8}", Color.Black)
                    ReferralRow("Time", java.text.SimpleDateFormat("dd-MM-yyyy HH:mm", java.util.Locale.getDefault()).format(java.util.Date(timestamp)), Color.Black)
                    if (result?.guardrailTriggered != null) {
                        ReferralRow("Guardrail", result.guardrailTriggered!!, Color(0xFF007AFF))
                    }

                    Divider(color = Color.LightGray, modifier = Modifier.padding(vertical = 8.dp))

                    if (photoCount > 0) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column {
                                Text(text = "📷 Swelling Photos", color = Color.Black, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                Text(text = "$photoCount photos captured during monitoring", color = Color.Gray, fontSize = 11.sp)
                            }
                            Box(modifier = Modifier.background(Color(0xFF007AFF).copy(alpha = 0.1f), RoundedCornerShape(8.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                                Text(text = "$photoCount", color = Color(0xFF007AFF), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "⚠️ Ask ASHA worker to show phone for visual review of swelling progression", color = Color(0xFFFF6B00), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    } else {
                        Text(text = "📷 No swelling photos captured", color = Color.Gray, fontSize = 11.sp)
                    }

                    Divider(color = Color.LightGray, modifier = Modifier.padding(vertical = 12.dp))
                    Text(text = "Scan at facility", color = Color.Gray, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(bitmap = qrBitmap.asImageBitmap(), contentDescription = "QR Code", modifier = Modifier.size(180.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Generated offline by Sarpa-Mitra • Gemma 4 E2B", color = Color.Gray, fontSize = 9.sp)
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CardColor), shape = RoundedCornerShape(12.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "🏥 For the Receiving Doctor", color = WhiteColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    listOf(
                        "• Triage generated by Gemma 4 E2B + WHO/ICMR guardrails",
                        "• Guardrail: ${result?.guardrailTriggered ?: "None fired"}",
                        "• Confidence: ${((result?.confidence ?: 0f) * 100).toInt()}%",
                        "• Do NOT rely solely on AI output — clinical assessment required",
                        "• Perform WBCT (Whole Blood Clotting Time) on arrival",
                        if (photoCount > 0) "• $photoCount swelling photos available — ask ASHA worker" else "• No swelling photos available"
                    ).forEach {
                        Text(text = it, color = GrayColor, fontSize = 12.sp, modifier = Modifier.padding(vertical = 2.dp))
                    }
                }
            }
        }

        item {
            Button(onClick = { saveReferralToGallery(context, qrContent, caseId) }, modifier = Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = RedColor), shape = RoundedCornerShape(12.dp)) {
                Text(text = "💾  SAVE TO GALLERY", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        item {
            Button(
                onClick = {
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
            TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text(text = "Back", color = GrayColor) }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// ── 9. MONITORING ────────────────────────────────────────────────────────────
@Composable
fun MonitoringScreen(
    onNewSymptom: () -> Unit,
    onBack: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val prefs = context.getSharedPreferences("sarpa_prefs", android.content.Context.MODE_PRIVATE)
    val isHindi = prefs.getBoolean("is_hindi", true)

    var secondsElapsed by remember { mutableStateOf(0) }
    var silenceSeconds by remember { mutableStateOf(0) }
    var sosTriggered by remember { mutableStateOf(false) }
    var nextCheckSeconds by remember { mutableStateOf(900) }
    var showSosDialog by remember { mutableStateOf(false) }
    var showOffsetDialog by remember { mutableStateOf(false) }
    var offsetInput by remember { mutableStateOf("") }
    var showSwellingCamera by remember { mutableStateOf(false) }
    var swellingPhotoCount by remember { mutableStateOf(0) }
    var lastPhotoTime by remember { mutableStateOf("") }
    var showPhotoPrompt by remember { mutableStateOf(false) }

    val voiceManager = remember { com.sarpamitra.voice.VoiceManager(context) }
    val sosManager = remember { com.sarpamitra.monitoring.SosManager(context) }

    DisposableEffect(Unit) { onDispose { voiceManager.destroy(); sosManager.destroy() } }

    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000)
            secondsElapsed++
            silenceSeconds++
            nextCheckSeconds--
            if (nextCheckSeconds <= 0) {
                nextCheckSeconds = 900
                showPhotoPrompt = true
                if (isHindi) voiceManager.speak("मरीज की जांच करें। सूजन की फोटो लें।")
                else voiceManager.speak("Check patient. Please photograph the swelling now.")
            }
            if (silenceSeconds >= 90 && !sosTriggered) {
                sosTriggered = true
                showSosDialog = true
                sosManager.triggerSos("SM-${System.currentTimeMillis()}", "UNKNOWN")
                if (isHindi) voiceManager.speak("मदद करो! सांप काटा है! मदद चाहिए!")
                else voiceManager.speak("Help needed! Snakebite emergency!")
            }
        }
    }

    fun resetSilence() { silenceSeconds = 0 }

    if (showSwellingCamera) {
        CameraXScreen(
            title = if (isHindi) "📷 सूजन की फोटो लें" else "📷 Photograph Swelling",
            overlayColor = AmberColor,
            onPhotoCaptured = { path ->
                swellingPhotoCount++
                val sdf = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
                lastPhotoTime = sdf.format(java.util.Date())
                com.sarpamitra.monitoring.SwellingPhotoManager.savePhoto(
                    context = context,
                    caseId = "SM-CURRENT",
                    photoPath = path,
                    minutesSinceBite = secondsElapsed / 60
                )
                showSwellingCamera = false
                resetSilence()
            },
            onSkip = { showSwellingCamera = false; resetSilence() },
            onValidatePhoto = null
        )
        return
    }

    if (showOffsetDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showOffsetDialog = false },
            title = { Text(text = if (isHindi) "काटने का समय?" else "When did the bite happen?", color = WhiteColor, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text(text = if (isHindi) "ऐप खोलने से पहले कितने मिनट हुए थे?" else "Enter minutes before you opened the app:", color = GrayColor, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    androidx.compose.material3.OutlinedTextField(
                        value = offsetInput,
                        onValueChange = { offsetInput = it.filter { c -> c.isDigit() } },
                        label = { Text(if (isHindi) "मिनट पहले" else "Minutes ago", color = GrayColor) },
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(focusedTextColor = WhiteColor, unfocusedTextColor = WhiteColor, focusedBorderColor = RedColor, unfocusedBorderColor = GrayColor)
                    )
                }
            },
            confirmButton = {
                Button(onClick = { secondsElapsed = (offsetInput.toIntOrNull() ?: 0) * 60; showOffsetDialog = false; offsetInput = "" }, colors = ButtonDefaults.buttonColors(containerColor = RedColor)) {
                    Text(if (isHindi) "सेट करें" else "SET")
                }
            },
            dismissButton = { TextButton(onClick = { showOffsetDialog = false }) { Text(if (isHindi) "रद्द करें" else "Cancel", color = GrayColor) } },
            containerColor = CardColor
        )
    }

    if (showSosDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showSosDialog = false; sosManager.stopStrobe() },
            title = { Text(text = "🚨 ${if (isHindi) "आपातकाल सक्रिय" else "SOS TRIGGERED"}", color = RedColor, fontWeight = FontWeight.Bold) },
            text = { Text(text = if (isHindi) "90 सेकंड की चुप्पी। अलार्म और टॉर्च चालू।" else "90 seconds of silence.\nAlarm and flashlight activated.", color = WhiteColor) },
            confirmButton = {
                Button(onClick = { showSosDialog = false; sosTriggered = false; silenceSeconds = 0; sosManager.stopStrobe() }, colors = ButtonDefaults.buttonColors(containerColor = RedColor)) {
                    Text(if (isHindi) "मैं ठीक हूं" else "I'M OK — CANCEL SOS")
                }
            },
            containerColor = CardColor
        )
    }

    if (showPhotoPrompt) {
        var countDown by remember { mutableStateOf(30) }
        LaunchedEffect(showPhotoPrompt) {
            while (countDown > 0) { kotlinx.coroutines.delay(1000); countDown-- }
            showPhotoPrompt = false
            nextCheckSeconds = 300
        }
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { },
            title = { Text(text = if (isHindi) "📷 सूजन की फोटो लें!" else "📷 Take Swelling Photo!", color = AmberColor, fontWeight = FontWeight.Bold, fontSize = 20.sp) },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (isHindi) "हर 15 मिनट में फोटो लेना ज़रूरी है।\nडॉक्टर को सूजन देखने में मदद मिलती है।\n\nअभी तक: $swellingPhotoCount फोटो" else "Photographing swelling every 15 minutes helps the doctor.\n\nPhotos so far: $swellingPhotoCount",
                        color = WhiteColor, textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(progress = countDown / 30f, color = AmberColor, modifier = Modifier.size(56.dp), strokeWidth = 4.dp)
                        Text(text = "$countDown", color = WhiteColor, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = if (isHindi) "सेकंड में बंद होगा" else "seconds then snooze 5min", color = GrayColor, fontSize = 11.sp)
                }
            },
            confirmButton = {
                Button(
                    onClick = { showPhotoPrompt = false; showSwellingCamera = true; resetSilence() },
                    colors = ButtonDefaults.buttonColors(containerColor = AmberColor),
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) { Text(text = if (isHindi) "📷 अभी फोटो लें" else "📷 TAKE PHOTO NOW", fontSize = 16.sp, fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = { showPhotoPrompt = false; nextCheckSeconds = 300; resetSilence() }) {
                    Text(text = if (isHindi) "5 मिनट बाद याद दिलाएं" else "Remind in 5 min", color = GrayColor, fontSize = 12.sp)
                }
            },
            containerColor = CardColor
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().background(BgColor).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "⏱️ ${if (isHindi) "निगरानी" else "MONITORING"}", color = WhiteColor, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Text(
                text = if (isHindi) "काटने के बाद: ${secondsElapsed / 60}मि ${secondsElapsed % 60}से" else "Time since bite: ${secondsElapsed / 60}m ${secondsElapsed % 60}s",
                color = AmberColor, fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(onClick = { showOffsetDialog = true }) { Text(text = "✏️ ${if (isHindi) "बदलें" else "Edit"}", color = GrayColor, fontSize = 12.sp) }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CardColor), shape = RoundedCornerShape(12.dp)) {
            Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = if (isHindi) "अगली जांच: ${nextCheckSeconds / 60}मि ${nextCheckSeconds % 60}से" else "Next check in: ${nextCheckSeconds / 60}m ${nextCheckSeconds % 60}s", color = GrayColor, fontSize = 14.sp)
                Text(text = if (isHindi) "SOS में: ${(90 - silenceSeconds).coerceAtLeast(0)}से" else "Silence SOS in: ${(90 - silenceSeconds).coerceAtLeast(0)}s", color = if (silenceSeconds > 60) RedColor else GrayColor, fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CardColor), shape = RoundedCornerShape(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(text = if (isHindi) "📷 सूजन की फोटो" else "📷 Swelling Photos", color = WhiteColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = if (swellingPhotoCount == 0) { if (isHindi) "अभी तक कोई फोटो नहीं" else "None taken yet" } else { if (isHindi) "$swellingPhotoCount फोटो • आखिरी: $lastPhotoTime" else "$swellingPhotoCount photos • Last: $lastPhotoTime" },
                        color = GrayColor, fontSize = 12.sp
                    )
                }
                Button(
                    onClick = { showSwellingCamera = true; resetSilence() },
                    colors = ButtonDefaults.buttonColors(containerColor = AmberColor),
                    modifier = Modifier.height(40.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) { Text(text = if (isHindi) "📷 अभी लें" else "📷 Take", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CardColor), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = if (isHindi) "इन लक्षणों पर ध्यान दें:" else "Watch for:", color = WhiteColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                if (isHindi) {
                    listOf("पलकें झुकना (ptosis)", "निगलने में कठिनाई", "सांस लेने में तकलीफ", "सूजन बढ़ना", "काटने की जगह से खून").forEach { Text(text = "• $it", color = GrayColor, fontSize = 14.sp) }
                } else {
                    listOf("Drooping eyelids (ptosis)", "Difficulty swallowing", "Breathing problems", "Increased swelling", "Bleeding from bite").forEach { Text(text = "• $it", color = GrayColor, fontSize = 14.sp) }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { resetSilence(); onNewSymptom() }, modifier = Modifier.fillMaxWidth().height(80.dp), colors = ButtonDefaults.buttonColors(containerColor = RedColor), shape = RoundedCornerShape(16.dp)) {
            Text(text = if (isHindi) "🚨  नया लक्षण" else "🚨  NEW SYMPTOM", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { resetSilence(); if (isHindi) voiceManager.speak("मरीज ठीक है। निगरानी जारी है।") else voiceManager.speak("Patient stable. Continuing to monitor.") },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenColor),
            shape = RoundedCornerShape(16.dp)
        ) { Text(text = if (isHindi) "✅  मैं यहां हूं — मरीज ठीक है" else "✅  I'M HERE — PATIENT STABLE", fontSize = 14.sp, fontWeight = FontWeight.Bold) }
        Spacer(modifier = Modifier.height(12.dp))
        TextButton(onClick = { resetSilence(); onBack() }) { Text(text = if (isHindi) "वापस" else "Back", color = GrayColor) }
    }
}

// ── 10. HISTORY ──────────────────────────────────────────────────────────────
@Composable
fun HistoryScreen(
    onBack: () -> Unit,
    onSettingsClick: () -> Unit,
    onCaseClick: (caseId: String, severity: String, syndrome: String, timestamp: String) -> Unit = { _, _, _, _ -> }
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    var cases by remember { mutableStateOf<List<com.sarpamitra.data.local.entity.PatientSession>>(emptyList()) }
    var showDeleteDialog by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    fun loadCases() {
        scope.launch {
            val result = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                com.sarpamitra.data.local.AppDatabase.getInstance(context)
                    .patientSessionDao()
                    .getAllSessions()
            }
            cases = result
        }
    }

    LaunchedEffect(Unit) { loadCases() }

    // Delete confirmation dialog
    showDeleteDialog?.let { sessionId ->
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = {
                Text(
                    text = "🗑️ Delete Case?",
                    color = RedColor,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "This will permanently delete case $sessionId and all associated swelling photos.\n\nThis cannot be undone.",
                    color = WhiteColor
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        scope.launch {
                            kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                                com.sarpamitra.data.local.AppDatabase.getInstance(context)
                                    .patientSessionDao()
                                    .deleteSession(sessionId)
                                com.sarpamitra.monitoring.SwellingPhotoManager.clearPhotos(
                                    context, sessionId
                                )
                            }
                            showDeleteDialog = null
                            loadCases()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = RedColor)
                ) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) {
                    Text("Cancel", color = GrayColor)
                }
            },
            containerColor = CardColor
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Case History",
                color = WhiteColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = onSettingsClick) {
                Text(text = "⚙️ Settings", color = GrayColor)
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${cases.size} case${if (cases.size != 1) "s" else ""} • Tap to view • 🗑️ to delete",
            color = GrayColor,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (cases.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "📋", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "No cases yet", color = GrayColor, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Completed triage cases will appear here",
                        color = GrayColor,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cases) { session ->
                    val severityColor = when (session.severity) {
                        "CRITICAL" -> RedColor
                        "SEVERE" -> Color(0xFFFF6B00)
                        "MODERATE" -> AmberColor
                        else -> GreenColor
                    }
                    val dateLabel = java.text.SimpleDateFormat(
                        "dd-MM-yyyy HH:mm",
                        java.util.Locale.getDefault()
                    ).format(java.util.Date(session.timestamp))

                    // Check if session is still active (within 6 hours)
                    val isActive = (System.currentTimeMillis() - session.timestamp) < (6 * 60 * 60 * 1000)
                    val photoCount = com.sarpamitra.monitoring.SwellingPhotoManager
                        .getPhotoCount(context, session.sessionId)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onCaseClick(
                                    session.sessionId,
                                    session.severity,
                                    session.asvType ?: "unknown",
                                    dateLabel
                                )
                            },
                        colors = CardDefaults.cardColors(containerColor = CardColor),
                        shape = RoundedCornerShape(12.dp),
                        border = if (isActive) androidx.compose.foundation.BorderStroke(1.dp, AmberColor) else null
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Severity dot
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(severityColor, shape = RoundedCornerShape(6.dp))
                                )
                                Spacer(modifier = Modifier.width(12.dp))

                                // Case info
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = session.sessionId,
                                            color = WhiteColor,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        if (isActive) {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Card(
                                                colors = CardDefaults.cardColors(
                                                    containerColor = AmberColor.copy(alpha = 0.2f)
                                                ),
                                                shape = RoundedCornerShape(4.dp)
                                            ) {
                                                Text(
                                                    text = "ACTIVE",
                                                    color = AmberColor,
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                    Text(
                                        text = "${session.severity} • $dateLabel",
                                        color = GrayColor,
                                        fontSize = 13.sp
                                    )
                                    if (session.guardrailTriggered != null) {
                                        Text(
                                            text = "⚡ ${session.guardrailTriggered}",
                                            color = AmberColor,
                                            fontSize = 11.sp
                                        )
                                    }
                                    if (photoCount > 0) {
                                        Text(
                                            text = "📷 $photoCount swelling photo${if (photoCount != 1) "s" else ""}",
                                            color = GrayColor,
                                            fontSize = 11.sp
                                        )
                                    }
                                }

                                // Delete button
                                TextButton(
                                    onClick = { showDeleteDialog = session.sessionId },
                                    contentPadding = PaddingValues(4.dp)
                                ) {
                                    Text(text = "🗑️", fontSize = 18.sp)
                                }

                                // Arrow
                                Text(text = "›", color = GrayColor, fontSize = 20.sp)
                            }

                            // Reasoning snippet
                            if (!session.reasoning.isNullOrEmpty()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = session.reasoning,
                                    color = GrayColor,
                                    fontSize = 11.sp,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    // Clear all button — only if cases exist
                    if (cases.isNotEmpty()) {
                        TextButton(
                            onClick = {
                                scope.launch {
                                    kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                                        val db = com.sarpamitra.data.local.AppDatabase.getInstance(context)
                                        cases.forEach { session ->
                                            db.patientSessionDao().deleteSession(session.sessionId)
                                            com.sarpamitra.monitoring.SwellingPhotoManager.clearPhotos(
                                                context, session.sessionId
                                            )
                                        }
                                    }
                                    loadCases()
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "🗑️ Clear All History", color = RedColor, fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Back", color = GrayColor)
        }
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
    var hindiSelected by remember { mutableStateOf(prefs.getBoolean("is_hindi", true)) }

    Column(modifier = Modifier.fillMaxSize().background(BgColor).padding(24.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Settings", color = WhiteColor, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CardColor), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Language", color = WhiteColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { hindiSelected = true; prefs.edit().putBoolean("is_hindi", true).apply() }, colors = ButtonDefaults.buttonColors(containerColor = if (hindiSelected) RedColor else GrayColor)) { Text(text = "Hindi 🇮🇳") }
                    Button(onClick = { hindiSelected = false; prefs.edit().putBoolean("is_hindi", false).apply() }, colors = ButtonDefaults.buttonColors(containerColor = if (!hindiSelected) RedColor else GrayColor)) { Text(text = "English 🇬🇧") }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Currently: ${if (hindiSelected) "Hindi" else "English"}", color = GreenColor, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CardColor), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Model Status", color = WhiteColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Gemma 4 E2B via LiteRT-LM • 2.58 GB", color = GreenColor, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "✅ Model loaded — offline ready", color = GreenColor, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = GrayColor), modifier = Modifier.fillMaxWidth()) { Text(text = "Check for Update (USB)") }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CardColor), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Developer", color = WhiteColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Run inference benchmark to measure performance on this device.", color = GrayColor, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onBenchmark, colors = ButtonDefaults.buttonColors(containerColor = GrayColor), modifier = Modifier.fillMaxWidth()) { Text(text = "📊 Run Benchmarks") }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CardColor), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "About", color = WhiteColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Sarpa-Mitra v1.0", color = GrayColor, fontSize = 13.sp)
                Text(text = "Offline snakebite triage for rural India", color = GrayColor, fontSize = 12.sp)
                Text(text = "Clinical protocol: WHO/ICMR guidelines", color = GrayColor, fontSize = 12.sp)
                Text(text = "AI: Gemma 4 E2B via LiteRT-LM", color = GrayColor, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text(text = "Back", color = GrayColor) }
    }
}


// ── SHARED HELPERS ───────────────────────────────────────────────────────────
@Composable
fun ReferralRow(label: String, value: String, valueColor: Color) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = label, color = Color.Gray, fontSize = 13.sp)
        Text(text = value, color = valueColor, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}

fun saveReferralToGallery(context: android.content.Context, content: String, caseId: String) {
    try {
        val fileName = "SarpaMitra_$caseId.txt"
        val file = java.io.File(context.getExternalFilesDir(null), fileName)
        file.writeText(content)
        android.media.MediaScannerConnection.scanFile(context, arrayOf(file.absolutePath), arrayOf("text/plain"), null)
    } catch (e: Exception) {
        android.util.Log.e("SARPA", "Failed to save referral: ${e.message}")
    }
}