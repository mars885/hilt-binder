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

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.paulrybitskyi.hiltbinder.common.utils.safeCast
import com.paulrybitskyi.hiltbinder.compiler.processing.XType
import com.paulrybitskyi.hiltbinder.compiler.processing.XTypeElement
import com.paulrybitskyi.hiltbinder.compiler.processing.factories.XTypeFactory
import com.paulrybitskyi.hiltbinder.compiler.processing.ksp.utils.*
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.JavaClassName
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.KotlinClassName

internal class KspTypeElement(
    env: KspProcessingEnv,
    override val delegate: KSClassDeclaration
): KspElement(env, delegate), XTypeElement {


    override val qualifiedName: String by lazy {
        checkNotNull(super.qualifiedName)
    }

    override val isClass: Boolean
        get() = delegate.isClass

    override val type: XType by lazy {
        XTypeFactory.createKspType(env, delegate.asType())
    }

    override val superclass: XType? by lazy {
        env.resolver
            .getSuperclass(delegate)
            ?.let { XTypeFactory.createKspType(env, it) }
    }

    override val interfaces: List<XType> by lazy {
        XTypeFactory.createKspTypes(env, delegate.getInterfaces().toList())
    }

    override val javaClassName: JavaClassName by lazy {
        delegate.toJavaClassName()
    }

    override val kotlinClassName: KotlinClassName by lazy {
        delegate.toKotlinClassName()
    }


    override fun equals(other: Any?): Boolean {
        return (delegate == other?.safeCast<KspTypeElement>()?.delegate)
    }


    override fun hashCode(): Int {
        return delegate.hashCode()
    }


    override fun toString(): String {
        return delegate.toString()
    }


}