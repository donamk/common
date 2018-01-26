package tech.pronghorn.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tech.pronghorn.test.PronghornTest

class MathUtilTests: PronghornTest() {
    @Test
    fun roundMaxTest() {
        assertThrows(IllegalArgumentException::class.java, {
            roundToNextPowerOfTwo(Integer.MAX_VALUE - 1)
        })
    }

    @Test
    fun roundNegativeTest() {
        assertThrows(IllegalArgumentException::class.java, {
            roundToNextPowerOfTwo(-1)
        })
    }

    val powers = arrayOf(1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024)

    @Test
    fun roundToNextTest() {
        var x = 0
        while(x < 1000){
            val expectedNext = powers.find { power -> power >= x }
            assertEquals(expectedNext, roundToNextPowerOfTwo(x))
            x += 1
        }
    }

    @Test
    fun isPowerTest() {
        var x = 0
        while(x < 1000){
            if(powers.contains(x)){
                assertTrue(isPowerOfTwo(x))
            }
            else {
                assertFalse(isPowerOfTwo(x))
            }
            x += 1
        }
    }
}
