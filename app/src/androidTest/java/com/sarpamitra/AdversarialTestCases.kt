package com.sarpamitra

import com.sarpamitra.guardrails.ClinicalGuardrail
import com.sarpamitra.guardrails.TriageResult
import org.junit.Assert.*
import org.junit.Test

class AdversarialTestCases {

    // TEST 1: Neurotoxic override — model says MILD but ptosis present
    @Test
    fun neurotoxicOverride_forcesCritical() {
        val modelOutput = TriageResult(
            severity = "MILD",
            confidence = 0.3f,
            asvRequired = false
        )
        val symptoms = listOf("ptosis", "leg swelling")
        val result = ClinicalGuardrail.apply(modelOutput, symptoms, null, 2f)

        assertEquals("CRITICAL", result.severity)
        assertEquals("immediate", result.urgency)
        assertTrue(result.asvRequired)
        assertEquals("NEUROTOXIC_OVERRIDE", result.guardrailTriggered)
    }

    // TEST 2: Low confidence — forces ASV and upgrades to MODERATE
    @Test
    fun lowConfidence_forcesAsvAndModerate() {
        val modelOutput = TriageResult(
            severity = "MILD",
            confidence = 0.55f,
            asvRequired = false
        )
        val result = ClinicalGuardrail.apply(modelOutput, listOf("swelling"), null, 1f)

        assertEquals("MODERATE", result.severity)
        assertTrue(result.asvRequired)
        assertEquals("LOW_CONFIDENCE", result.guardrailTriggered)
    }

    // TEST 3: Dry bite — no symptoms after 7 hours
    @Test
    fun dryBite_noAsvNeeded() {
        val modelOutput = TriageResult(
            severity = "MODERATE",
            confidence = 0.8f,
            asvRequired = true
        )
        val result = ClinicalGuardrail.apply(modelOutput, emptyList(), null, 7f)

        assertEquals("MILD", result.severity)
        assertFalse(result.asvRequired)
        assertEquals("routine", result.urgency)
        assertEquals("DRY_BITE", result.guardrailTriggered)
    }

    // TEST 4: Pediatric — age 8, weight-based dosing
    @Test
    fun pediatric_weightBasedVials() {
        val modelOutput = TriageResult(
            severity = "MODERATE",
            confidence = 0.75f,
            asvRequired = true,
            estimatedVials = 10
        )
        val result = ClinicalGuardrail.apply(modelOutput, listOf("swelling"), 8, 1f)

        // Weight = 8*2+8 = 24kg → vials = 24/5 = 4
        assertEquals(4, result.estimatedVials)
        assertEquals("PEDIATRIC_DOSING", result.guardrailTriggered)
    }

    // TEST 5: Mixed toxicity — both neuro and hemo signs
    @Test
    fun mixedToxicity_polyvalentMaxUrgency() {
        val modelOutput = TriageResult(
            severity = "SEVERE",
            confidence = 0.8f,
            asvRequired = true,
            asvType = "monovalent_cobra"
        )
        val symptoms = listOf("ptosis", "bleeding", "swelling")
        val result = ClinicalGuardrail.apply(modelOutput, symptoms, null, 1f)

        assertEquals("polyvalent", result.asvType)
        assertEquals("immediate", result.urgency)
        assertEquals("MIXED_TOXICITY", result.guardrailTriggered)
    }
}