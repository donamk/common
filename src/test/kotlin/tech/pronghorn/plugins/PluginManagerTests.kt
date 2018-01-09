package tech.pronghorn.plugins

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tech.pronghorn.plugins.arrayHash.ArrayHasherPlugin
import tech.pronghorn.plugins.concurrentMap.ConcurrentMapPlugin
import tech.pronghorn.plugins.concurrentSet.ConcurrentSetPlugin
import tech.pronghorn.plugins.internalQueue.InternalQueuePlugin
import tech.pronghorn.plugins.logging.LoggingPlugin
import tech.pronghorn.plugins.mpscQueue.MpscQueuePlugin
import tech.pronghorn.plugins.spmcQueue.SpmcQueuePlugin
import tech.pronghorn.plugins.spscQueue.SpscQueuePlugin
import tech.pronghorn.test.PronghornTest

class PluginManagerTests: PronghornTest() {
    @Test
    fun resetDefaultPluginsTest() {
        val preLogging = PluginManager.loggingPlugin
        val preArrayHasher = PluginManager.arrayHasherPlugin
        val preConcurrentMap = PluginManager.concurrentMapPlugin
        val preConcurrentSet = PluginManager.concurrentSetPlugin
        val preInternalQueue = PluginManager.internalQueuePlugin
        val preMpscQueue = PluginManager.mpscQueuePlugin
        val preSpmcQueue = PluginManager.spmcQueuePlugin
        val preSpscQueue = PluginManager.spscQueuePlugin

        LoggingPlugin.setPlugin(LoggingPlugin.default)
        ArrayHasherPlugin.setPlugin(ArrayHasherPlugin.default)
        ConcurrentMapPlugin.setPlugin(ConcurrentMapPlugin.default)
        ConcurrentSetPlugin.setPlugin(ConcurrentSetPlugin.default)
        InternalQueuePlugin.setPlugin(InternalQueuePlugin.default)
        MpscQueuePlugin.setPlugin(MpscQueuePlugin.default)
        SpmcQueuePlugin.setPlugin(SpmcQueuePlugin.default)
        SpscQueuePlugin.setPlugin(SpscQueuePlugin.default)

        assertEquals(LoggingPlugin.getPlugin(), LoggingPlugin.default)
        assertEquals(ArrayHasherPlugin.getPlugin(), ArrayHasherPlugin.default)
        assertEquals(ConcurrentMapPlugin.getPlugin(), ConcurrentMapPlugin.default)
        assertEquals(ConcurrentSetPlugin.getPlugin(), ConcurrentSetPlugin.default)
        assertEquals(InternalQueuePlugin.getPlugin(), InternalQueuePlugin.default)
        assertEquals(MpscQueuePlugin.getPlugin(), MpscQueuePlugin.default)
        assertEquals(SpmcQueuePlugin.getPlugin(), SpmcQueuePlugin.default)
        assertEquals(SpscQueuePlugin.getPlugin(), SpscQueuePlugin.default)

        LoggingPlugin.setPlugin(preLogging)
        ArrayHasherPlugin.setPlugin(preArrayHasher)
        ConcurrentMapPlugin.setPlugin(preConcurrentMap)
        ConcurrentSetPlugin.setPlugin(preConcurrentSet)
        InternalQueuePlugin.setPlugin(preInternalQueue)
        MpscQueuePlugin.setPlugin(preMpscQueue)
        SpmcQueuePlugin.setPlugin(preSpmcQueue)
        SpscQueuePlugin.setPlugin(preSpscQueue)
    }
}
