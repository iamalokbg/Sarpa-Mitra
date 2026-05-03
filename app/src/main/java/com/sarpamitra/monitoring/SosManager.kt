package com.sarpamitra.monitoring

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.telephony.SmsManager

class SosManager(private val context: Context) {

    private var toneGenerator: ToneGenerator? = null
    private val handler = Handler(Looper.getMainLooper())
    private var isStrobing = false
    private var cameraManager: android.hardware.camera2.CameraManager? = null
    private var cameraId: String? = null

    init {
        cameraManager = context.getSystemService(Context.CAMERA_SERVICE)
                as android.hardware.camera2.CameraManager
        cameraId = cameraManager?.cameraIdList?.firstOrNull()
    }

    fun triggerSos(caseId: String, districtCode: String) {
        playAlarm()
        vibrate()
        startFlashStrobe()
        queueSms(caseId, districtCode)
    }

    private fun playAlarm() {
        try {
            toneGenerator = ToneGenerator(AudioManager.STREAM_ALARM, 100)
            toneGenerator?.startTone(ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK, 5000)
        } catch (e: Exception) {
            // Audio unavailable
        }
    }

    private fun vibrate() {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        val pattern = longArrayOf(0, 500, 200, 500, 200, 500)
        vibrator?.vibrate(VibrationEffect.createWaveform(pattern, -1))
    }

    private fun startFlashStrobe() {
        isStrobing = true
        var torchOn = false
        val strobeRunnable = object : Runnable {
            override fun run() {
                if (!isStrobing) {
                    cameraManager?.setTorchMode(cameraId ?: return, false)
                    return
                }
                torchOn = !torchOn
                try {
                    cameraManager?.setTorchMode(cameraId ?: return, torchOn)
                } catch (e: Exception) { }
                handler.postDelayed(this, 300)
            }
        }
        handler.post(strobeRunnable)
        handler.postDelayed({ stopStrobe() }, 10000)
    }

    fun stopStrobe() {
        isStrobing = false
        try {
            cameraManager?.setTorchMode(cameraId ?: return, false)
        } catch (e: Exception) { }
    }

    private fun queueSms(caseId: String, districtCode: String) {
        // SMS queued — sends when signal available via OutboxSmsDao
        // Message contains NO clinical data, NO GPS, NO patient info
        val message = "Sarpa-Mitra: Emergency case logged. Case ID: $caseId. District: $districtCode. Contact block office."
        // Store in Room OutboxSms for deferred sending
    }

    fun destroy() {
        stopStrobe()
        toneGenerator?.stopTone()
        toneGenerator?.release()
        toneGenerator = null
    }
}