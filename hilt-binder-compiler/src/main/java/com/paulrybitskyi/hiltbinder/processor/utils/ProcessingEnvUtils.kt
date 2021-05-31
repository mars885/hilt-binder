/*
 * Copyright 2021 Paul Rybitskyi, paul.rybitskyi.work@gmail.com
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

package com.paulrybitskyi.hiltbinder.processor.utils

import com.paulrybitskyi.hiltbinder.common.utils.ANY_TYPE_QUALIFIED_NAME
import com.paulrybitskyi.hiltbinder.common.utils.OBJECT_TYPE_QUALIFIED_NAME
import com.paulrybitskyi.hiltbinder.compiler.processing.XBackend
import com.paulrybitskyi.hiltbinder.compiler.processing.XProcessingEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.XType


internal fun XProcessingEnv.getTypeUnsafely(qualifiedName: String): XType {
    return checkNotNull(getType(qualifiedName))
}


internal fun XProcessingEnv.getRootType(backend: XBackend = this.backend): XType {
    return getTypeUnsafely(
        when(backend) {
            XBackend.JAVAC -> OBJECT_TYPE_QUALIFIED_NAME
            XBackend.KSP -> ANY_TYPE_QUALIFIED_NAME
        }
    )
}