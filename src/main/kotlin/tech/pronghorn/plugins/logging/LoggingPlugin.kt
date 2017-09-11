package tech.pronghorn.plugins.logging

import tech.pronghorn.logging.Logger
import tech.pronghorn.plugins.Plugin
import tech.pronghorn.plugins.PluginManager

interface LoggingPlugin {
    companion object : Plugin<LoggingPlugin>(LoggingDefaultPlugin) {
        fun get(clazz: Class<*>): Logger = PluginManager.loggingPlugin.get(clazz.simpleName)
    }

    fun get(name: String): Logger
}
