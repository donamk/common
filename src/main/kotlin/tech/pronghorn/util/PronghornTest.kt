package tech.pronghorn.util

import mu.KotlinLogging
import java.util.*

abstract class PronghornTest(val forcedSeed: Long? = null) {
    protected val logger = KotlinLogging.logger(this.javaClass.name)

    val random by lazy {
        val seed = forcedSeed ?: Random().nextLong()
        logger.info("Random seed: $seed")
        Random(seed)
    }

    fun test(repeatCount: Int = 16,
             block: () -> Unit) = repeat(repeatCount) { block() }
}
