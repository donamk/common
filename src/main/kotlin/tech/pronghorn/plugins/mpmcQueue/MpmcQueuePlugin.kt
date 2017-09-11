package tech.pronghorn.plugins.mpmcQueue

import tech.pronghorn.plugins.Plugin
import tech.pronghorn.plugins.PluginManager
import java.util.*

interface MpmcQueuePlugin {
    companion object: Plugin<MpmcQueuePlugin>(MpmcQueueDefaultPlugin) {
        fun <T> get(capacity: Int): Queue<T> = PluginManager.mpmcQueuePlugin.get(capacity)
    }

    fun <T> get(capacity: Int): Queue<T>
}
