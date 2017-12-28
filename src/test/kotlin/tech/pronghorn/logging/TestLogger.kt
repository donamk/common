package tech.pronghorn.logging

import tech.pronghorn.plugins.logging.LoggingPlugin
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class TestLogger(override val name: String): Logger() {
    override val isTraceEnabled: Boolean = true
    override val isDebugEnabled: Boolean = true
    override val isInfoEnabled: Boolean = true
    override val isWarnEnabled: Boolean = true
    override val isErrorEnabled: Boolean = true

    private val gmt = ZoneId.of("GMT")

    private fun getFormattedDate(): String = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(gmt))
    private fun getFormattedMessage(level: String,
                                    message: String): String = "${getFormattedDate()} $level $name - $message"

    override fun traceImpl(message: String) = println(getFormattedMessage("TRACE ", message))
    override fun debugImpl(message: String) = println(getFormattedMessage("DEBUG ", message))
    override fun infoImpl(message: String) = println(getFormattedMessage("INFO ", message))
    override fun warnImpl(message: String) = println(getFormattedMessage("WARN ", message))
    override fun errorImpl(message: String) = println(getFormattedMessage("ERROR ", message))
}

class TestLoggingPlugin: LoggingPlugin {
    override fun get(name: String): Logger = TestLogger(name)
}
