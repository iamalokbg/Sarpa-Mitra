package com.sarpamitra.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sarpamitra.data.local.dao.FacilityDao
import com.sarpamitra.data.local.dao.OutboxSmsDao
import com.sarpamitra.data.local.dao.PatientSessionDao
import com.sarpamitra.data.local.entity.Facility
import com.sarpamitra.data.local.entity.OutboxSms
import com.sarpamitra.data.local.entity.PatientSession

@Database(
    entities = [PatientSession::class, Facility::class, OutboxSms::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientSessionDao(): PatientSessionDao
    abstract fun facilityDao(): FacilityDao
    abstract fun outboxSmsDao(): OutboxSmsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sarpa_mitra_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}