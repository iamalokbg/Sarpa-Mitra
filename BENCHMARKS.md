# Sarpa-Mitra Benchmarks

## Test Environment

| Property | Value                            |
|---|----------------------------------|
| Device | Redmi 14                         |
| RAM | 4GB                              |
| Chipset | Helio G88                        |
| Android | 11+                              |
| Network | Airplane mode (fully offline)    |
| Model | gemma-4-E2B-it.litertlm (2.58GB) |
| Runtime | LiteRT-LM 0.11.0 — CPU backend   |

---

## Results

| Metric | Result |
|---|---|
| Model file size | 2.58 GB |
| Model load time (cold) | ~45 seconds |
| Model load time (warm) | ~3-8 seconds |
| Triage inference (text) | ~2-6 seconds* |
| Peak RAM usage | 1.8-2.4 GB** |
| Offline verified | ✅ Airplane mode |

---

## Clinical Input Parameters

| Parameter | Supported | Notes |
|---|---|---|
| Symptoms | ✅ | 6 chips + voice |
| Patient age | ✅ | 4 age groups → guardrail Rule 3 |
| Hours since bite | ✅ | Editable in monitoring → guardrail Rule 4 |
| Wound photo | ✅ | Documented, stored with case |


## Measurement Method

Inference time measured manually with stopwatch:
- Start: user taps ANALYZE NOW
- Stop: ResultsDashboard appears on screen
- 10 consecutive runs, airplane mode enabled throughout

---

## Vision Inference Note

Gemma 4 E2B natively supports multimodal vision input (image + text).
On 4GB devices, adding image tokens to the inference context causes OOM crash
due to insufficient RAM headroom after model loading (~1.8GB) and Android OS (~1.5GB).

**Resolution:**
- Photos are captured and stored with each case for hospital documentation
- Text-only triage runs reliably at ~2.4 seconds
- Full visual wound assessment (fang mark analysis, swelling grading, necrosis detection)
  demonstrated on 6GB+ devices in the `Sarpa-Mitra-Gemma4` branch

---

## Why CPU Backend

LiteRT-LM supports both CPU and GPU backends. We chose CPU for two reasons:

1. **Stability** — GPU backend requires OpenCL support. Our target devices
   (Redmi 12, Realme C53) use MediaTek chipsets with inconsistent OpenCL driver
   implementations. CPU backend runs reliably on all Android 11+ devices.

2. **Emergency reliability** — A crash during GPU inference in an emergency
   is worse than slower CPU inference. 2.4 seconds is acceptable for triage.

---

## Target Device Profile

Sarpa-Mitra targets the most common Android device in rural India:

| Spec | Minimum | Target |
|---|---|---|
| RAM | 4GB | 4-6GB |
| Storage | 32GB | 32GB+ |
| Android | 11 | 11-13 |
| Chipset | Any | MediaTek Helio G85/G88 |
| Camera | Any + flash | 50MP+ |
| Price range | $100-150 USD | Redmi 12 / Realme C53 |

---

## Model Delivery Performance

| Step | Time |
|---|---|
| USB transfer (laptop → phone) | ~60 seconds (40MB/s) |
| Internal copy (/tmp → /files) | ~2 minutes |
| First app launch after model | ~45 seconds load |
| Subsequent launches | ~3 seconds |

Model is sideloaded once at block office USB session.
No repeated downloads required.