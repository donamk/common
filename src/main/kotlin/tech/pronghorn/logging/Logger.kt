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

public abstract class Logger {
    public abstract val name: String

    public abstract val isTraceEnabled: Boolean
    public abstract val isDebugEnabled: Boolean
    public abstract val isInfoEnabled: Boolean
    public abstract val isWarnEnabled: Boolean
    public abstract val isErrorEnabled: Boolean

    public abstract fun traceImpl(message: String)
    public abstract fun debugImpl(message: String)
    public abstract fun infoImpl(message: String)
    public abstract fun warnImpl(message: String)
    public abstract fun errorImpl(message: String)

    public inline fun trace(block: () -> String) {
        if (isTraceEnabled) traceImpl(block())
    }

    public inline fun debug(block: () -> String) {
        if (isDebugEnabled) debugImpl(block())
    }

    public inline fun info(block: () -> String) {
        if (isInfoEnabled) infoImpl(block())
    }

    public inline fun warn(block: () -> String) {
        if (isWarnEnabled) warnImpl(block())
    }

    public inline fun error(block: () -> String) {
        if (isErrorEnabled) errorImpl(block())
    }
}
