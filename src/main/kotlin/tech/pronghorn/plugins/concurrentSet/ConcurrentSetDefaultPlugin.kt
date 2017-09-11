package tech.pronghorn.plugins.concurrentSet

import java.util.*
import java.util.concurrent.ConcurrentHashMap


object ConcurrentSetDefaultPlugin : ConcurrentSetPlugin {
    override fun <T> get(): MutableSet<T> {
        return Collections.newSetFromMap(ConcurrentHashMap())
    }
}
