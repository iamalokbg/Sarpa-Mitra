package com.sarpamitra.guardrails

data class TriageResult(
    val speciesIdentified: Boolean = false,
    val speciesGuess: String? = null,
    val syndrome: String = "unknown",
    val severity: String = "MODERATE",
    val asvRequired: Boolean = true,
    val asvType: String? = "polyvalent",
    val estimatedVials: Int? = 8,
    val urgency: String = "urgent",
    val firstAid: List<String> = emptyList(),
    val monitoringProtocol: String = "observe_6h",
    val confidence: Float = 0.5f,
    val reasoning: String = "",
    val guardrailTriggered: String? = null
)

object ClinicalGuardrail {

    fun apply(result: TriageResult, symptoms: List<String>, ageYears: Int?, hoursSinceBite: Float?): TriageResult {
        var r = result
        var guardrailFired: String? = null

        // RULE 1: Neurotoxic override
        val neurotoxicKeywords = listOf("ptosis", "drooping", "dysphagia", "swallowing", "respiratory", "breathing", "paralysis", "weakness")
        val hasNeurotoxic = symptoms.any { s -> neurotoxicKeywords.any { k -> s.lowercase().contains(k) } }
        if (hasNeurotoxic) {
            r = r.copy(
                severity = "CRITICAL",
                urgency = "immediate",
                asvRequired = true,
                asvType = "polyvalent",
                estimatedVials = 10,
                monitoringProtocol = "neuro_check_15min"
            )
            guardrailFired = "NEUROTOXIC_OVERRIDE"
        }

        // RULE 2: Low confidence override
        if (r.confidence < 0.60f && guardrailFired == null) {
            val currentSeverityRank = severityRank(r.severity)
            val moderateRank = severityRank("MODERATE")
            r = r.copy(
                severity = if (currentSeverityRank < moderateRank) "MODERATE" else r.severity,
                asvRequired = true
            )
            guardrailFired = "LOW_CONFIDENCE"
        }

        // RULE 3: Pediatric dosing
        if (ageYears != null && ageYears < 12) {
            val weightKg = estimatePediatricWeight(ageYears)
            val vials = (weightKg / 5).toInt().coerceAtLeast(4)
            r = r.copy(estimatedVials = vials)
            guardrailFired = guardrailFired ?: "PEDIATRIC_DOSING"
        }

        // RULE 4: Dry bite
        if (hoursSinceBite != null && hoursSinceBite > 6f && symptoms.isEmpty() && guardrailFired == null) {
            r = r.copy(
                severity = "MILD",
                asvRequired = false,
                urgency = "routine",
                monitoringProtocol = "observe_24h"
            )
            guardrailFired = "DRY_BITE"
        }

        // RULE 5: Mixed toxicity → polyvalent
        val hemotoxicKeywords = listOf("bleeding", "swelling", "bruising", "necrosis", "blood")
        val hasHemotoxic = symptoms.any { s -> hemotoxicKeywords.any { k -> s.lowercase().contains(k) } }
        if (hasNeurotoxic && hasHemotoxic) {
            r = r.copy(
                asvType = "polyvalent",
                urgency = "immediate"
            )
            // Only set guardrail tag if not already set by a higher priority rule
            if (guardrailFired == null) {
                guardrailFired = "MIXED_TOXICITY"
            }
        }

        return r.copy(guardrailTriggered = guardrailFired)
    }

    private fun severityRank(severity: String): Int = when (severity) {
        "MILD" -> 1
        "MODERATE" -> 2
        "SEVERE" -> 3
        "CRITICAL" -> 4
        else -> 2
    }

    private fun estimatePediatricWeight(ageYears: Int): Float {
        return if (ageYears < 1) 7f else (ageYears * 2 + 8).toFloat()
    }
}