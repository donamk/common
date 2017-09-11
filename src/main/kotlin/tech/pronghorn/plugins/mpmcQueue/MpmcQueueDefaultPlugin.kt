package tech.pronghorn.plugins.mpmcQueue

import java.util.*
import java.util.concurrent.ArrayBlockingQueue

object MpmcQueueDefaultPlugin : MpmcQueuePlugin {
    override fun <T> get(capacity: Int): Queue<T> {
        return ArrayBlockingQueue(capacity)
    }
}
