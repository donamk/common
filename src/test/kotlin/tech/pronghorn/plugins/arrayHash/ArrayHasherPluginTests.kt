package tech.pronghorn.plugins.arrayHash

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import tech.pronghorn.test.PronghornTest
import tech.pronghorn.test.repeatCount

class ArrayHasherPluginTests : PronghornTest() {
    /*
     * Tests the default array hashing plugin creates proper values.
     */
    @RepeatedTest(repeatCount)
    fun arrayHashPlugin() {
        val hasher = ArrayHasherPlugin.get()
        val fooA = "foo".toByteArray(Charsets.US_ASCII)
        val fooB = "foo".toByteArray(Charsets.US_ASCII)
        val bar = "bar".toByteArray(Charsets.US_ASCII)

        assertNotEquals(fooA, fooB)
        assertEquals(hasher(fooA), hasher(fooB))
        assertNotEquals(hasher(fooA), hasher(bar))
        assertNotEquals(hasher(fooB), hasher(bar))
    }
}
