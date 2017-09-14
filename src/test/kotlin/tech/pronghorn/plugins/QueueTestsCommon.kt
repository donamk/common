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

package tech.pronghorn.plugins

import java.util.Queue

class Producer(val count: Int,
               val queue: Queue<Int>) : Thread() {
    var producedSum = 0
    var producedCount = 0

    override fun run() {
        while (producedCount < count) {
            if (queue.offer(producedCount)) {
                producedSum += producedCount
                producedCount += 1
            }
        }
    }
}

class Consumer(val count: Int,
               val queue: Queue<Int>) : Thread() {
    var consumedSum = 0
    var consumedCount = 0

    override fun run() {
        while (consumedCount < count) {
            val value = queue.poll()
            if (value != null) {
                consumedSum += value
                consumedCount += 1
            }
        }
    }
}
