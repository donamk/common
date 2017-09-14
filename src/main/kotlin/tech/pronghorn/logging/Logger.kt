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

abstract class Logger {
    abstract val name: String

    abstract val isTraceEnabled: Boolean
    abstract val isDebugEnabled: Boolean
    abstract val isInfoEnabled: Boolean
    abstract val isWarnEnabled: Boolean
    abstract val isErrorEnabled: Boolean

    abstract protected fun trace(message: String)
    abstract protected fun debug(message: String)
    abstract protected fun info(message: String)
    abstract protected fun warn(message: String)
    abstract protected fun error(message: String)

    fun trace(block: () -> String) {
        if (isTraceEnabled) trace(block())
    }

    fun debug(block: () -> String) {
        if (isDebugEnabled) debug(block())
    }

    fun info(block: () -> String) {
        if (isInfoEnabled) info(block())
    }

    fun warn(block: () -> String) {
        if (isWarnEnabled) warn(block())
    }

    fun error(block: () -> String) {
        if (isErrorEnabled) error(block())
    }
}
