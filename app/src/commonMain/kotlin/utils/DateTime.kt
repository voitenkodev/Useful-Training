package utils

import kotlinx.datetime.*
import kotlin.time.Duration

class DateTimeKtx {

    fun currentTime() = Clock.System.now().minus(1, DateTimeUnit.HOUR).toString()

    fun durationFrom(iso8601Timestamp: String): String {
        val now: Instant = Clock.System.now()
        val result: Duration = (now - Instant.parse(iso8601Timestamp))
        return result.toString()
    }

    fun formattedDate(iso8601Timestamp: String): String? {
        val localDateTime = iso8601TimestampToLocalDateTime(iso8601Timestamp) ?: return null
        val date = localDateTime.date
        val day = date.dayOfMonth
        val month = date.monthNumber
        val year = date.year
        return "${day.zeroPrefixed(2)}.${month.zeroPrefixed(2)}.${year}"
    }

    fun formattedTime(iso8601Timestamp: String): String? {
        val localDateTime = iso8601TimestampToLocalDateTime(iso8601Timestamp) ?: return null
        val hour = localDateTime.hour
        val min = localDateTime.minute
        return timeFormat(hour, min)
    }

    fun formattedWeekDay(iso8601Timestamp: String): String? {
        val localDateTime = iso8601TimestampToLocalDateTime(iso8601Timestamp) ?: return null
        return localDateTime.dayOfWeek.name
    }

    fun getFormattedDuration(duration: String): String? {
        return Duration.parseOrNull(duration)?.toComponents { hours, minutes, _, _ ->
            timeFormat(hours.toInt(), minutes)
        }
    }

    //___________________________ INTERNAL API ___________________________

    private fun iso8601TimestampToLocalDateTime(timestamp: String): LocalDateTime? {
        return kotlin.runCatching {
            Instant.parse(timestamp).toLocalDateTime(TimeZone.currentSystemDefault())
        }.getOrNull()
    }

    private fun timeFormat(
        hour: Int,
        min: Int
    ): String {
        return if (hour > 0 && min > 0) {
            "${hour.zeroPrefixed(2)}h ${min.zeroPrefixed(2)}m"
        } else if (hour > 0 && min == 0) {
            "${hour}h"
        } else if (hour == 0 && min > 0) {
            "${min}m"
        } else {
            ""
        }
    }

    private fun Int.zeroPrefixed(maxLength: Int): String {
        if (this < 0 || maxLength < 1) return ""
        val string = this.toString()
        val currentStringLength = string.length
        return if (maxLength <= currentStringLength) {
            string
        } else {
            val diff = maxLength - currentStringLength
            var prefixedZeros = ""
            repeat(diff) {
                prefixedZeros += "0"
            }
            "$prefixedZeros$string"
        }
    }
}