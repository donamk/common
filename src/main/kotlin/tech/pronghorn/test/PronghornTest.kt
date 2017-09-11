package tech.pronghorn.test

import tech.pronghorn.plugins.logging.LoggingPlugin
import java.util.*

const val repeatCount: Int = 1

abstract class PronghornTest(val forcedSeed: Long? = null) {
    protected val logger = LoggingPlugin.get(javaClass)

    val random by lazy {
        val seed = forcedSeed ?: Random().nextLong()
        logger.info { "Random seed: $seed" }
        Random(seed)
    }
}
