package com.masih.callrecorder.service
import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
class CallAccessibilityService : AccessibilityService() {
    override fun onServiceConnected() { super.onServiceConnected() }
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event ?: return
        if (event.packageName?.contains("com.android.phone") == true || event.packageName?.contains("com.android.dialer") == true) {
            sendBroadcast(Intent(CALL_UI_EVENT))
        }
    }
    override fun onInterrupt() {}
    companion object { const val CALL_UI_EVENT = "com.masih.callrecorder.CALL_UI_EVENT" }
}