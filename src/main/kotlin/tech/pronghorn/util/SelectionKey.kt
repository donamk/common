package tech.pronghorn.util

import java.nio.channels.SelectionKey

fun SelectionKey.removeInterestOps(removeInterestOps: Int) {
    ignoreException { interestOps(interestOps() and removeInterestOps.inv()) }
}

fun SelectionKey.addInterestOps(newInterestOps: Int) {
    ignoreException { interestOps(interestOps() or newInterestOps) }
}
