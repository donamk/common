package tech.pronghorn.plugins.internalQueue

import java.util.Queue

abstract class BoundedInternalQueue<T>(protected val queue: Queue<T>): Queue<T> by queue {
    abstract fun remaining(): Int
}
