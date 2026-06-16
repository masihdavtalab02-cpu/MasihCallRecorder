package com.masih.callrecorder.util
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private val timeFormat = SimpleDateFormat("HH:mm", Locale("fa"))
    private val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale("fa"))

    fun formatFull(timestamp: Long): String {
        val cal = Calendar.getInstance().apply { timeInMillis = timestamp }
        val now = Calendar.getInstance()
        val timePart = timeFormat.format(cal.time)
        return when {
            isSameDay(cal, now) -> "امضȱض $timePart"
            isSameDay(cal, getYesterday()) -> "دعارض $timePart"
            else -> "${dateFormat.format(cal.time)}  $timePart"
        }
    }

    fun formatDuration(ms: Long): String {
        val totalSeconds = ms / 1000
        val h = totalSeconds / 3600; val m = (totalSeconds % 3600) / 60; val s = totalSeconds % 60
        return if (h > 0) String.format("%d:%02d:%02d", h, m, s) else String.format("%d:%02d", m, s)
    }

    private fun isSameDay(c1: Calendar, c2: Calendar) = c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
    private fun getYesterday(): Calendar = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
}