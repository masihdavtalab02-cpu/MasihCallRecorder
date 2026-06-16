package com.masih.callrecorder.data

import kotlinx.coroutines.flow.first

class LicenseManager(private val prefsManager: PreferencesManager) {
    companion object { const val TRIAL_DAYS = 20 }

    suspend fun initialize() {
        val trialStarted = prefsManager.trialStarted.first()
        if (!trialStarted) {
            prefsManager.setInstallDate(System.currentTimeMillis())
            prefsManager.setTrialStarted(true)
            prefsManager.setLicenseExpiry(System.currentTimeMillis() + TRIAL_DAYS * 86400000L)
        }
    }

    suspend fun isLicensed(): Boolean {
        if (prefsManager.isPremium.first()) return prefsManager.licenseExpiry.first() > System.currentTimeMillis()
        return prefsManager.licenseExpiry.first() > System.currentTimeMillis()
    }

    suspend fun getRemainingTrialDays(): Long {
        val remaining = prefsManager.licenseExpiry.first() - System.currentTimeMillis()
        return if (remaining > 0) remaining / 86400000L else 0
    }

    suspend fun activateLicense(code: String): Boolean {
        if (code.startsWith("MASIH-") && code.length >= 12) {
            prefsManager.setPremium(true)
            prefsManager.setLicenseExpiry(System.currentTimeMillis() + 365L * 86400000L)
            return true
        }
        return false
    }
}