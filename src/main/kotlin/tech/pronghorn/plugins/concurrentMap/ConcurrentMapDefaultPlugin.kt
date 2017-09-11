package tech.pronghorn.plugins.concurrentMap

import java.util.concurrent.ConcurrentHashMap


object ConcurrentMapDefaultPlugin : ConcurrentMapPlugin {
    override fun <K, V> get(initialCapacity: Int,
                            loadFactor: Float): MutableMap<K, V> {
        return ConcurrentHashMap(initialCapacity, loadFactor)
    }
}
