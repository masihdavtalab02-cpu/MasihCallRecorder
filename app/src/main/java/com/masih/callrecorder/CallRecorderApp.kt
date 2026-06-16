package com.masih.callrecorder

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.masih.callrecorder.data.AppDatabase
import com.masih.callrecorder.data.PreferencesManager

class CallRecorderApp : Application() {
    lateinit var database: AppDatabase
        private set
    lateinit var prefsManager: PreferencesManager
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = AppDatabase.getInstance(this)
        prefsManager = PreferencesManager(this)
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_RECORDING,
                "ضبط مکالمه",
                NotificationManager.IMPORTANCE_LOW
            ).apply { description = "اعلان هنگام ضبط مکالمه" }
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_RECORDING = "call_recording_channel"
        lateinit var instance: CallRecorderApp
            private set
    }
}
