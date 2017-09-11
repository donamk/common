package tech.pronghorn.logging

abstract class Logger {
    abstract val name: String

    abstract val isTraceEnabled: Boolean
    abstract val isDebugEnabled: Boolean
    abstract val isInfoEnabled: Boolean
    abstract val isWarnEnabled: Boolean
    abstract val isErrorEnabled: Boolean

    abstract protected fun trace(message: String)
    abstract protected fun debug(message: String)
    abstract protected fun info(message: String)
    abstract protected fun warn(message: String)
    abstract protected fun error(message: String)

    fun trace(block: () -> String) {
        if (isTraceEnabled) trace(block())
    }

    fun debug(block: () -> String) {
        if (isDebugEnabled) debug(block())
    }

    fun info(block: () -> String) {
        if (isInfoEnabled) info(block())
    }

    fun warn(block: () -> String) {
        if (isWarnEnabled) warn(block())
    }

    fun error(block: () -> String) {
        if (isErrorEnabled) error(block())
    }
}
