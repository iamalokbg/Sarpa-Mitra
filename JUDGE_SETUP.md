# 🏆 Judge Setup Guide — Sarpa-Mitra

> **Quick start for hackathon judges evaluating Sarpa-Mitra offline snakebite triage.**

---

## What You Need

| Item | Source |
|------|--------|
| `sarpa-mitra-v1.0.0.apk` | [GitHub Release](https://github.com/iamalokbg/Sarpa-Mitra/releases/latest) |
| `gemma-4-e2b-it.litertlm` | Provided separately by organizers (2.58GB) |
| Android phone | Android 11+, 4GB RAM, 3.5GB free storage |
| USB OTG cable + USB drive (optional) | For model sideloading |

---

## Step 1: Install the APK

1. Download `sarpa-mitra-v1.0.0.apk` from the GitHub Release page
2. Transfer APK to the Android test device
3. Tap the APK to install
4. If blocked: **Settings → Security → Install unknown apps** → allow file manager
5. Open the app once

**Expected:** You will see a **"Model Missing"** screen. This is normal — the app is waiting for the AI model.

---

## Step 2: Push the AI Model

Copy `gemma-4-e2b-it.litertlm` to this **exact path** on the Android device:

```
/Android/data/com.yourpackage.sarpamitra/files/models/gemma-4-e2b-it.litertlm
```

### Method A: USB OTG (Recommended for Non-Technical Judges)
1. Copy `gemma-4-e2b-it.litertlm` to a USB drive
2. Connect USB drive to Android phone via **USB OTG cable**
3. Open a file manager (e.g., **Files by Google**, **Solid Explorer**)
4. Navigate to the USB drive → copy the `.litertlm` file
5. Paste into `/Android/data/com.yourpackage.sarpamitra/files/models/`

> ⚠️ **Note:** Android 11+ restricts access to `/Android/data/`. If the stock file manager blocks you, use **Solid Explorer** or **X-Plore File Manager** from the Play Store — both can access this path.

### Method B: ADB (For Technical Judges)
```bash
adb push gemma-4-e2b-it.litertlm /sdcard/Android/data/com.yourpackage.sarpamitra/files/models/
```

### Method C: Bluetooth / Nearby Share
1. Send `gemma-4-e2b-it.litertlm` to the phone via Bluetooth or Nearby Share
2. Use a file manager to move it from Downloads to the correct path above

---

## Step 3: Launch & Verify

1. **Reopen** Sarpa-Mitra
2. **First model load:** ~45 seconds (one-time only)
3. The app will show the **Home Screen** — model is now loaded

### Offline Verification Test
1. Toggle **Airplane Mode ON**
2. Open Sarpa-Mitra
3. Tap **"Start Triage"**
4. Speak or type symptoms in **Hindi or English**
5. The app should respond with severity, ASV vials, and first aid — **with zero internet**

---

## 📋 What to Evaluate

| Feature | How to Test |
|---------|-------------|
| **Voice Triage** | Tap mic icon → speak Hindi symptoms like "सांस फूलना, उल्टी" |
| **Age-Based ASV** | Select "Child" → verify pediatric dosing appears |
| **Guardrails** | Enter "ptosis" or "breathing difficulty" → verify it forces **Critical** |
| **Monitoring Mode** | Complete triage → tap "Start Monitoring" → wait for 15-min prompt |
| **QR Referral** | Finish triage → tap "Generate QR" → scan with another phone |
| **Photo Timeline** | In monitoring mode → capture wound photo → verify timestamp |
| **Airplane Mode** | Toggle airplane mode ON → entire app should function normally |

---

## 🐛 Troubleshooting

| Problem | Solution |
|---------|----------|
| "Model Missing" persists after copying | Double-check file path spelling; ensure `.litertlm` extension is correct |
| Cannot access `/Android/data/` folder | Use **Solid Explorer** or **X-Plore** file manager from Play Store |
| App crashes on launch | Close background apps; ensure 4GB+ RAM is free |
| Hindi voice not recognized | Download Hindi language pack in **Settings → Languages → Text-to-Speech** |
| Model load very slow | Expected on first launch (~45s); subsequent launches are instant |
| "App not installed" error | Ensure Android 11+; check "Install unknown apps" permission |

---

## 📂 File Checklist

After setup, the device should have:

```
/Android/data/com.yourpackage.sarpamitra/files/
├── models/
│   └── gemma-4-e2b-it.litertlm     ← 2.58GB (provided separately)
└── (app creates other folders automatically)
```

---

## ⚕️ Safety Note

This is an **emergency triage assistant**, not a replacement for hospital care. The app explicitly tells users to go to the hospital immediately. The 5 hardcoded clinical guardrails (see [GUARDRAILS.md](GUARDRAILS.md)) override the AI when critical symptoms are detected.

---

## 🔗 Links

- **GitHub Repo:** https://github.com/iamalokbg/Sarpa-Mitra
- **Latest Release:** https://github.com/iamalokbg/Sarpa-Mitra/releases/latest
- **Guardrails Doc:** [GUARDRAILS.md](GUARDRAILS.md)
- **Benchmarks Doc:** [BENCHMARKS.md](BENCHMARKS.md)

---

*Built for the farmer in Bihar at midnight, with shaking hands and zero signal.*
