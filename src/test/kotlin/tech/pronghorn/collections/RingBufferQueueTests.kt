package tech.pronghorn.collections

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import tech.pronghorn.test.PronghornTest
import tech.pronghorn.test.lightRepeatCount
import tech.pronghorn.util.isPowerOfTwo
import tech.pronghorn.util.roundToNextPowerOfTwo
import java.nio.ByteBuffer
import java.nio.channels.*

class RingBufferQueueTests: PronghornTest() {
    private val smallQueueSize = 16

    @RepeatedTest(lightRepeatCount)
    fun powerOfTwoRequirementTest() {
        val requestedCapacity = 1 + Math.abs(random.nextInt()) % 256
        val queue = RingBufferQueue<Int>(requestedCapacity)
        if(isPowerOfTwo(requestedCapacity)) {
            assertEquals(requestedCapacity, queue.capacity)
        }
        else {
            assertEquals(roundToNextPowerOfTwo(requestedCapacity), queue.capacity)
        }
    }

    @RepeatedTest(lightRepeatCount)
    fun addTest() {
        val queue = RingBufferQueue<Int>(smallQueueSize)
        val randomNumber = random.nextInt()
        queue.add(randomNumber)
        assertEquals(queue.element(), randomNumber)
    }

    @RepeatedTest(lightRepeatCount)
    fun addFullTest() {
        val queue = RingBufferQueue<Int>(smallQueueSize)

        var x = 0
        while(x < smallQueueSize){
            queue.add(random.nextInt())
            x += 1
        }

        assertThrows(IllegalStateException::class.java, { queue.add(random.nextInt()) })
    }

    @RepeatedTest(lightRepeatCount)
    fun removeTest() {
        val queue = RingBufferQueue<Int>(smallQueueSize)
        assertThrows(NoSuchElementException::class.java, { queue.remove() })

        val randomNumber = random.nextInt()
        queue.add(randomNumber)
        assertEquals(randomNumber, queue.remove())
    }

    @RepeatedTest(lightRepeatCount)
    fun containsTest() {
        val queue = RingBufferQueue<Int>(smallQueueSize)

        val randomNumber = random.nextInt()
        assertFalse(queue.contains(randomNumber))

        queue.add(randomNumber)
        assertTrue(queue.contains(randomNumber))

        queue.remove()
        assertFalse(queue.contains(randomNumber))
    }

    @RepeatedTest(lightRepeatCount)
    fun containsAllTest() {
        val queue = RingBufferQueue<Int>(smallQueueSize)

        val randomNumbers = listOf(
                random.nextInt(),
                random.nextInt(),
                random.nextInt()
        )

        assertFalse(queue.containsAll(randomNumbers))
        queue.add(randomNumbers[0])
        assertFalse(queue.containsAll(randomNumbers))
        queue.add(randomNumbers[1])
        assertFalse(queue.containsAll(randomNumbers))
        queue.add(randomNumbers[2])
        assertTrue(queue.containsAll(randomNumbers))
    }

    @RepeatedTest(lightRepeatCount)
    fun isEmptyTest() {
        val queue = RingBufferQueue<Int>(smallQueueSize)
        assertTrue(queue.isEmpty())
        queue.add(random.nextInt())
        assertFalse(queue.isEmpty())
        queue.remove()
        assertTrue(queue.isEmpty())
    }

    @RepeatedTest(lightRepeatCount)
    fun addAllTest() {
        val queue = RingBufferQueue<Int>(smallQueueSize)
        val randomNumbers = listOf(
                random.nextInt(),
                random.nextInt(),
                random.nextInt()
        )

        queue.addAll(randomNumbers)
        assertTrue(queue.containsAll(randomNumbers))
    }

    @RepeatedTest(lightRepeatCount)
    fun addAllFullTest() {
        val queue = RingBufferQueue<Int>(smallQueueSize)
        val randomNumbers = random.ints((smallQueueSize / 2) + 1L).toArray().toList()

        assertTrue(queue.addAll(randomNumbers))
        assertEquals(randomNumbers.size, queue.size)
        assertFalse(queue.addAll(randomNumbers))
    }

    @RepeatedTest(lightRepeatCount)
    fun clearTest() {
        val queue = RingBufferQueue<Int>(smallQueueSize)
        val randomNumbers = listOf(
                random.nextInt(),
                random.nextInt(),
                random.nextInt()
        )
        queue.addAll(randomNumbers)

        assertEquals(randomNumbers.size, queue.size)
        queue.clear()
        assertEquals(0, queue.size)
        assertTrue(queue.isEmpty())
    }

    @RepeatedTest(lightRepeatCount)
    fun offerTest() {
        val queue = RingBufferQueue<Int>(smallQueueSize)
        var x = 0
        while(x < smallQueueSize){
            assertTrue(queue.offer(random.nextInt()))
            x += 1
        }
        assertFalse(queue.offer(random.nextInt()))
    }

    @RepeatedTest(lightRepeatCount)
    fun elementTest() {
        val queue = RingBufferQueue<Int>(smallQueueSize)
        assertThrows(NoSuchElementException::class.java, { queue.element() })
        val firstRandom = random.nextInt()
        queue.add(firstRandom)
        assertEquals(firstRandom, queue.element())

        queue.add(random.nextInt())
        assertEquals(firstRandom, queue.element())
    }

    @RepeatedTest(lightRepeatCount)
    fun peekTest() {
        val queue = RingBufferQueue<Int>(smallQueueSize)

        assertNull(queue.peek())
        val firstRandom = random.nextInt()
        queue.add(firstRandom)

        assertEquals(firstRandom, queue.peek())
        queue.add(random.nextInt())
        assertEquals(firstRandom, queue.peek())
    }

    @RepeatedTest(lightRepeatCount)
    fun pollTest() {
        val queue = RingBufferQueue<Int>(smallQueueSize)

        assertNull(queue.poll())
        val firstRandom = random.nextInt()
        queue.add(firstRandom)
        val secondRandom = random.nextInt()
        queue.add(secondRandom)

        assertEquals(firstRandom, queue.poll())
        assertEquals(secondRandom, queue.poll())
        assertNull(queue.poll())
    }

    @RepeatedTest(lightRepeatCount)
    fun unimplementedTest() {
        val queue = RingBufferQueue<Int>(smallQueueSize)

        assertThrows(UnsupportedOperationException::class.java, { queue.iterator() })
        assertThrows(UnsupportedOperationException::class.java, { queue.remove(1) })
        assertThrows(UnsupportedOperationException::class.java, { queue.removeAll(listOf(1)) })
        assertThrows(UnsupportedOperationException::class.java, { queue.retainAll(listOf(1)) })
    }
}
