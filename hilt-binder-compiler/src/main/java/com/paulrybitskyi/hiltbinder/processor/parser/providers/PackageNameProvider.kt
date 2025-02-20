/*
 * Copyright 2021 Paul Rybitskyi, oss@paulrybitskyi.com
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

package com.paulrybitskyi.hiltbinder.processor.parser.providers

import com.paulrybitskyi.hiltbinder.common.utils.PACKAGE_SEPARATOR
import com.paulrybitskyi.hiltbinder.processor.model.BindingSchema
import org.apache.commons.lang3.StringUtils

internal class PackageNameProvider {

    fun providePackageName(bindings: Sequence<BindingSchema>): String {
        @Suppress("SpreadOperator")
        return bindings
            .map(BindingSchema::packageName)
            .toList()
            .toTypedArray()
            .let { StringUtils.getCommonPrefix(*it) }
            .trimEnd(PACKAGE_SEPARATOR)
    }
}
