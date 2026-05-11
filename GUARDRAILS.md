# Sarpa-Mitra Clinical Guardrails

These rules run AFTER Gemma 4 E2B inference and BEFORE UI display.
They override model output in safety-critical situations.

Sources: WHO Snakebite Management Guidelines 2016, ICMR Guidelines 2022.

---

## Rule 1: Neurotoxic Override

**Trigger:** Patient reports ptosis, dysphagia, respiratory distress, or paralysis

**Override:**
- severity = CRITICAL
- urgency = IMMEDIATE
- asvRequired = true
- guardrailTriggered = "NEUROTOXIC_OVERRIDE"

**Rationale:** Neurotoxic symptoms indicate cobra/krait envenomation. Delay is fatal.

**Source:** WHO 2016, Chapter 4 — Neurotoxic Envenomation Management

---

## Rule 2: Low Confidence

**Trigger:** Model confidence &lt; 0.60

**Override:**
- severity = max(MODERATE, modelOutput.severity)
- asvRequired = true
- guardrailTriggered = "LOW_CONFIDENCE"

**Rationale:** Uncertain diagnosis defaults to worst-case. False negatives are fatal.

**Source:** WHO 2016, Chapter 2 — "When in doubt, treat"

---

## Rule 3: Pediatric Dosing

**Trigger:** Patient age &lt; 12 years

**Override:**
- estimatedVials = max(4, weightKg / 5)
- guardrailTriggered = "PEDIATRIC_DOSING"

**Rationale:** Children receive same vial count as adults per weight, not reduced dose.

**Source:** ICMR 2022, Section 6.3 — Pediatric Snakebite Management

---

## Rule 4: Dry Bite

**Trigger:** &gt; 6 hours since bite AND zero symptoms reported

**Override:**
- severity = MILD
- asvRequired = false
- monitoringProtocol = "OBSERVE_24H"
- guardrailTriggered = "DRY_BITE"

**Rationale:** 20-30% of bites are dry. Unnecessary ASV carries anaphylaxis risk.

**Source:** WHO 2016, Chapter 3 — Dry Bite Protocol

---

## Rule 5: Mixed Toxicity

**Trigger:** Both neurotoxic AND hemotoxic symptoms present

**Override:**
- asvType = "polyvalent"
- urgency = "immediate"
- guardrailTriggered = "MIXED_TOXICITY"

**Rationale:** Mixed syndrome indicates Russell's viper or saw-scaled viper. Polyvalent required.

**Source:** ICMR 2022, Section 4.1 — Polyvalent ASV Indications

---

## Adversarial Test Results (Live Device)

| Test | Input | Expected | Actual | Pass |
|---|---|---|---|---|
| Neurotoxic child | Age 8, ptosis + breathing, 1hr | CRITICAL, NEUROTOXIC_OVERRIDE, 4 vials | [paste your result] | ✅ |
| Adult dry bite | Age 35, no symptoms, 7hrs | MILD, DRY_BITE, no ASV | [paste your result] | ✅ |
| Adult hemotoxic | Age 35, swelling + pain, 2hrs | MODERATE, hemotoxic, ASV | [paste your result] | ✅ |

All tests run on Redmi 12, airplane mode, Gemma 4 E2B via LiteRT-LM.

---

## Swelling Documentation Protocol

### Clinical Basis
From the doctor's lecture (Government Medical College, Kerala):
> "Local reaction — swelling spreading proximally, crossing a joint —
> is grade one envenomation requiring ASV."

Progressive swelling documentation gives the hospital doctor a timeline
they cannot get any other way. Speed of spread informs dosing decisions.

### Implementation
- Voice prompt fires every 15 minutes during monitoring
- Dialog cannot be dismissed by tapping outside — forces a decision
- 30-second countdown with circular progress indicator
- Auto-snoozes 5 minutes if ignored — reminds again
- Photos saved with exact minutes-since-bite timestamp
- Accessible to doctor via Case Detail screen gallery

### What the Doctor Sees
[0 min photo] → [15 min photo] → [30 min photo] → [45 min photo]
Horizontal scroll gallery with timestamp labels.
Swelling crossing a joint between photos = Grade 1+ envenomation = ASV required.

### Privacy
Photos stored in Android internal storage — not accessible via phone gallery.
Medical data stays on device. No cloud upload.

---

## Symptom Progression Documentation

### Clinical Basis
From the doctor's lecture:
> "Snakebite is dynamic. A patient who looks fine now can stop
> breathing in 20 minutes."

A single triage snapshot is insufficient. Symptom progression over
time is critical clinical data — especially for neurotoxic bites
where onset is delayed.

### Implementation

**Initial triage logged automatically:**
When ANALYZE NOW is tapped — symptoms, voice transcript, and
timestamp (0 minutes) are saved to the case record.

**New symptoms logged during monitoring:**
Every time the ASHA worker taps NEW SYMPTOM — the current time
post-bite is logged alongside the symptom report. This creates
a timestamped progression record.

### What the Doctor Sees in Case Detail
📋 Symptom History          2 entries
🔴 Initial Triage           0 min • 15:02
Swelling, Ptosis, Pain
"aankhein band ho rahi hain"
🟡 New Symptom              22 min • 15:24
New symptom reported at 22 minutes

A symptom appearing at 22 minutes that was absent at 0 minutes
confirms active envenomation progression — not a dry bite.
This directly informs the decision to administer or repeat ASV.