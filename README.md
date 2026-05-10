# Sarpa-Mitra 🐍
### *Works where the snake lives. Offline.*

---

## The Problem

Last year, a farmer in rural Bihar was bitten by a snake at night. His family had a smartphone. They had no signal. They had no idea what to do next. They applied a tourniquet — the worst possible thing to do. By the time they reached a hospital, the delay had cost him his life.

**58,000 Indians die from snakebite every year. Most have phones. None have signal.**

Existing apps fail rural India in three ways:
- They require internet to function
- They assume the user can read

Sarpa-Mitra was built to solve these problems.

---

## What It Does

Sarpa-Mitra is a voice-first, offline emergency triage app for ASHA workers and family members in rural India. No internet. No literacy required. No snake identification needed.

You speak. It listens. It tells you what to do — out loud, in local language.

| Feature | Status |
|---|---|
| Voice triage — Hindi + English | ✅ |
| Patient age input (pediatric dosing) | ✅ |
| Hindi + English UI voice output | ✅ |
| WHO/ICMR first aid protocol | ✅ |
| Wound photo capture for documentation | ✅ |
| Gemma 4 E2B via LiteRT-LM | ✅ |
| Hardcoded clinical guardrails (WHO/ICMR) | ✅ |
| Tier-based hospital routing | ✅ |
| QR referral slip generation | ✅ |
| 15-minute monitoring with auto-SOS | ✅ |
| 90-second silence emergency trigger | ✅ |
| Swelling photo timeline (every 15 min) | ✅ |
| Timestamped photo gallery for doctor | ✅ |
| 30-second countdown photo reminder | ✅ |
| Photo count on referral slip | ✅ |

---

## The Design Philosophy

**Every decision was made for one user: a terrified family member in a dark field at midnight, with shaking hands and zero signal.**

- No menus during emergency flow
- Every instruction read aloud automatically
- Minimum touch target: 64dp
- If the AI is unsure → it defaults to worst-case and tells you
- If vision fails → voice takes over
- If voice fails → giant Yes/No buttons
- If nothing works → 90 seconds of silence triggers automatic SOS

---

## Architecture

```
User speaks symptoms in Hindi / taps symptom chips
        ↓
Patient age selected (Child / Teen / Adult / Elder)
        ↓
Android offline STT — hi-IN / en-US (no internet)
        ↓
Wound photo captured → stored with case record
        ↓
Gemma 4 E2B — LiteRT-LM CPU backend
(2.58GB model, runs fully on device, ~2.4s inference)
        ↓
ClinicalGuardrail.kt
(5 hardcoded WHO/ICMR rules override AI output)
Rule 1: Ptosis/breathing → CRITICAL regardless of confidence
Rule 2: Confidence < 60% → force ASV + flag uncertainty
Rule 3: Age < 12 → weight-based pediatric vial dosing
Rule 4: 6hr + zero symptoms → dry bite, no ASV
Rule 5: Neuro + hemo signs → polyvalent ASV, max urgency
        ↓
Severity dashboard — Hindi/English voice output (TTS)
        ↓
┌─────────────────────────────────────────┐
│         Monitoring Mode (optional)       │
│  Every 15 min → voice prompt            │
│  30-second countdown dialog             │
│  ASHA captures swelling photo           │
│  Timestamped → saved to case record     │
│  90-second silence → SOS alarm + flash  │
└─────────────────────────────────────────┘
        ↓
QR referral slip
→ Case ID, severity, syndrome, ASV, vials, guardrail
→ Swelling photo count + "Ask ASHA to show phone"
→ "For the Receiving Doctor" card (WBCT reminder)
        ↓
Hospital routing (tier-based probability)
District Hospital → HIGH probability
Sub-District → Medium-High
PHC → Variable ⚠️
        ↓
Doctor scans QR → opens Case Detail screen
→ Swelling photo gallery (horizontal scroll, timestamped)
→ Visual progression confirms envenomation grade
```

---

## Technical Stack

