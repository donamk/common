/*
 * Copyright 2017 Pronghorn Technology LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.pronghorn.plugins.mpscQueue

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import tech.pronghorn.plugins.Consumer
import tech.pronghorn.plugins.Producer
import tech.pronghorn.test.*
import tech.pronghorn.util.roundToNextPowerOfTwo
import java.util.Queue

class MpscQueuePluginTests : PronghornTest() {
    // Tests the default mpsc queue plugin returns a bounded queue for getBounded
    @RepeatedTest(lightRepeatCount)
    fun mpscQueuePluginBounded() {
        val capacity = roundToNextPowerOfTwo(4 + random.nextInt(64))
        val queue = MpscQueuePlugin.getBounded<String>(capacity)

        var x = 0
        while (x < capacity) {
            queue.add("$x")
            x += 1
        }

        assertEquals(capacity, queue.size)
        assertFalse(queue.offer("foo"))
    }

    // Tests the mpsc queue plugin returns an unbounded queue for getUnbounded
    @RepeatedTest(heavyRepeatCount)
    fun mpscQueuePluginUnbounded() {
        val queue = MpscQueuePlugin.getUnbounded<String>()
        val toAdd = 1024 * 1024

        var x = 0
        while (x < toAdd) {
            queue.add("$x")
            x += 1
        }

        assertEquals(toAdd, queue.size)
    }

    fun checkQueueThreadSafety(queue: Queue<Int>,
                               count: Int) {
        val toConsume = count
        val toProduce = count / 2

        val consumer = Consumer(toConsume, queue)

        val producerA = Producer(toProduce, queue)
        val producerB = Producer(toProduce, queue)

        val threads = listOf(consumer, producerA, producerB)
        threads.forEach(Thread::start)
        threads.forEach { thread -> thread.join(1000) }

        assertEquals(toConsume, consumer.consumedCount)
        assertEquals(toProduce, producerA.producedCount)
        assertEquals(toProduce, producerB.producedCount)
    }

    // Tests the default bounded mpsc queue plugin is appropriately thread-safe
    @RepeatedTest(heavyRepeatCount)
    fun mpscQueuePluginBoundedThreadSafeTest() {
        val capacity = 64
        val queue = MpscQueuePlugin.getBounded<Int>(capacity)

        checkQueueThreadSafety(queue, capacity)
    }

    // Tests the default unbounded mpsc queue plugin is appropriately thread-safe
    @RepeatedTest(heavyRepeatCount)
    fun mpscQueuePluginUnboundedThreadSafeTest() {
        val queue = MpscQueuePlugin.getUnbounded<Int>()

        checkQueueThreadSafety(queue, 1024 * 16)
    }
}
