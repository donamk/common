package tech.pronghorn.plugins

import tech.pronghorn.PRONGHORN_PLUGIN_PREFIX
import tech.pronghorn.PRONGHORN_PROPERTIES
import tech.pronghorn.plugins.arrayHash.ArrayHasherPlugin
import tech.pronghorn.plugins.concurrentMap.ConcurrentMapPlugin
import tech.pronghorn.plugins.concurrentSet.ConcurrentSetPlugin
import tech.pronghorn.plugins.internalQueue.InternalQueuePlugin
import tech.pronghorn.plugins.logging.BootstrapLoggingPlugin
import tech.pronghorn.plugins.logging.LoggingPlugin
import tech.pronghorn.plugins.mpmcQueue.MpmcQueuePlugin
import tech.pronghorn.plugins.mpscQueue.MpscQueuePlugin
import tech.pronghorn.plugins.spscQueue.SpscQueuePlugin
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.Properties

internal object PluginManager {
    private val properties = loadProperties()

    @Volatile
    var loggingPlugin: LoggingPlugin = BootstrapLoggingPlugin
        private set

    init {
        loggingPlugin = loadPlugin(LoggingPlugin)
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
    var mpmcQueuePlugin: MpmcQueuePlugin = loadPlugin(MpmcQueuePlugin)
        private set
    @Volatile
    var mpscQueuePlugin: MpscQueuePlugin = loadPlugin(MpscQueuePlugin)
        private set
    @Volatile
    var spscQueuePlugin: SpscQueuePlugin = loadPlugin(SpscQueuePlugin)
        private set

    private fun loadProperties(): Properties {
        val properties = Properties()
        try {
            val stream = javaClass.classLoader.getResource(PRONGHORN_PROPERTIES)?.openStream()
            if (stream != null) {
                properties.load(stream)
            }
        }
        catch (ex: Exception) {
            // no-op
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
            is MpmcQueuePlugin -> mpmcQueuePlugin = plugin
            is MpscQueuePlugin -> mpscQueuePlugin = plugin
            is SpscQueuePlugin -> spscQueuePlugin = plugin
            else -> return false
        }
        return true
    }
}
