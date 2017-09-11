package tech.pronghorn.plugins.mpscQueue

import java.util.Queue
import java.util.concurrent.ArrayBlockingQueue

object MpscQueueDefaultPlugin : MpscQueuePlugin {
    override fun <T> get(capacity: Int): Queue<T> {
        return ArrayBlockingQueue(capacity)
    }
}
