package tech.pronghorn.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tech.pronghorn.test.PronghornTest
import java.nio.channels.*

class SelectionKeyUtilTests: PronghornTest() {
    @Test
    fun appendTest() {
        val socket = SocketChannel.open()
        socket.configureBlocking(false)
        val selector = Selector.open()
        val key = socket.register(selector, 0)
        assertEquals(key.interestOps(), 0)

        key.addInterestOps(SelectionKey.OP_READ)
        assertEquals(key.interestOps(), SelectionKey.OP_READ)

        key.addInterestOps(SelectionKey.OP_WRITE)
        assertEquals(key.interestOps(), SelectionKey.OP_READ.or(SelectionKey.OP_WRITE))

        key.addInterestOps(SelectionKey.OP_CONNECT)
        assertEquals(key.interestOps(), SelectionKey.OP_READ.or(SelectionKey.OP_WRITE).or(SelectionKey.OP_CONNECT))
    }

    @Test
    fun removeTest() {
        val socket = SocketChannel.open()
        socket.configureBlocking(false)
        val selector = Selector.open()
        val key = socket.register(selector, SelectionKey.OP_READ.or(SelectionKey.OP_WRITE).or(SelectionKey.OP_CONNECT))
        assertEquals(key.interestOps(), SelectionKey.OP_READ.or(SelectionKey.OP_WRITE).or(SelectionKey.OP_CONNECT))

        key.removeInterestOps(SelectionKey.OP_CONNECT)
        assertEquals(key.interestOps(), SelectionKey.OP_READ.or(SelectionKey.OP_WRITE))

        key.removeInterestOps(SelectionKey.OP_WRITE)
        assertEquals(key.interestOps(), SelectionKey.OP_READ)

        key.removeInterestOps(SelectionKey.OP_READ)
        assertEquals(key.interestOps(), 0)
    }
}
