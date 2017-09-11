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
