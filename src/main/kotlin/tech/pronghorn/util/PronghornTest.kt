package tech.pronghorn.util

import mu.KotlinLogging
import java.util.*

abstract class PronghornTest {
    protected val logger = KotlinLogging.logger {}

    val random by lazy {
        val seed = Random().nextLong()
        logger.info("Random seed: $seed")
        Random(seed)
    }

    fun test(repeatCount: Int = 16,
             block: () -> Unit) = repeat(repeatCount) { block() }
}
