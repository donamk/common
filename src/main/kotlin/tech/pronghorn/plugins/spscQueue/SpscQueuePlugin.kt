package tech.pronghorn.plugins.spscQueue

import tech.pronghorn.plugins.Plugin
import tech.pronghorn.plugins.PluginManager
import java.util.*

interface SpscQueuePlugin {
    companion object : Plugin<SpscQueuePlugin>(SpscQueueDefaultPlugin) {
        fun <T> get(capacity: Int): Queue<T> = PluginManager.spscQueuePlugin.get(capacity)
    }

    fun <T> get(capacity: Int): Queue<T>
}
