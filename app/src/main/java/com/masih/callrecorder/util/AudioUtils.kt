package com.masih.callrecorder.util
import android.content.Context
import android.media.MediaRecorder
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object AudioUtils {
    private val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
    fun getRecordingsDirectory(context: Context): File {
        val dir = File(context.getExternalFilesDir(null), "Recordings")
        if (!dir.exists()) dir.mkdirs()
        return dir
    }
    fun generateFileName(phoneNumber: String): String {
        val cleaned = phoneNumber.replace(Regex("[^0-9]"), "")
        val dateStr = dateFormat.format(Date())
        return if (cleaned.isNotBlank()) "Rec_\${cleaned}_\${dateStr}" else "Rec_Unknown_\${dateStr}"
    }
    fun getAudioSource(idx: Int) = when(idx) { 0 -> MediaRecorder.AudioSource.VOICE_COMMUNICATION; 1 -> MediaRecorder.AudioSource.VOICE_RECOGNITION; 2 -> MediaRecorder.AudioSource.MIC; else -> MediaRecorder.AudioSource.DEFAULT }
    fun getAudioFormat(fmt: String) = when(fmt.lowercase()) { "aac","mp3" -> MediaRecorder.OutputFormat.MPEG_4; "amr" -> MediaRecorder.OutputFormat.AMR_NB; else -> MediaRecorder.OutputFormat.MPEG_4 }
    fun getAudioEncoder(fmt: String) = when(fmt.lowercase()) { "aac","mp3" -> MediaRecorder.AudioEncoder.AAC; "amr" -> MediaRecorder.AudioEncoder.AMR_NB; else -> MediaRecorder.AudioEncoder.AAC }
    fun getFileExtension(fmt: String) = when(fmt.lowercase()) { "aac" -> "m4a"; "mp3" -> "mp3"; "amr" -> "amr"; "wav" -> "wav"; else -> "m4a" }
    fun formatDuration(durationMs: Long): String { val ts = durationMs / 1000; val h = ts / 3600; val m = (ts % 3600) / 60; val s = ts % 60; return if (h > 0) String.format("%d:%02d:%02d", h, m, s) else String.format("%d:%02d", m, s) }
    fun formatFileSize(bytes: Long): String = when { bytes < 1024 -> "\${bytes} B"; bytes < 1048576 -> "\${bytes/1024} KB"; else -> String.format("%.1f MB", bytes / 1048576.0) }
    fun deleteRecording(path: String): Boolean = try { File(path).delete() } catch (e: Exception) { false }
}
