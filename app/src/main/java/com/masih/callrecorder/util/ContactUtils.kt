package com.masih.callrecorder.util
import android.content.Context
import android.provider.ContactsContract
object ContactUtils {
    fun getContactName(context: Context, phoneNumber: String): String? {
        if (phoneNumber.isBlank()) return null
        val cleaned = phoneNumber.replace(Regex("[^0-9+]"), "")
        val uri = ContactsContract.PhoneLookup.CONTENT_FILTER_URI.buildUpon().appendPath(cleaned).build()
        try {
            context.contentResolver.query(uri, arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME), null, null, null)?.use {
                if (it.moveToFirst()) return it.getString(it.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME))
            }
        } catch (e: Exception) {}
        return null
    }
    fun getFormattedPhoneNumber(phoneNumber: String): String {
        if (phoneNumber.isBlank()) return "یاناام" 
        val cleaned = phoneNumber.replace(Regex("[^0-9+]"), "")
        return when {
            cleaned.startsWith("+98") -> "0${cleaned.substring(3)}"
            cleaned.startsWith("98") -> "0${cleaned.substring(2)}"
            else -> cleaned
        }
    }
}