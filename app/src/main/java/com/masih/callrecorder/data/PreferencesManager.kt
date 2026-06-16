package com.masih.callrecorder.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesManager(private val context: Context) {
    companion object {
        val KEY_AUTO_RECORD = booleanPreferencesKey("auto_record")
        val KEY_INSTALL_DATE = longPreferencesKey("install_date")
        val KEY_LICENSE_EXPIRY = longPreferencesKey("license_expiry")
        val KEY_TRIAL_STARTED = booleanPreferencesKey("trial_started")
        val KEY_IS_PREMIUM = booleanPreferencesKey("is_premium")
        val KEY_AUDIO_FORMAT = stringPreferencesKey("audio_format")
        val KEY_AUDIO_SOURCE = intPreferencesKey("audio_source")
        val KEY_AUDIO_BITRATE = intPreferencesKey("audio_bitrate")
    }

    val autoRecord: Flow<Boolean> = context.dataStore.data.map { it[KEY_AUTO_RECORD] ?: true }
    val isPremium: Flow<Boolean> = context.dataStore.data.map { it[KEY_IS_PREMIUM] ?: false }
    val installDate: Flow<Long> = context.dataStore.data.map { it[KEY_INSTALL_DATE] ?: 0L }
    val licenseExpiry: Flow<Long> = context.dataStore.data.map { it[KEY_LICENSE_EXPIRY] ?: 0L }
    val trialStarted: Flow<Boolean> = context.dataStore.data.map { it[KEY_TRIAL_STARTED] ?: false }
    val audioFormat: Flow<String> = context.dataStore.data.map { it[KEY_AUDIO_FORMAT] ?: "aac" }
    val audioSource: Flow<Int> = context.dataStore.data.map { it[KEY_AUDIO_SOURCE] ?: 0 }
    val audioBitrate: Flow<Int> = context.dataStore.data.map { it[KEY_AUDIO_BITRATE] ?: 128000 }

    suspend fun setAutoRecord(enabled: Boolean) { context.dataStore.edit { it[KEY_AUTO_RECORD] = enabled } }
    suspend fun setPremium(premium: Boolean) { context.dataStore.edit { it[KEY_IS_PREMIUM] = premium } }
    suspend fun setInstallDate(date: Long) { context.dataStore.edit { it[KEY_INSTALL_DATE] = date } }
    suspend fun setLicenseExpiry(expiry: Long) { context.dataStore.edit { it[KEY_LICENSE_EXPIRY] = expiry } }
    suspend fun setTrialStarted(started: Boolean) { context.dataStore.edit { it[KEY_TRIAL_STARTED] = started } }
    suspend fun setAudioFormat(format: String) { context.dataStore.edit { it[KEY_AUDIO_FORMAT] = format } }
    suspend fun setAudioSource(source: Int) { context.dataStore.edit { it[KEY_AUDIO_SOURCE] = source } }
    suspend fun setAudioBitrate(bitrate: Int) { context.dataStore.edit { it[KEY_AUDIO_BITRATE] = bitrate } }
}