package tech.pronghorn.logging

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

internal class BootstrapLogger(override val name: String) : Logger() {
    override val isTraceEnabled: Boolean = false
    override val isDebugEnabled: Boolean = false
    override val isInfoEnabled: Boolean = false
    override val isWarnEnabled: Boolean = true
    override val isErrorEnabled: Boolean = true

    private val gmt = ZoneId.of("GMT")

    private fun getFormattedDate(): String = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(gmt))
    private fun getFormattedMessage(level: String,
                                    message: String): String = "${getFormattedDate()} $level $name - $message"

    override fun trace(message: String) = Unit
    override fun debug(message: String) = Unit
    override fun info(message: String) = Unit
    override fun warn(message: String) = System.err.println(getFormattedMessage("WARN", message))
    override fun error(message: String) = System.err.println(getFormattedMessage("ERROR", message))
}
