package com.masih.callrecorder.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import com.masih.callrecorder.data.LicenseManager
import com.masih.callrecorder.service.RecordingService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CallReceiver : BroadcastReceiver() {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            scope.launch {
                val app = context.applicationContext as? com.masih.callrecorder.CallRecorderApp ?: return@launch
                val licenseManager = LicenseManager(app.prefsManager)
                if (!licenseManager.isLicensed()) return@launch
                if (app.prefsManager.autoRecord.first()) { RecordingService.startService(context) }
            }
        }
    }
}