package com.sarpamitra.ai

import android.content.Context
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import com.sarpamitra.guardrails.ClinicalGuardrail
import com.sarpamitra.guardrails.TriageResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class GemmaInference(private val context: Context) {

    private var llm: LlmInference? = null
    var isModelMissing = false
        private set
    var isMmapFallback = false
        private set

    fun loadModel(): Boolean {
        val modelPath = context.filesDir.absolutePath + "/models/gemma-2b-it-cpu-int8.bin"
        val file = java.io.File(modelPath)
        if (!file.exists()) {
            isModelMissing = true
            return false
        }
        return try {
            val options = LlmInference.LlmInferenceOptions.builder()
                .setModelPath(modelPath)
                .setMaxTokens(400)
                .build()
            llm = LlmInference.createFromOptions(context, options)
            true
        } catch (e: Exception) {
            isModelMissing = true
            false
        }
    }

    suspend fun triage(
        symptoms: List<String>,
        transcript: String,
        ageYears: Int?,
        hoursSinceBite: Float?,
        hasSnakePhoto: Boolean,
        hasBitePhoto: Boolean
    ): TriageResult = withContext(Dispatchers.IO) {

        if (llm == null) {
            if (!loadModel()) return@withContext fallbackResult(symptoms)
        }

        val symptomText = symptoms.joinToString(", ").ifEmpty { transcript }
        val prompt = buildPrompt(symptomText, ageYears, hoursSinceBite)

        return@withContext try {
            val raw = llm?.generateResponse(prompt) ?: ""
            val parsed = parseJson(raw)
            ClinicalGuardrail.apply(parsed, symptoms + transcript.split(" "), ageYears, hoursSinceBite)
        } catch (e: Exception) {
            val fallback = fallbackResult(symptoms)
            ClinicalGuardrail.apply(fallback, symptoms + transcript.split(" "), ageYears, hoursSinceBite)
        }
    }

    private fun buildPrompt(symptoms: String, age: Int?, hours: Float?): String {
        val ageText = if (age != null) "Patient age: $age years." else ""
        val hoursText = if (hours != null) "Hours since bite: $hours." else ""
        return """<start_of_turn>user
You are an emergency snakebite triage assistant in rural India.
$ageText $hoursText
Symptoms reported: $symptoms

Respond ONLY with valid JSON in this exact format, no other text:
{"syndrome":"neurotoxic|hemotoxic|cytotoxic|unknown","severity":"MILD|MODERATE|SEVERE|CRITICAL","asv_required":true|false,"asv_type":"polyvalent|monovalent_cobra|monovalent_viper|null","estimated_vials":8,"urgency":"routine|urgent|immediate","confidence":0.75,"reasoning":"brief explanation"}
<end_of_turn>
<start_of_turn>model
"""
    }

    private fun parseJson(raw: String): TriageResult {
        return try {
            val cleaned = raw.substringAfter("{").substringBefore("}").let { "{$it}" }
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
        val hasNeuro = symptoms.any { it.contains("ptosis", ignoreCase = true) || it.contains("breathing", ignoreCase = true) }
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
        llm?.close()
        llm = null
    }
}
