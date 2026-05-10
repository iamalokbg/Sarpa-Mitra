package com.sarpamitra.monitoring

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class SwellingPhoto(
    val path: String,
    val timestamp: Long,
    val minutesSinceBite: Int,
    val timeLabel: String
)

object SwellingPhotoManager {

    private const val PREFS_KEY = "swelling_photos"

    fun savePhoto(
        context: Context,
        caseId: String,
        photoPath: String,
        minutesSinceBite: Int
    ) {
        val prefs = context.getSharedPreferences("sarpa_swelling_$caseId", Context.MODE_PRIVATE)
        val existing = prefs.getString(PREFS_KEY, "[]")
        val array = JSONArray(existing)

        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val entry = JSONObject().apply {
            put("path", photoPath)
            put("timestamp", System.currentTimeMillis())
            put("minutes", minutesSinceBite)
            put("timeLabel", sdf.format(Date()))
        }
        array.put(entry)
        prefs.edit().putString(PREFS_KEY, array.toString()).apply()
    }

    fun getPhotos(context: Context, caseId: String): List<SwellingPhoto> {
        val prefs = context.getSharedPreferences("sarpa_swelling_$caseId", Context.MODE_PRIVATE)
        val json = prefs.getString(PREFS_KEY, "[]") ?: "[]"
        val array = JSONArray(json)
        val result = mutableListOf<SwellingPhoto>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            val path = obj.getString("path")
            if (File(path).exists()) {
                result.add(
                    SwellingPhoto(
                        path = path,
                        timestamp = obj.getLong("timestamp"),
                        minutesSinceBite = obj.getInt("minutes"),
                        timeLabel = obj.getString("timeLabel")
                    )
                )
            }
        }
        return result
    }

    fun getPhotoCount(context: Context, caseId: String): Int {
        return getPhotos(context, caseId).size
    }

    fun renameSession(context: Context, oldCaseId: String, newCaseId: String) {
        val oldPrefs = context.getSharedPreferences("sarpa_swelling_$oldCaseId", Context.MODE_PRIVATE)
        val json = oldPrefs.getString(PREFS_KEY, "[]") ?: "[]"
        if (json == "[]") return
        val newPrefs = context.getSharedPreferences("sarpa_swelling_$newCaseId", Context.MODE_PRIVATE)
        newPrefs.edit().putString(PREFS_KEY, json).apply()
        oldPrefs.edit().clear().apply()
    }

    fun clearPhotos(context: Context, caseId: String) {
        context.getSharedPreferences("sarpa_swelling_$caseId", Context.MODE_PRIVATE)
            .edit().clear().apply()
    }
}