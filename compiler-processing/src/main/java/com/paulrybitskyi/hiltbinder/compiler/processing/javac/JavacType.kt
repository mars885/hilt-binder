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
import com.paulrybitskyi.hiltbinder.compiler.processing.XElement
import com.paulrybitskyi.hiltbinder.compiler.processing.XType
import com.paulrybitskyi.hiltbinder.compiler.processing.factories.XElementFactory
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.utils.toJavaTypeName
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.utils.toKotlinTypeName
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.JavaTypeName
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.KotlinTypeName
import javax.lang.model.type.TypeMirror

internal class JavacType(
    private val env: JavacProcessingEnv,
    private val delegate: TypeMirror
): XType {


    override val element: XElement by lazy {
        XElementFactory.createJavacElement(env, env.typeUtils.asElement(delegate))
    }

    override val javaTypeName: JavaTypeName by lazy {
        delegate.toJavaTypeName()
    }

    override val kotlinTypeName: KotlinTypeName by lazy {
        delegate.toKotlinTypeName()
    }


    override fun isAssignableFrom(other: XType): Boolean {
        return other.safeCast<JavacType>()
            ?.let { env.typeUtils.isSubtype(it.delegate, delegate) }
            ?: false
    }


    override fun equals(other: Any?): Boolean {
        return other?.safeCast<JavacType>()
            ?.let { env.typeUtils.isSameType(delegate, it.delegate) }
            ?: false
    }


    override fun hashCode(): Int {
        return delegate.hashCode()
    }


    override fun toString(): String {
        return delegate.toString()
    }


}