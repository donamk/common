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

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.LinkedBlockingQueue

public object MpscQueueDefaultPlugin : MpscQueuePlugin {
    private class DrainableArrayBlockingQueue<T>(capacity: Int,
                                                 private val arrayQueue: ArrayBlockingQueue<T> = ArrayBlockingQueue(capacity)) : DrainableQueue<T>(arrayQueue) {
        override fun drainTo(collection: MutableCollection<T>,
                             maxElements: Int): Int {
            return arrayQueue.drainTo(collection, maxElements)
        }

        override fun fillFrom(collection: Collection<T>): Int {
            var filled = 0
            collection.forEach { value ->
                if(!arrayQueue.offer(value)){
                    return filled
                }
                filled += 1
            }
            return filled
        }
    }

    private class DrainableLinkedBlockingQueue<T>(private val linkedQueue: LinkedBlockingQueue<T> = LinkedBlockingQueue()) : DrainableQueue<T>(linkedQueue) {
        override fun drainTo(collection: MutableCollection<T>,
                             maxElements: Int): Int {
            return linkedQueue.drainTo(collection, maxElements)
        }

        override fun fillFrom(collection: Collection<T>): Int {
            linkedQueue.addAll(collection)
            return collection.size
        }
    }

    override fun <T> getBounded(capacity: Int): DrainableQueue<T> {
        return DrainableArrayBlockingQueue(capacity)
    }

    override fun <T> getUnbounded(): DrainableQueue<T> {
        return DrainableLinkedBlockingQueue()
    }
}
