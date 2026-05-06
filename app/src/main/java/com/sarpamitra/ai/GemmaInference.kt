package com.sarpamitra.ai

import android.content.Context
import com.google.ai.edge.litertlm.Backend
import com.google.ai.edge.litertlm.Content
import com.google.ai.edge.litertlm.Contents
import com.google.ai.edge.litertlm.Conversation
import com.google.ai.edge.litertlm.ConversationConfig
import com.google.ai.edge.litertlm.Engine
import com.google.ai.edge.litertlm.EngineConfig
import com.google.ai.edge.litertlm.SamplerConfig
import com.sarpamitra.guardrails.ClinicalGuardrail
import com.sarpamitra.guardrails.TriageResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class GemmaInference(private val context: Context) {

    private var engine: Engine? = null
    private var conversation: Conversation? = null
    var isModelMissing = false
        private set

    fun loadModel(): Boolean {
        val modelPath = context.filesDir.absolutePath + "/models/gemma4.litertlm"
        val file = java.io.File(modelPath)
        if (!file.exists()) {
            isModelMissing = true
            return false
        }
        return try {
            val config = EngineConfig(
                modelPath = modelPath,
                backend = Backend.CPU(),
                cacheDir = context.cacheDir.absolutePath
            )
            engine = Engine(config)
            engine?.initialize()
            conversation = engine?.createConversation(freshConvConfig())
            true
        } catch (e: Exception) {
            isModelMissing = true
            false
        }
    }

    // Vision disabled on 4GB devices — OOM crash
    // Photo is saved for documentation only
    // Returns true always — no blocking
    suspend fun validateWoundPhoto(imagePath: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext true
    }

    suspend fun triage(
        symptoms: List<String>,
        transcript: String,
        ageYears: Int?,
        hoursSinceBite: Float?,
        hasSnakePhoto: Boolean,
        hasBitePhoto: Boolean,
        woundFindings: String? = null
    ): TriageResult = withContext(Dispatchers.IO) {

        if (engine == null) {
            if (!loadModel()) return@withContext fallbackResult(symptoms)
        }

        val prompt = buildPrompt(
            symptoms.joinToString(", ").ifEmpty { transcript },
            ageYears,
            hoursSinceBite,
            hasBitePhoto
        )

        return@withContext try {
            val message = Contents.of(Content.Text(prompt))
            val response = conversation?.sendMessage(message)
            val raw = response?.toString() ?: ""
            resetConversation()
            val parsed = parseJson(raw)
            ClinicalGuardrail.apply(
                parsed,
                symptoms + transcript.split(" "),
                ageYears,
                hoursSinceBite
            )
        } catch (e: Exception) {
            val fallback = fallbackResult(symptoms)
            ClinicalGuardrail.apply(
                fallback,
                symptoms + transcript.split(" "),
                ageYears,
                hoursSinceBite
            )
        }
    }

    private fun buildPrompt(
        symptoms: String,
        age: Int?,
        hours: Float?,
        hasBitePhoto: Boolean
    ): String {
        val ageText = if (age != null) "Patient age: $age years." else ""
        val hoursText = if (hours != null) "Hours since bite: $hours." else ""
        val photoText = if (hasBitePhoto) "A wound photo has been captured and saved for hospital review." else ""
        return """You are an emergency snakebite triage assistant in rural India.
$ageText $hoursText $photoText
Symptoms reported: $symptoms

Respond ONLY with valid JSON, no markdown, no explanation, no extra text:
{"syndrome":"neurotoxic","severity":"MILD|MODERATE|SEVERE|CRITICAL","asv_required":true,"asv_type":"polyvalent","estimated_vials":8,"urgency":"urgent","confidence":0.75,"reasoning":"brief explanation"}"""
    }

    private fun freshConvConfig() = ConversationConfig(
        samplerConfig = SamplerConfig(
            topK = 40,
            topP = 0.95,
            temperature = 0.1
        )
    )

    private fun resetConversation() {
        conversation?.close()
        conversation = engine?.createConversation(freshConvConfig())
    }

    private fun parseJson(raw: String): TriageResult {
        return try {
            val jsonStart = raw.indexOf("{")
            val jsonEnd = raw.lastIndexOf("}") + 1
            if (jsonStart == -1 || jsonEnd == 0) throw Exception("No JSON found")
            val cleaned = raw.substring(jsonStart, jsonEnd)
            val json = JSONObject(cleaned)
            TriageResult(
                syndrome = json.optString("syndrome", "unknown"),
                severity = json.optString("severity", "MODERATE").uppercase(),
                asvRequired = json.optBoolean("asv_required", true),
                asvType = json.optString("asv_type", "polyvalent"),
                estimatedVials = json.optInt("estimated_vials", 8),
                urgency = json.optString("urgency", "urgent"),
                confidence = json.optDouble("confidence", 0.5).toFloat(),
                reasoning = json.optString("reasoning", "")
            )
        } catch (e: Exception) {
            TriageResult(confidence = 0.4f)
        }
    }

    private fun fallbackResult(symptoms: List<String>): TriageResult {
        val hasNeuro = symptoms.any {
            it.contains("ptosis", ignoreCase = true) ||
                    it.contains("breathing", ignoreCase = true)
        }
        return TriageResult(
            syndrome = if (hasNeuro) "neurotoxic" else "unknown",
            severity = if (hasNeuro) "CRITICAL" else "MODERATE",
            asvRequired = true,
            asvType = "polyvalent",
            estimatedVials = 8,
            urgency = if (hasNeuro) "immediate" else "urgent",
            confidence = 0.4f,
            reasoning = "Model unavailable. Defaulting to safe protocol."
        )
    }

    fun close() {
        conversation?.close()
        engine?.close()
        engine = null
        conversation = null
    }
}