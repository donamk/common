/*
 * Copyright 2017 Pronghorn Technology LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.pronghorn.plugins

import tech.pronghorn.PRONGHORN_PLUGIN_PREFIX
import tech.pronghorn.PRONGHORN_PROPERTIES
import tech.pronghorn.plugins.arrayHash.ArrayHasherPlugin
import tech.pronghorn.plugins.concurrentMap.ConcurrentMapPlugin
import tech.pronghorn.plugins.concurrentSet.ConcurrentSetPlugin
import tech.pronghorn.plugins.internalQueue.InternalQueuePlugin
import tech.pronghorn.plugins.logging.*
import tech.pronghorn.plugins.mpscQueue.MpscQueuePlugin
import tech.pronghorn.plugins.spscQueue.SpscQueuePlugin
import tech.pronghorn.util.ignoreException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.Properties

internal object PluginManager {
    private val properties = loadProperties()

    @Volatile
    var loggingPlugin: LoggingPlugin = BootstrapLoggingPlugin
        private set

    init {
        val postBootstrapLoggingPlugin = loadPlugin(LoggingPlugin)
        if(postBootstrapLoggingPlugin is LoggingDefaultPlugin){
            val logger = LoggingPlugin.get(javaClass)
            logger.warn { "No Pronghorn logging plug-in detected, falling back to default no-op logger. Include or implement a logging plugin to enable logging." }
        }
        loggingPlugin = postBootstrapLoggingPlugin
    }

    @Volatile
    var arrayHasherPlugin: ArrayHasherPlugin = loadPlugin(ArrayHasherPlugin)
        private set
    @Volatile
    var concurrentMapPlugin: ConcurrentMapPlugin = loadPlugin(ConcurrentMapPlugin)
        private set
    @Volatile
    var concurrentSetPlugin: ConcurrentSetPlugin = loadPlugin(ConcurrentSetPlugin)
        private set
    @Volatile
    var internalQueuePlugin: InternalQueuePlugin = loadPlugin(InternalQueuePlugin)
        private set
    @Volatile
    var mpscQueuePlugin: MpscQueuePlugin = loadPlugin(MpscQueuePlugin)
        private set
    @Volatile
    var spscQueuePlugin: SpscQueuePlugin = loadPlugin(SpscQueuePlugin)
        private set

    private fun loadProperties(): Properties {
        val properties = Properties()
        ignoreException {
            val stream = javaClass.classLoader.getResource(PRONGHORN_PROPERTIES)?.openStream()
            if (stream != null) {
                properties.load(stream)
            }
        }
        return properties
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> loadPlugin(plugin: Plugin<T>): T {
        val logger = LoggingPlugin.get(javaClass)
        val type: Type = (plugin.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        val pluginName = type.typeName.replaceBeforeLast('.', "").replace(".", "")
        val pluginClassName = properties.getProperty("$PRONGHORN_PLUGIN_PREFIX.$pluginName")
        if (pluginClassName != null) {
            try {
                val clazz = Class.forName(pluginClassName)
                if (clazz.interfaces.find { it == type } != null) {
                    val instance = clazz.kotlin.objectInstance
                    logger.debug { "Loading plugin $pluginName implementation : ${clazz.name}" }
                    if (instance != null) {
                        return instance as T
                    }
                    else {
                        return clazz.newInstance() as T
                    }
                }
            }
            catch (ex: Exception) {
                logger.error { "Failed to load plugin $pluginName : ${ex.javaClass.simpleName} ${ex.message}" }
                logger.warn { "Falling back to default for $pluginName" }
            }
        }

        return plugin.default
    }

    fun setPlugin(plugin: Any): Boolean {
        when (plugin) {
            is LoggingPlugin -> loggingPlugin = plugin
            is ArrayHasherPlugin -> arrayHasherPlugin = plugin
            is ConcurrentMapPlugin -> concurrentMapPlugin = plugin
            is ConcurrentSetPlugin -> concurrentSetPlugin = plugin
            is InternalQueuePlugin -> internalQueuePlugin = plugin
            is MpscQueuePlugin -> mpscQueuePlugin = plugin
            is SpscQueuePlugin -> spscQueuePlugin = plugin
            else -> return false
        }
        return true
    }
}
