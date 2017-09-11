package tech.pronghorn.plugins.internalQueue

import tech.pronghorn.collections.RingBufferQueue
import java.util.Queue

object InternalQueueDefaultPlugin : InternalQueuePlugin {
    override fun <T> get(capacity: Int): Queue<T> {
        return RingBufferQueue(capacity)
    }
}
