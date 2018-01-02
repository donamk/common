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

package tech.pronghorn.plugins.spscQueue

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import tech.pronghorn.plugins.internalQueue.InternalQueuePlugin
import tech.pronghorn.test.*
import tech.pronghorn.util.roundToNextPowerOfTwo

class InternalQueuePluginTests : PronghornTest() {
    // Tests the internal queue plugin returns a bounded queue for getBounded
    @RepeatedTest(lightRepeatCount)
    fun internalQueuePluginBoundedTest() {
        val capacity = roundToNextPowerOfTwo(4 + random.nextInt(64))
        val queue = InternalQueuePlugin.getBounded<String>(capacity)

        var x = 0
        while (x < capacity) {
            queue.add("$x")
            x += 1
        }

        assertEquals(capacity, queue.size)
        assertFalse(queue.offer("foo"))
    }

    // Tests the internal queue plugin returns an unbounded queue for getUnbounded
    @RepeatedTest(heavyRepeatCount)
    fun internalQueuePluginUnboundedTest() {
        val queue = InternalQueuePlugin.getUnbounded<String>()
        val toAdd = 1024 * 1024

        var x = 0
        while (x < toAdd) {
            queue.add("$x")
            x += 1
        }

        assertEquals(toAdd, queue.size)
    }
}
