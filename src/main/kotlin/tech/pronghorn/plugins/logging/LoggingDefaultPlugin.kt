package tech.pronghorn.plugins.logging

import tech.pronghorn.logging.Logger
import tech.pronghorn.logging.NoopLogger

object LoggingDefaultPlugin: LoggingPlugin {
    override fun get(name: String): Logger = NoopLogger(name)
}
