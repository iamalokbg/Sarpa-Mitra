package com.sarpamitra

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sarpamitra.ai.GemmaInference
import com.sarpamitra.guardrails.TriageResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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
    val hoursSinceBite: Float? = null
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

    // Called from CameraScreen via Navigation — reuses existing Gemma instance
    suspend fun validateWoundPhoto(imagePath: String): Boolean {
        return gemma.validateWoundPhoto(imagePath)
    }

    fun runTriage() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null, stage = "Loading model...")

            val state = _state.value

            _state.value = _state.value.copy(stage = "Analyzing wound photo...")
            kotlinx.coroutines.delay(600)

            _state.value = _state.value.copy(stage = "Cross-referencing symptoms...")
            kotlinx.coroutines.delay(600)

            _state.value = _state.value.copy(stage = "Applying clinical guardrails...")

            val result = gemma.triage(
                symptoms = state.symptoms,
                transcript = state.transcript,
                ageYears = null,
                hoursSinceBite = state.hoursSinceBite,
                hasSnakePhoto = state.snakePhotoPath != null,
                hasBitePhoto = state.bitePhotoPath != null,
                woundFindings = null
            )

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