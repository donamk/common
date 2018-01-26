package tech.pronghorn.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tech.pronghorn.test.PronghornTest

class CommonUtilTests : PronghornTest() {
    @Test
    fun stackTraceTest() {
        val randomNumber = random.nextInt().toString()
        val exception = Exception(randomNumber)
        val asString = exception.stackTraceToString()
        assertTrue(asString.contains(randomNumber))
    }

    fun toIgnore() {
        throw Exception("Should be ignored.")
    }

    @Test
    fun ignoreExceptionTest() {
        ignoreException {
            throw Exception("Should be ignored.")
        }
        ignoreException {
            assertTrue(true)
        }
    }

    @Test
    fun ignoreExceptionsTest() {
        ignoreExceptions(
                { toIgnore() },
                { toIgnore() }
        )
    }
}
