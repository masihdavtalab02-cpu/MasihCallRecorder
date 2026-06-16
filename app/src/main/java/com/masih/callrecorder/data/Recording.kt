package com.masih.callrecorder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recordings")
data class Recording(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val phoneNumber: String,
    val contactName: String?,
    val fileName: String,
    val filePath: String,
    val duration: Long,
    val timestamp: Long,
    val callType: String,
    val fileSize: Long,
    val isStarred: Boolean = false,
    val notes: String? = null
)