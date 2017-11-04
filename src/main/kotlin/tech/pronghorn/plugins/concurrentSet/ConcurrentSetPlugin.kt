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

package tech.pronghorn.plugins.concurrentSet

import tech.pronghorn.plugins.Plugin
import tech.pronghorn.plugins.PluginManager

public interface ConcurrentSetPlugin {
    companion object : Plugin<ConcurrentSetPlugin>(ConcurrentSetDefaultPlugin) {
        override fun getPlugin() = PluginManager.concurrentSetPlugin

        public fun <T> get(): MutableSet<T> = PluginManager.concurrentSetPlugin.get()
    }

    public fun <T> get(): MutableSet<T>
}
