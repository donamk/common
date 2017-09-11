package tech.pronghorn.logging

class NoopLogger(override val name: String) : Logger() {
    override val isTraceEnabled: Boolean = false
    override val isDebugEnabled: Boolean = false
    override val isInfoEnabled: Boolean = false
    override val isWarnEnabled: Boolean = false
    override val isErrorEnabled: Boolean = false

    override fun trace(message: String) = Unit
    override fun debug(message: String) = Unit
    override fun info(message: String) = Unit
    override fun warn(message: String) = Unit
    override fun error(message: String) = Unit
}
