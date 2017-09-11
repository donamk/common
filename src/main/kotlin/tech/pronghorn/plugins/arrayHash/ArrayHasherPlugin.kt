package tech.pronghorn.plugins.arrayHash

import tech.pronghorn.plugins.Plugin
import tech.pronghorn.plugins.PluginManager

interface ArrayHasherPlugin {
    companion object : Plugin<ArrayHasherPlugin>(ArrayHasherDefaultPlugin) {
        fun get(): (ByteArray) -> Long = PluginManager.arrayHasherPlugin.get()
    }

    fun get(): (ByteArray) -> Long
}
