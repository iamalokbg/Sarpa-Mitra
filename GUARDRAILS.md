# Sarpa-Mitra Clinical Guardrails

These rules run AFTER Gemma 4 inference and BEFORE UI display.
They override model output in safety-critical situations.
Sources: WHO Snakebite Management Guidelines 2016, ICMR Guidelines 2022.

## Rule 1: Neurotoxic Override
**Trigger:** Patient reports ptosis, dysphagia, respiratory distress, or paralysis  
**Override:** severity = CRITICAL, urgency = IMMEDIATE, ASV = required  
**Rationale:** Neurotoxic symptoms indicate cobra/krait envenomation. Delay is fatal.  
**Source:** WHO 2016, Chapter 4 — Neurotoxic Envenomation Management

## Rule 2: Low Confidence
**Trigger:** Model confidence < 0.60  
**Override:** severity = max(MODERATE, model), asvRequired = true  
**Rationale:** Uncertain diagnosis defaults to worst-case. False negatives are fatal.  
**Source:** WHO 2016, Chapter 2 — "When in doubt, treat"

## Rule 3: Pediatric Dosing
**Trigger:** Patient age < 12 years  
**Override:** Vial count = (estimated weight kg / 5), minimum 4 vials  
**Rationale:** Children receive same vial count as adults per weight, not reduced dose.  
**Source:** ICMR 2022, Section 6.3 — Pediatric Snakebite Management

## Rule 4: Dry Bite
**Trigger:** > 6 hours since bite AND zero symptoms reported  
**Override:** severity = MILD, asvRequired = false, observe 24h  
**Rationale:** 20-30% of bites are dry. Unnecessary ASV carries anaphylaxis risk.  
**Source:** WHO 2016, Chapter 3 — Dry Bite Protocol

## Rule 5: Mixed Toxicity
**Trigger:** Both neurotoxic AND hemotoxic symptoms present  
**Override:** asvType = polyvalent, urgency = immediate  
**Rationale:** Mixed syndrome indicates Russell's viper or saw-scaled viper. Polyvalent required.  
**Source:** ICMR 2022, Section 4.1 — Polyvalent ASV Indications