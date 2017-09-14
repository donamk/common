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

package tech.pronghorn.logging

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

internal class BootstrapLogger(override val name: String) : Logger() {
    override val isTraceEnabled: Boolean = false
    override val isDebugEnabled: Boolean = false
    override val isInfoEnabled: Boolean = false
    override val isWarnEnabled: Boolean = true
    override val isErrorEnabled: Boolean = true

    private val gmt = ZoneId.of("GMT")

    private fun getFormattedDate(): String = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(gmt))
    private fun getFormattedMessage(level: String,
                                    message: String): String = "${getFormattedDate()} $level $name - $message"

    override fun trace(message: String) = Unit
    override fun debug(message: String) = Unit
    override fun info(message: String) = Unit
    override fun warn(message: String) = System.err.println(getFormattedMessage("WARN", message))
    override fun error(message: String) = System.err.println(getFormattedMessage("ERROR", message))
}
