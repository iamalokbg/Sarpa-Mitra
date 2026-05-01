package com.sarpamitra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var output by remember { mutableStateOf("Tap button to test Gemma") }
            var loading by remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF1C1C1E)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Sarpa-Mitra AI Test",
                        color = Color.White,
                        fontSize = 22.sp
                    )
                    Text(
                        text = output,
                        color = Color.Green,
                        fontSize = 14.sp
                    )
                    Button(
                        onClick = {
                            if (!loading) {
                                loading = true
                                output = "Loading model... (30-60 seconds)"
                                scope.launch(Dispatchers.IO) {
                                    try {
                                        val modelPath = filesDir.absolutePath +
                                                "/models/gemma-2b-it-cpu-int8.bin"
                                        val options = LlmInference.LlmInferenceOptions.builder()
                                            .setModelPath(modelPath)
                                            .setMaxTokens(200)
                                            .build()
                                        val llm = LlmInference.createFromOptions(
                                            this@MainActivity, options
                                        )
                                        val result = llm.generateResponse(
                                            "A snakebite patient has leg swelling and drooping eyelids. Severity in one sentence:"
                                        )
                                        output = result ?: "No response"
                                        llm.close()
                                    } catch (e: Exception) {
                                        output = "ERROR: ${e.message}"
                                    } finally {
                                        loading = false
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = if (loading) "Running..." else "TEST GEMMA",
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}