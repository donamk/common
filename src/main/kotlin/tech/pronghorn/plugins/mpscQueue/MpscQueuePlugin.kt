package tech.pronghorn.plugins.mpscQueue

import tech.pronghorn.plugins.Plugin
import tech.pronghorn.plugins.PluginManager
import java.util.Queue

interface MpscQueuePlugin {
    companion object : Plugin<MpscQueuePlugin>(MpscQueueDefaultPlugin) {
        fun <T> get(capacity: Int): Queue<T> = PluginManager.mpscQueuePlugin.get(capacity)
    }

    fun <T> get(capacity: Int): Queue<T>
}
