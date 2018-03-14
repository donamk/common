package tech.pronghorn.plugins.mpscQueue

import java.util.Queue

abstract class DrainableQueue<T>(protected val queue: Queue<T>): Queue<T> by queue {
    abstract fun drainTo(collection: MutableCollection<T>,
                         maxElements: Int): Int

    abstract fun fillFrom(collection: Collection<T>): Int
}
