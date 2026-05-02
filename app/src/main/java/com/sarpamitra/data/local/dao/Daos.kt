package com.sarpamitra.data.local.dao

import androidx.room.*
import com.sarpamitra.data.local.entity.Facility
import com.sarpamitra.data.local.entity.OutboxSms
import com.sarpamitra.data.local.entity.PatientSession

@Dao
interface PatientSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: PatientSession)

    @Query("SELECT * FROM patient_sessions ORDER BY timestamp DESC")
    suspend fun getAll(): List<PatientSession>

    @Query("SELECT * FROM patient_sessions WHERE syncStatus = 'LOCAL'")
    suspend fun getUnsynced(): List<PatientSession>

    @Query("UPDATE patient_sessions SET syncStatus = 'SYNCED' WHERE sessionId = :id")
    suspend fun markSynced(id: String)
}

@Dao
interface FacilityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(facilities: List<Facility>)

    @Query("SELECT * FROM facilities WHERE district = :district ORDER BY type ASC")
    suspend fun getByDistrict(district: String): List<Facility>

    @Query("SELECT * FROM facilities WHERE type = :type ORDER BY name ASC")
    suspend fun getByType(type: String): List<Facility>

    @Query("SELECT COUNT(*) FROM facilities")
    suspend fun count(): Int
}

@Dao
interface OutboxSmsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sms: OutboxSms)

    @Query("SELECT * FROM outbox_sms WHERE sent = 0")
    suspend fun getUnsent(): List<OutboxSms>

    @Query("UPDATE outbox_sms SET sent = 1 WHERE id = :id")
    suspend fun markSent(id: String)
}