package tech.pronghorn.plugins.concurrentSet

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import tech.pronghorn.test.PronghornTest
import tech.pronghorn.test.repeatCount
import kotlin.concurrent.thread

class ConcurrentSetPluginTests : PronghornTest() {
    /*
     * Tests the default concurrent set plugin functions properly.
     */
    @RepeatedTest(repeatCount)
    fun concurrentMapPlugin() {
        val map = ConcurrentSetPlugin.get<String>()

        val threadA = thread(start = false) {
            var x = 0
            while (x < 10000) {
                map.add("$x")
                x += 2
            }
        }

        val threadB = thread(start = false) {
            var x = 1
            while (x < 10000) {
                map.add("$x")
                x += 2
            }
        }

        threadA.start()
        threadB.start()
        threadA.join()
        threadB.join()

        assertEquals(10000, map.size)
    }
}
