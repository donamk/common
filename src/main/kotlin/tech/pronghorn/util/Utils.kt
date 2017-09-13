package tech.pronghorn.util

import java.io.PrintWriter
import java.io.StringWriter

fun Exception.stackTraceToString(): String {
    val exceptionWriter = StringWriter()
    printStackTrace(PrintWriter(exceptionWriter))
    return exceptionWriter.toString()
}

fun runAllIgnoringExceptions(vararg blocks: () -> Unit) {
    blocks.forEach { block ->
        try {
            block()
        }
        catch (ex: Exception) {
            // no-op
        }
    }
}
