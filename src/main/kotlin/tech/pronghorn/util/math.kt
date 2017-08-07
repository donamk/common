package tech.pronghorn.util

const val MAX_POW2 = 1 shl 30

fun roundToPowerOfTwo(value: Int): Int {
    if (value > MAX_POW2) {
        throw IllegalArgumentException("There is no larger power of 2 int for value:$value since it exceeds 2^31.")
    }
    if (value < 0) {
        throw IllegalArgumentException("Given value:$value. Expecting value >= 0.")
    }
    return 1 shl 32 - Integer.numberOfLeadingZeros(value - 1)
}
