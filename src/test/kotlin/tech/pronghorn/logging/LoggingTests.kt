package tech.pronghorn.logging

import org.junit.jupiter.api.Test
import tech.pronghorn.plugins.PluginManager
import tech.pronghorn.plugins.logging.*
import tech.pronghorn.test.PronghornTest

class LoggingTests: PronghornTest() {
    fun testPlugin(plugin: LoggingPlugin) {
        val prePlugin = PluginManager.loggingPlugin

        LoggingPlugin.setPlugin(plugin)
        val logger = LoggingPlugin.get(javaClass)

        logger.trace { "trace message" }
        logger.debug { "debug message" }
        logger.info { "info message" }
        logger.warn { "warn message" }
        logger.error { "error message" }

        logger.traceImpl("trace message")
        logger.debugImpl("debug message")
        logger.infoImpl("info message")
        logger.warnImpl("warn message")
        logger.errorImpl("error message")

        // reset the active plugin to what it was set to prior to running this test
        LoggingPlugin.setPlugin(prePlugin)
    }

    @Test
    fun bootstrapLoggerTest() {
        testPlugin(BootstrapLoggingPlugin)
    }

    @Test
    fun defaultLoggerTest() {
        testPlugin(LoggingDefaultPlugin)
    }
}
