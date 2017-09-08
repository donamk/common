package tech.pronghorn.collections

import tech.pronghorn.util.isPowerOfTwo
import tech.pronghorn.util.roundToNextPowerOfTwo
import java.util.*

class RingBufferQueue<T>(requestedCapacity: Int) : Queue<T> {
    private val logger = mu.KotlinLogging.logger {}
    val capacity: Int = validateCapacity(requestedCapacity)
    @Suppress("UNCHECKED_CAST")
    private val ring: Array<T?> = Array<Any?>(capacity, { null }) as Array<T?>
    private val mask = capacity - 1
    private var read = 0
    private var write = 0
    override val size: Int
        get() = write - read

    private fun validateCapacity(value: Int): Int {
        if (isPowerOfTwo(value)) {
            return value
        }
        else {
            logger.warn { "RingBuffer capacity should be a power of two, but ($value) requested. Using the next available: ${roundToNextPowerOfTwo(value)}." }
            return roundToNextPowerOfTwo(value)
        }
    }

    override fun remove(): T {
        if (read == write) {
            throw NoSuchElementException()
        }
        else {
            read += 1
            return ring[(read - 1).and(mask)]!!
        }
    }

    override fun contains(element: T?): Boolean {
        val length = write - read
        var x = 0
        while (x < length) {
            if (ring[(read + x).and(mask)] == element) {
                return true
            }
            x += 1
        }
        return false
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        elements.forEach { element ->
            if (!contains(element)) {
                return false
            }
        }

        return true
    }

    override fun isEmpty(): Boolean = write == read

    fun isFull(): Boolean = write - read == capacity

    override fun addAll(elements: Collection<T>): Boolean {
        if (write - read < elements.size) {
            return false
        }

        elements.forEachIndexed { index, element ->
            ring[(write + index).and(mask)] = element
        }

        write += elements.size
        return true
    }

    override fun clear() {
        while (read < write) {
            ring[read.and(mask)] = null
            read += 1
        }
        write = 0
        read = 0
    }

    override fun offer(element: T?): Boolean {
        if (isFull()) {
            return false
        }

        ring[write.and(mask)] = element
        write += 1
        return true
    }

    override fun element(): T? {
        if (isEmpty()) {
            throw NoSuchElementException()
        }

        return ring[read.and(mask)]
    }

    override fun add(element: T?): Boolean {
        if (isFull()) {
            throw IllegalStateException()
        }

        ring[write.and(mask)] = element
        write += 1
        return true
    }

    override fun peek(): T? {
        if (isEmpty()) {
            return null
        }
        else {
            return ring[read.and(mask)]
        }
    }

    override fun poll(): T? {
        if (isEmpty()) {
            return null
        }
        else {
            read += 1
            return ring[(read - 1).and(mask)]
        }
    }

    override fun iterator(): MutableIterator<T> {
        throw UnsupportedOperationException()
    }

    override fun remove(element: T?): Boolean {
        throw UnsupportedOperationException()
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        throw UnsupportedOperationException()
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        throw UnsupportedOperationException()
    }
}
