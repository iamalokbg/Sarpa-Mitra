# Sarpa-Mitra Voice Pipeline

## Overview

Sarpa-Mitra uses Android's built-in speech engines for all voice processing.
No external models. No internet. No third-party libraries.
Everything runs on-device, including in airplane mode.

---

## Pipeline Architecture
User speaks
↓
Android SpeechRecognizer (offline)
→ Language: hi-IN (Hindi) or en-US (English)
→ Requires offline language pack downloaded once
↓
Transcribed text → Gemma 4 E2B reasoning
↓
TriageResult → voice output text built
↓
Android TextToSpeech (offline)
→ Language: Locale("hi", "IN") or Locale.ENGLISH
→ Auto-plays on ResultsDashboard after 1 second
↓
User hears severity + action in their language

---

## Components

### Speech-to-Text (STT)

| Property | Value |
|---|---|
| Engine | Android SpeechRecognizer |
| Hindi locale | `hi-IN` |
| English locale | `en-US` |
| Internet required | No — offline language pack |
| Trigger | Hold-to-speak button in SymptomInput |
| Fallback | Symptom chip selection if STT fails |

**Offline setup required (one time):**
Phone Settings → General Management → Language
→ Speech Recognition → Download Hindi offline pack

### Text-to-Speech (TTS)

| Property | Value |
|---|---|
| Engine | Android TextToSpeech |
| Hindi locale | `Locale("hi", "IN")` |
| English locale | `Locale.ENGLISH` |
| Internet required | No |
| Auto-play | Yes — 1 second after ResultsDashboard opens |
| Also used | MonitoringScreen 15-minute prompts, SOS alert |

---

## Hindi Voice Output Examples

| Situation | Hindi Output |
|---|---|
| Critical severity | "गंभीर स्थिति। एंटीवेनम जरूरी है। जिला अस्पताल जाएं।" |
| Mild severity | "हल्की स्थिति। निगरानी जारी रखें।" |
| 15-min monitoring | "मरीज की जांच करें। कोई नया लक्षण? नया लक्षण बटन दबाएं।" |
| Patient stable | "मरीज ठीक है। निगरानी जारी है।" |
| SOS triggered | "मदद करो! सांप काटा है! मदद चाहिए!" |

---

## Language Toggle

Language is saved to SharedPreferences key `is_hindi` (boolean).

```kotlin
val prefs = context.getSharedPreferences("sarpa_prefs", Context.MODE_PRIVATE)
val isHindi = prefs.getBoolean("is_hindi", true) // Hindi is default
```

Toggle is accessible from Settings screen.
Change takes effect immediately on next voice interaction.

---

## Why We Chose Android Built-in Engines

We evaluated three approaches:

| Option | Size | Internet | Hindi Quality | Decision |
|---|---|---|---|---|
| Whisper.cpp tiny | 39MB extra | No | Good | Rejected — extra JNI complexity |
| Piper TTS | 50MB extra | No | Good | Rejected — APK size increase |
| Android built-in | 0MB extra | No (after pack) | Acceptable | ✅ Chosen |

**Decision rationale:**
- Target devices (Redmi 12, Realme C53) ship with Android TTS pre-installed
- Hindi offline pack is a one-time 50MB download over any connection
- Zero JNI compilation issues on Windows development environment
- Acceptable quality for emergency triage context

---

## Honest Limitations

- **Bhojpuri, Odia, Malayalam not supported** — dedicated dialect ASR models
  required. Post-hackathon scope.
- **Hindi STT accuracy** — Android's offline Hindi recognition is trained on
  standard Hindi, not rural dialects. Users with strong regional accents may
  need to use symptom chips instead.
- **TTS voice quality** — Android's built-in Hindi TTS is robotic compared to
  Piper. Acceptable for emergency use. Piper integration is planned.
- **One-time setup required** — offline Hindi pack must be downloaded before
  first use. App shows instruction on first launch.

---

## Offline Verification

Tested on Xiaomi 24094RAD4I in full airplane mode:

| Test | Result |
|---|---|
| Hindi STT — speak symptoms | ✅ Transcribed correctly |
| Hindi TTS — severity output | ✅ Played automatically |
| English STT — speak symptoms | ✅ Transcribed correctly |
| English TTS — severity output | ✅ Played automatically |
| Language toggle mid-session | ✅ Takes effect immediately |

**All voice features confirmed working with zero internet connectivity.**