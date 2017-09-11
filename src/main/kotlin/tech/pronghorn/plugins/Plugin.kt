package tech.pronghorn.plugins

@Suppress("UNCHECKED_CAST")
abstract class Plugin<T>(val default: T) {
    fun setPlugin(plugin: T) {
        PluginManager.setPlugin(plugin!!)
    }
}
