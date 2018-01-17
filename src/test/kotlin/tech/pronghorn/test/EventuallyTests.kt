package tech.pronghorn.test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Duration
import java.util.concurrent.atomic.AtomicBoolean

class EventuallyTests : PronghornTest() {
    /*
     * Ensures tests fail with the appropriate AssertionError
     */
    @Test
    fun failingAssertionEventuallyTest() {
        assertThrows(AssertionError::class.java, {
            eventually(Duration.ofMillis(100)) {
                assertTrue(false)
            }
        })
    }

    /*
     * Ensures tests fail with the appropriate AssertionError
     */
    @Test
    fun failingExceptionEventuallyTest() {
        assertThrows(AssertionError::class.java, {
            eventually(Duration.ofMillis(100)) {
                throw Exception("This will get converted to an assertion error.")
            }
        })
    }

    /*
     * Ensures tests fail at the appropriate time limit
     */
    @Test
    fun timingFailsEventuallyTest() {
        assertThrows(AssertionError::class.java, {
            val failTime = Duration.ofMillis(100)
            val check = AtomicBoolean(false)
            val thread = Thread {
                Thread.sleep(failTime.toMillis() + 1)
                check.set(true)
            }
            thread.start()

            eventually(Duration.ofMillis(100)) {
                assertTrue(check.get())
            }
        })
    }

    /*
     * Ensures tests succeed at the appropriate time limit
     */
    @Test
    fun timingSucceedsEventuallyTest() {
        val time = Duration.ofMillis(100)
        val check = AtomicBoolean(false)
        val thread = Thread {
            Thread.sleep(time.toMillis() - 10)
            check.set(true)
        }
        thread.start()

        eventually(Duration.ofMillis(100), Duration.ofMillis(1)) {
            assertTrue(check.get())
        }
    }
}