| Layer | Technology | Why |
|---|---|---|
| AI Core | Gemma 4 E2B — LiteRT-LM | Only model that fits on 4GB device AND runs Gemma 4 |
| UI | Jetpack Compose, dark mode only | Zero cognitive load in emergency |
| Voice STT | Android SpeechRecognizer offline | No Whisper dependency, works on cheap phones |
| Voice TTS | Android TextToSpeech | Hindi + English, zero internet |
| Camera | CameraX + blur detection | Wound documentation, rejects blurry photos |
| Database | Room SQLite | All cases stored locally, USB sync to block office |
| OS | Android 11+, 4GB RAM | Target device: Redmi 12 / Realme C53 |

---

## Clinical Guardrails — Why AI Alone Isn't Enough

We don't trust the AI to make medical decisions alone.

Five hardcoded rules run **after** Gemma 4 inference and **before** anything reaches the screen. These rules override the model regardless of its confidence:

1. **Ptosis or breathing difficulty reported → CRITICAL, immediate ASV, regardless of model output**
2. **Confidence below 60% → default to ASV required, flag uncertainty**
3. **Patient under 12 → weight-based vial calculation**
4. **6+ hours with zero symptoms → dry bite protocol, no ASV**
5. **Both neurotoxic and hemotoxic signs → polyvalent ASV, maximum urgency**

This hybrid approach — LLM flexibility + hardcoded safety — is why we trust this app in the field.

See [GUARDRAILS.md](GUARDRAILS.md) for full rules with WHO/ICMR citations.

---

## Honest Failures and Limitations

We believe showing what doesn't work is as important as showing what does.

- **Vision inference disabled on 4GB devices** — adding image tokens to Gemma 4 on 4GB RAM causes OOM crash. Photos are captured and saved for hospital documentation. Full visual wound assessment (fang marks, swelling grade, necrosis) runs on 6GB+ devices and is demonstrated in the `Sarpa-Mitra-Gemma4` branch.
- **Hindi and English only** — Bhojpuri, Odia, and Malayalam require dedicated dialect ASR models. Post-hackathon scope.
- **USB model delivery** — 2.58GB cannot download over rural data connections. Model is sideloaded via USB at monthly ASHA block office visits.
- **No real-time ASV stock data** — we replaced fake stock claims with honest tier-based routing. District Hospitals have government mandate to stock ASV. PHCs may run out.

---

## Benchmarks

**Device:** Xiaomi 24094RAD4I (4GB RAM, Helio G88)
**Model:** gemma-4-E2B-it.litertlm (2.58GB) via LiteRT-LM CPU backend
**Network:** Airplane mode throughout

| Metric | Result |
|---|---|
| Model load (first launch) | ~45 seconds |
| Triage inference (text + symptoms) | ~2.4 seconds average |
| Offline verified | ✅ |

See [BENCHMARKS.md](BENCHMARKS.md) for full results.

---

## Model Delivery
ASHA monthly meeting at block office
↓
Technician connects USB OTG
↓
2.58GB model copied to /files/models/
↓
App detects model on next launch
↓
Works offline forever after

APK size: ~50MB. Model is a separate payload, not bundled.

---

## The Challenges We Faced

**1. Kotlin version war**
LiteRT-LM 0.11.0 requires Kotlin 2.2. Our project used 2.0.21. Upgrading caused KSP incompatibility. Fix: `-Xskip-metadata-version-check` flag.

**2. MediaPipe → LiteRT-LM migration**
Gemma 4 doesn't exist in MediaPipe `.bin` format. Had to migrate entire inference pipeline to LiteRT-LM SDK mid-build. Three days, zero Android experience at start of project.

**3. Vision OOM on 4GB**
Gemma 4 vision inference requires more RAM than available on 4GB devices. We documented this honestly and kept photo capture for documentation only.

**4. `Engine.initialize()` missing**
Every inference call returned null silently. Two days of debugging revealed a single missing line: `engine?.initialize()`.

**5. Hindi STT in airplane mode**
Android's offline Hindi speech recognition requires manual language pack download. Added explicit instruction in onboarding.

---

## Installation

1. Download APK from [Releases](https://github.com/iamalokbg/Sarpa-Mitra/releases)
2. Install on Android 11+ device with 4GB+ RAM
3. Visit block office for USB model sideload
4. App shows "Model missing" screen until model is loaded

---

## Competition Tracks

- Main Track
- Health & Sciences Impact Track
- LiteRT Special Technology Prize

---

