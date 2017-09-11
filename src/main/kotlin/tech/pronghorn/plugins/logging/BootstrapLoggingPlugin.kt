package tech.pronghorn.plugins.logging

import tech.pronghorn.logging.BootstrapLogger
import tech.pronghorn.logging.Logger

internal object BootstrapLoggingPlugin: LoggingPlugin {
    override fun get(name: String): Logger = BootstrapLogger(name)
}

