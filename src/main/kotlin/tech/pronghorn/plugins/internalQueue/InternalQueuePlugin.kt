package tech.pronghorn.plugins.internalQueue

import tech.pronghorn.plugins.Plugin
import tech.pronghorn.plugins.PluginManager
import java.util.Queue

interface InternalQueuePlugin {
    companion object : Plugin<InternalQueuePlugin>(InternalQueueDefaultPlugin) {
        fun <T> get(capacity: Int): Queue<T> = PluginManager.internalQueuePlugin.get(capacity)
    }

    fun <T> get(capacity: Int): Queue<T>
}
