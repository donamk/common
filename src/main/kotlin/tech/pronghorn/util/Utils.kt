package tech.pronghorn.util

import java.io.PrintWriter
import java.io.StringWriter

fun Exception.stackTraceToString(): String {
    val exceptionWriter = StringWriter()
    printStackTrace(PrintWriter(exceptionWriter))
    return exceptionWriter.toString()
}
