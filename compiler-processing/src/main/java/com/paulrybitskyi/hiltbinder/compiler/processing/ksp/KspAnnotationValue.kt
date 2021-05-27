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

package com.paulrybitskyi.hiltbinder.compiler.processing.ksp

import com.google.devtools.ksp.symbol.KSType
import com.paulrybitskyi.hiltbinder.common.utils.safeCast
import com.paulrybitskyi.hiltbinder.common.utils.unsafeCast
import com.paulrybitskyi.hiltbinder.compiler.processing.XAnnotationValue
import com.paulrybitskyi.hiltbinder.compiler.processing.XType
import com.paulrybitskyi.hiltbinder.compiler.processing.factories.XTypeFactory
import com.paulrybitskyi.hiltbinder.compiler.processing.ksp.utils.simpleName

internal class KspAnnotationValue(
    private val env: KspProcessingEnv,
    private val value: Any?
): XAnnotationValue {


    override fun getAsBoolean(default: Boolean): Boolean {
        return (value?.safeCast() ?: default)
    }


    override fun <T : Enum<*>> getAsEnum(valueOf: (String) -> T, default: T): T {
        return try {
            when(value) {
                is Enum<*> -> value.unsafeCast()
                is KSType -> valueOf(value.simpleName)
                else -> default
            }
        } catch(error: Throwable) {
            default
        }
    }


    override fun getAsType(default: XType?): XType? {
        return value?.safeCast<KSType>()
            ?.let { XTypeFactory.createKspType(env, it) }
            ?: default
    }


}