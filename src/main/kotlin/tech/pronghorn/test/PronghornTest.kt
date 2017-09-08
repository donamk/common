package tech.pronghorn.test

import mu.KotlinLogging
import java.util.*

const val repeatCount: Int = 1

abstract class PronghornTest(val forcedSeed: Long? = null) {
    protected val logger = KotlinLogging.logger(this.javaClass.name)

    val random by lazy {
        val seed = forcedSeed ?: Random().nextLong()
        logger.info { "Random seed: $seed" }
        Random(seed)
    }
}
