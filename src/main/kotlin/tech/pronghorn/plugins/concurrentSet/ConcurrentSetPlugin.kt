package tech.pronghorn.plugins.concurrentSet

import tech.pronghorn.plugins.Plugin
import tech.pronghorn.plugins.PluginManager

interface ConcurrentSetPlugin {
    companion object: Plugin<ConcurrentSetPlugin>(ConcurrentSetDefaultPlugin) {
        fun <T> get(): MutableSet<T> = PluginManager.concurrentSetPlugin.get()
    }

    fun <T> get(): MutableSet<T>
}
