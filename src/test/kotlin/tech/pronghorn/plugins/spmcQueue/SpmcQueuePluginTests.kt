package tech.pronghorn.plugins.spmcQueue

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.RepeatedTest
import tech.pronghorn.plugins.Consumer
import tech.pronghorn.plugins.Producer
import tech.pronghorn.test.*
import tech.pronghorn.util.roundToNextPowerOfTwo
import java.util.Queue

class SpmcQueuePluginTests : PronghornTest() {
    // Tests the default spmc queue plugin returns a bounded queue for getBounded
    @RepeatedTest(lightRepeatCount)
    fun spmcQueuePluginBounded() {
        val capacity = roundToNextPowerOfTwo(4 + random.nextInt(64))
        val queue = SpmcQueuePlugin.getBounded<String>(capacity)

        var x = 0
        while (x < capacity) {
            queue.add("$x")
            x += 1
        }

        Assertions.assertEquals(capacity, queue.size)
        Assertions.assertFalse(queue.offer("foo"))
    }

    // Tests the spmc queue plugin returns an unbounded queue for getUnbounded
    @RepeatedTest(heavyRepeatCount)
    fun spmcQueuePluginUnbounded() {
        val queue = SpmcQueuePlugin.getUnbounded<String>()
        val toAdd = 1024 * 1024

        var x = 0
        while (x < toAdd) {
            queue.add("$x")
            x += 1
        }

        Assertions.assertEquals(toAdd, queue.size)
    }

    fun checkQueueThreadSafety(queue: Queue<Int>,
                               count: Int) {
        val toConsume = count / 2
        val toProduce = count

        val consumerA = Consumer(toConsume, queue)
        val consumerB = Consumer(toConsume, queue)

        val producer = Producer(toProduce, queue)

        val threads = listOf(producer, consumerA, consumerB)
        threads.forEach(Thread::start)
        threads.forEach { thread -> thread.join(1000) }

        Assertions.assertEquals(toConsume, consumerA.consumedCount)
        Assertions.assertEquals(toConsume, consumerB.consumedCount)
        Assertions.assertEquals(toProduce, producer.producedCount)
    }

    // Tests the default bounded spmc queue plugin is appropriately thread-safe
    @RepeatedTest(heavyRepeatCount)
    fun spmcQueuePluginBoundedThreadSafeTest() {
        val capacity = 64
        val queue = SpmcQueuePlugin.getBounded<Int>(capacity)

        checkQueueThreadSafety(queue, capacity)
    }

    // Tests the default unbounded spmc queue plugin is appropriately thread-safe
    @RepeatedTest(heavyRepeatCount)
    fun spmcQueuePluginUnboundedThreadSafeTest() {
        val queue = SpmcQueuePlugin.getUnbounded<Int>()

        checkQueueThreadSafety(queue, 1024 * 16)
    }
}
