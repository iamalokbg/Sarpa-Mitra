package com.sarpamitra.monitoring

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class SymptomEntry(
    val symptoms: List<String>,
    val transcript: String,
    val minutesSinceBite: Int,
    val timeLabel: String,
    val timestamp: Long
)

object SymptomHistoryManager {

    private const val PREFS_KEY = "symptom_history"

    fun logSymptoms(
        context: Context,
        caseId: String,
        symptoms: List<String>,
        transcript: String,
        minutesSinceBite: Int
    ) {
        val prefs = context.getSharedPreferences("sarpa_symptoms_$caseId", Context.MODE_PRIVATE)
        val existing = prefs.getString(PREFS_KEY, "[]") ?: "[]"
        val array = JSONArray(existing)
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val entry = JSONObject().apply {
            put("symptoms", symptoms.joinToString(", "))
            put("transcript", transcript)
            put("minutes", minutesSinceBite)
            put("timeLabel", sdf.format(Date()))
            put("timestamp", System.currentTimeMillis())
        }
        array.put(entry)
        prefs.edit().putString(PREFS_KEY, array.toString()).apply()
    }

    fun getHistory(context: Context, caseId: String): List<SymptomEntry> {
        val prefs = context.getSharedPreferences("sarpa_symptoms_$caseId", Context.MODE_PRIVATE)
        val json = prefs.getString(PREFS_KEY, "[]") ?: "[]"
        val array = JSONArray(json)
        val result = mutableListOf<SymptomEntry>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            result.add(
                SymptomEntry(
                    symptoms = obj.getString("symptoms").split(", ").filter { it.isNotEmpty() },
                    transcript = obj.getString("transcript"),
                    minutesSinceBite = obj.getInt("minutes"),
                    timeLabel = obj.getString("timeLabel"),
                    timestamp = obj.getLong("timestamp")
                )
            )
        }
        return result
    }

    fun renameSession(context: Context, oldCaseId: String, newCaseId: String) {
        val oldPrefs = context.getSharedPreferences("sarpa_symptoms_$oldCaseId", Context.MODE_PRIVATE)
        val json = oldPrefs.getString(PREFS_KEY, "[]") ?: "[]"
        if (json == "[]") return
        val newPrefs = context.getSharedPreferences("sarpa_symptoms_$newCaseId", Context.MODE_PRIVATE)
        newPrefs.edit().putString(PREFS_KEY, json).apply()
        oldPrefs.edit().clear().apply()
    }

    fun clearHistory(context: Context, caseId: String) {
        context.getSharedPreferences("sarpa_symptoms_$caseId", Context.MODE_PRIVATE)
            .edit().clear().apply()
    }
}