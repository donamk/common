package tech.pronghorn.plugins.internalQueue

import tech.pronghorn.collections.RingBufferQueue
import java.util.*

object InternalQueueDefaultPlugin : InternalQueuePlugin {
    override fun <T> get(capacity: Int): Queue<T> {
        return RingBufferQueue(capacity)
    }
}
