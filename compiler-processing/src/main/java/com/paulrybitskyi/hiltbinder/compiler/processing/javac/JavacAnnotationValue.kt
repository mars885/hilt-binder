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

package com.paulrybitskyi.hiltbinder.compiler.processing.javac

import com.paulrybitskyi.hiltbinder.common.utils.safeCast
import com.paulrybitskyi.hiltbinder.compiler.processing.XAnnotationValue
import com.paulrybitskyi.hiltbinder.compiler.processing.XType
import com.paulrybitskyi.hiltbinder.compiler.processing.factories.XTypeFactory
import javax.lang.model.type.TypeMirror

internal class JavacAnnotationValue(
    private val env: JavacProcessingEnv,
    private val value: Any?
): XAnnotationValue {


    override fun getAsBoolean(default: Boolean): Boolean {
        return (value?.safeCast() ?: default)
    }


    override fun <T : Enum<*>> getAsEnum(valueOf: (String) -> T, default: T): T {
        return try {
            valueOf(value.toString())
        } catch(ignore: Throwable) {
            default
        }
    }


    override fun getAsType(default: XType?): XType? {
        return value?.safeCast<TypeMirror>()
            ?.let { XTypeFactory.createJavacType(env, it) }
            ?: default
    }


}
