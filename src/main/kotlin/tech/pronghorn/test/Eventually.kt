package tech.pronghorn.test

import java.time.Duration

private val defaultEventually: Duration = Duration.ofSeconds(1)
private val interval: Duration = Duration.ofMillis(10)

fun eventually(duration: Duration = defaultEventually,
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
    throw AssertionError("Test failed after ${duration.toMillis()} ms. Attempted $times times.\nLast exception: ${lastException?.message}")
}
