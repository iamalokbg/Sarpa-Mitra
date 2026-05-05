package com.sarpamitra.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarpamitra.ai.GemmaInference
import com.sarpamitra.benchmark.BenchmarkLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun BenchmarkScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var isRunning by remember { mutableStateOf(false) }
    var currentRun by remember { mutableStateOf(0) }
    var status by remember { mutableStateOf("Ready to benchmark") }
    var summary by remember { mutableStateOf("") }
    val runResults = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "📊 Benchmarks",
            color = WhiteColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Enable AIRPLANE MODE before running",
            color = AmberColor,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Device: Xiaomi 24094RAD4I", color = GrayColor, fontSize = 13.sp)
                Text(text = "Model: gemma-2b-it-cpu-int8.bin", color = GrayColor, fontSize = 13.sp)
                Text(text = "Runs: 5 consecutive", color = GrayColor, fontSize = 13.sp)
                Text(text = "Network: Airplane mode required", color = AmberColor, fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isRunning) {
            CircularProgressIndicator(color = RedColor)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = status, color = WhiteColor, fontSize = 14.sp, textAlign = TextAlign.Center)
            Text(text = "Run $currentRun / 5", color = GrayColor, fontSize = 12.sp)
        } else {
            Button(
                onClick = {
                    isRunning = true
                    runResults.clear()
                    BenchmarkLogger.clear()
                    scope.launch(Dispatchers.IO) {
                        val gemma = GemmaInference(context)

                        // Measure model load
                        status = "Loading model..."
                        val loadMs = BenchmarkLogger.measureModelLoad {
                            gemma.loadModel()
                        }

                        // 5 inference runs
                        repeat(5) { i ->
                            currentRun = i + 1
                            status = "Running inference $currentRun/5..."
                            val result = BenchmarkLogger.measureInference(i + 1, if (i == 0) loadMs else 0L) {
                                kotlinx.coroutines.runBlocking {
                                    gemma.triage(
                                        symptoms = listOf("swelling", "ptosis", "breathing difficulty"),
                                        transcript = "leg swelling and drooping eyelids",
                                        ageYears = null,
                                        hoursSinceBite = 1.5f,
                                        hasSnakePhoto = false,
                                        hasBitePhoto = false
                                    )
                                }
                            }
                            runResults.add("Run ${i + 1}: ${result.inferenceMs}ms • ${result.peakRamMb}MB RAM")
                        }

                        gemma.close()
                        summary = BenchmarkLogger.getSummary()
                        status = "Complete"
                        isRunning = false
                    }
                },
                modifier = Modifier.fillMaxWidth().height(64.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RedColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "▶ RUN BENCHMARK (5 runs)", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (runResults.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Results", color = WhiteColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    runResults.forEach { result ->
                        Text(text = result, color = GreenColor, fontSize = 13.sp)
                    }
                }
            }
        }

        if (summary.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A3A1A)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Summary", color = GreenColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = summary,
                        color = GreenColor,
                        fontSize = 12.sp,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(android.content.Intent.EXTRA_TEXT, summary)
                        putExtra(android.content.Intent.EXTRA_SUBJECT, "Sarpa-Mitra Benchmark Results")
                    }
                    context.startActivity(android.content.Intent.createChooser(shareIntent, "Share Benchmarks"))
                },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GrayColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "📤 Share Results", fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        TextButton(onClick = onBack) {
            Text(text = "Back", color = GrayColor)
        }
    }
}