package tech.pronghorn.plugins.mpmcQueue

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import tech.pronghorn.plugins.Consumer
import tech.pronghorn.plugins.Producer
import tech.pronghorn.test.PronghornTest
import tech.pronghorn.test.repeatCount
import tech.pronghorn.util.roundToNextPowerOfTwo

class MpmcQueuePluginTests : PronghornTest() {
    /*
     * Tests the default mpmc queue plugin returns a bounded queue
     */
    @RepeatedTest(repeatCount)
    fun mpmcQueuePluginBounded() {
        val capacity = roundToNextPowerOfTwo(4 + random.nextInt(64))
        val queue = MpmcQueuePlugin.get<String>(capacity)

        var x = 0
        while (x < capacity) {
            queue.add("$x")
            x += 1
        }

        assertEquals(capacity, queue.size)
        assertFalse(queue.offer("foo"))
    }

    /*
     * Tests the default mpmc queue plugin is appropriately thread-safe
     */
    @RepeatedTest(repeatCount)
    fun mpmcQueuePluginThreadSafe() {
        val capacity = 64
        val queue = MpmcQueuePlugin.get<Int>(capacity)

        val consumerA = Consumer(capacity, queue)
        val consumerB = Consumer(capacity, queue)

        val producerA = Producer(capacity, queue)
        val producerB = Producer(capacity, queue)

        val threads = listOf(consumerA, consumerB, producerA, producerB)
        threads.forEach(Thread::start)
        threads.forEach { thread -> thread.join(1000) }

        assertEquals(capacity, consumerA.consumedCount)
        assertEquals(capacity, consumerB.consumedCount)
        assertEquals(capacity, producerA.producedCount)
        assertEquals(capacity, producerB.producedCount)
        assertEquals(consumerA.consumedSum + consumerB.consumedSum, producerA.producedSum + producerB.producedSum)
    }
}
