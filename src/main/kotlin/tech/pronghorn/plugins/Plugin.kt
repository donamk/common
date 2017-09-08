package tech.pronghorn.plugins

import mu.KotlinLogging
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.*

@Suppress("UNCHECKED_CAST")
abstract class Plugin<T>(private val default: T,
                         private val propertiesKey: String) {
    private val logger = KotlinLogging.logger {}

    private val type: Type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]

    private var setPlugin: T? = null

    protected val plugin: T by lazy {
        setPlugin ?: findPlugin()
    }

    fun set(value: T) {
        setPlugin = value
    }

    protected fun findPlugin(): T {
        val properties = Properties()
        try {
            val stream = javaClass.classLoader.getResource(PRONGHORN_PROPERTIES)?.openStream()
            if (stream != null) {
                logger.debug { "Loading config file $PRONGHORN_PROPERTIES" }
                properties.load(stream)
            }
        }
        catch (ex: Exception) {
            // no-op
        }

        val pluginClassName = properties.getProperty(propertiesKey)
        if (pluginClassName != null) {
            try {
                val clazz = Class.forName(pluginClassName)
                if (clazz.interfaces.find { it == type } != null) {
                    val instance = clazz.kotlin.objectInstance
                    logger.debug { "Loading plugin ${type.typeName} implementation : ${clazz.name}" }
                    if (instance != null) {
                        return instance as T
                    }
                    else {
                        return clazz.newInstance() as T
                    }
                }
            }
            catch (ex: Exception) {
                logger.error { "Failed to load plugin ${type.typeName} : ${ex.javaClass.simpleName} ${ex.message}" }
                logger.warn { "Falling back to default for ${type.typeName}" }
                return default
            }
        }

        logger.debug { "Loading plugin ${type.typeName} default implementation" }
        return default
    }
}
