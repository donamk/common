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

package tech.pronghorn.test

import java.time.Duration

private val defaultEventually: Duration = Duration.ofSeconds(1)
private val defaultInterval: Duration = Duration.ofMillis(10)

public fun eventually(duration: Duration = defaultEventually,
                      interval: Duration = defaultInterval,
                      block: () -> Unit) {
    val end = System.currentTimeMillis() + duration.toMillis()

    var times = 0
    var lastException: Throwable? = null
    while (System.currentTimeMillis() < end) {
        try {
            block()
            return
        }
        catch (ex: Exception) {
            lastException = ex
            // ignore and proceed
        }
        catch (ex: AssertionError) {
            lastException = ex
            // ignore and proceed
        }
        Thread.sleep(interval.toMillis())

        times++
    }

    throw AssertionError("Test failed after ${duration.toMillis()} ms. Attempted $times times.\nLast exception: $lastException")
}
