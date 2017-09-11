package tech.pronghorn.plugins.concurrentMap

import tech.pronghorn.plugins.Plugin
import tech.pronghorn.plugins.PluginManager

interface ConcurrentMapPlugin {
    companion object: Plugin<ConcurrentMapPlugin>(ConcurrentMapDefaultPlugin) {
        fun <K,V> get(initialCapacity: Int = 16,
                      loadFactor: Float = 0.75f): MutableMap<K,V> = PluginManager.concurrentMapPlugin.get(initialCapacity, loadFactor)
    }

    fun <K,V> get(initialCapacity: Int = 16,
                  loadFactor: Float = 0.75f): MutableMap<K,V>
}
