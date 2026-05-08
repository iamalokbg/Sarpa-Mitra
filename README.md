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

| Feature | What it means in the field |
|---|---|
| Voice triage — Hindi + English | A panicking family member can speak instead of type |
| Wound photo documentation | Visual record saved for hospital review |
| Gemma 4 E2B on-device | Real AI reasoning without internet |
| Hardcoded clinical guardrails | AI output verified against WHO/ICMR before it reaches the user |
| Tier-based hospital routing | Honest — no fake stock data, just institutional probability |
| QR referral slip | Doctor scans at hospital, sees full case history |
| 15-minute monitoring | Voice reminds carer to check symptoms every 15 minutes |
| 90-second silence SOS | If patient loses consciousness, app screams for help |

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
User speaks symptoms in Hindi
↓
Android offline STT (no internet)
↓
Gemma 4 E2B — LiteRT-LM CPU backend
(2.58GB model, runs fully on device)
↓
ClinicalGuardrail.kt
(5 hardcoded WHO/ICMR rules override AI output)
↓
Severity dashboard + voice output
↓
QR referral slip → Hospital routing

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

