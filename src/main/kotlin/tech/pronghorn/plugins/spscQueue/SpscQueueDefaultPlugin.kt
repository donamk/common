package tech.pronghorn.plugins.spscQueue

import java.util.Queue
import java.util.concurrent.ArrayBlockingQueue

object SpscQueueDefaultPlugin : SpscQueuePlugin {
    override fun <T> get(capacity: Int): Queue<T> {
        return ArrayBlockingQueue(capacity)
    }
}
