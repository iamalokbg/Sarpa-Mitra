package com.sarpamitra.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patient_sessions")
data class PatientSession(
    @PrimaryKey val sessionId: String,
    val timestamp: Long,
    val biteLocation: String,
    val symptomsJson: String,
    val snakePhotoPath: String?,
    val bitePhotoPath: String?,
    val severity: String,
    val asvRequired: Boolean,
    val asvType: String?,
    val estimatedVials: Int?,
    val urgency: String,
    val confidence: Float,
    val reasoning: String,
    val guardrailTriggered: String?,
    val referralGenerated: Boolean,
    val syncStatus: String
)

@Entity(tableName = "facilities")
data class Facility(
    @PrimaryKey val facilityId: String,
    val name: String,
    val type: String,
    val district: String,
    val latitude: Double,
    val longitude: Double
)

@Entity(tableName = "outbox_sms")
data class OutboxSms(
    @PrimaryKey val id: String,
    val caseId: String,
    val districtCode: String,
    val timestamp: Long,
    val sent: Boolean
)