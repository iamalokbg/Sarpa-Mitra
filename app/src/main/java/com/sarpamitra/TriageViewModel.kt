package com.sarpamitra

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sarpamitra.ai.GemmaInference
import com.sarpamitra.guardrails.TriageResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

data class TriageState(
    val isLoading: Boolean = false,
    val result: TriageResult? = null,
    val error: String? = null,
    val stage: String = "",
    val symptoms: List<String> = emptyList(),
    val transcript: String = "",
    val snakePhotoPath: String? = null,
    val bitePhotoPath: String? = null,
    val isModelMissing: Boolean = false,
    val hoursSinceBite: Float? = null,
    val ageYears: Int? = null
)

class TriageViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(TriageState())
    val state: StateFlow<TriageState> = _state

    private val gemma = GemmaInference(application)

    fun setSymptoms(symptoms: List<String>) {
        _state.value = _state.value.copy(symptoms = symptoms)
    }

    fun setTranscript(transcript: String) {
        _state.value = _state.value.copy(transcript = transcript)
    }

    fun setSnakePhoto(path: String?) {
        _state.value = _state.value.copy(snakePhotoPath = path)
    }

    fun setBitePhoto(path: String?) {
        _state.value = _state.value.copy(bitePhotoPath = path)
    }

    fun setHoursSinceBite(hours: Float) {
        _state.value = _state.value.copy(hoursSinceBite = hours)
    }

    fun setAgeYears(age: Int) {
        _state.value = _state.value.copy(ageYears = age)
    }

    suspend fun validateWoundPhoto(imagePath: String): Boolean {
        return gemma.validateWoundPhoto(imagePath)
    }

    fun runTriage() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null,
                stage = "Loading model..."
            )

            val currentState = _state.value

            _state.value = _state.value.copy(stage = "Analyzing symptoms...")
            kotlinx.coroutines.delay(600)

            _state.value = _state.value.copy(stage = "Cross-referencing syndromes...")
            kotlinx.coroutines.delay(600)

            _state.value = _state.value.copy(stage = "Applying clinical guardrails...")

            val result = gemma.triage(
                symptoms = currentState.symptoms,
                transcript = currentState.transcript,
                ageYears = currentState.ageYears,
                hoursSinceBite = currentState.hoursSinceBite,
                hasSnakePhoto = currentState.snakePhotoPath != null,
                hasBitePhoto = currentState.bitePhotoPath != null,
                woundFindings = null
            )

            // Save to Room DB with proper session ID
            val sessionId = "SM-${System.currentTimeMillis()}"
            withContext<Unit>(Dispatchers.IO) {
                val db = com.sarpamitra.data.local.AppDatabase.getInstance(getApplication())
                db.patientSessionDao().insert(
                    com.sarpamitra.data.local.entity.PatientSession(
                        sessionId = sessionId,
                        timestamp = System.currentTimeMillis(),
                        biteLocation = "",
                        symptomsJson = currentState.symptoms.joinToString(","),
                        snakePhotoPath = currentState.snakePhotoPath,
                        bitePhotoPath = currentState.bitePhotoPath,
                        severity = result.severity,
                        asvRequired = result.asvRequired,
                        asvType = result.asvType,
                        estimatedVials = result.estimatedVials,
                        urgency = result.urgency,
                        confidence = result.confidence,
                        reasoning = result.reasoning,
                        guardrailTriggered = result.guardrailTriggered,
                        referralGenerated = false,
                        syncStatus = "LOCAL"
                    )
                )
                // Rename swelling photos from SM-CURRENT to real session ID
                val appContext: android.content.Context = getApplication()
                com.sarpamitra.monitoring.SwellingPhotoManager.renameSession(
                    appContext, "SM-CURRENT", sessionId
                )

            }

            if (gemma.isModelMissing) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    isModelMissing = true,
                    result = result,
                    stage = ""
                )
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    result = result,
                    stage = ""
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        gemma.close()
    }
}