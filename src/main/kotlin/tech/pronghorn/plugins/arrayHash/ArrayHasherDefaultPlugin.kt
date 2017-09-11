package tech.pronghorn.plugins.arrayHash

import java.util.Arrays

object ArrayHasherDefaultPlugin : ArrayHasherPlugin {
    override fun get(): (ByteArray) -> Long = { arr: ByteArray -> Arrays.hashCode(arr).toLong() }
}
