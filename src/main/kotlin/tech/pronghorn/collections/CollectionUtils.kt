package tech.pronghorn.collections

import tech.pronghorn.plugins.logging.LoggingPlugin
import tech.pronghorn.util.isPowerOfTwo
import tech.pronghorn.util.roundToNextPowerOfTwo

internal inline fun <reified T : Any> validatePowerOfTwoCapacity(caller: T,
                                                                 value: Int): Int {
    if (isPowerOfTwo(value)) {
        return value
    }
    else {
        val logger = LoggingPlugin.get(caller.javaClass)
        logger.warn { "${caller.javaClass.simpleName} capacity should be a power of two, but ($value) requested. Using the next available: ${roundToNextPowerOfTwo(value)}." }
        return roundToNextPowerOfTwo(value)
    }
}
