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

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.paulrybitskyi.hiltbinder.compiler.processing.XBackend
import com.paulrybitskyi.hiltbinder.compiler.processing.XFiler
import com.paulrybitskyi.hiltbinder.compiler.processing.XLogger
import com.paulrybitskyi.hiltbinder.compiler.processing.XProcessingEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.XRoundEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.XType
import com.paulrybitskyi.hiltbinder.compiler.processing.XTypeElement
import com.paulrybitskyi.hiltbinder.compiler.processing.factories.XFilerFactory
import com.paulrybitskyi.hiltbinder.compiler.processing.factories.XLoggerFactory
import com.paulrybitskyi.hiltbinder.compiler.processing.factories.XRoundEnvFactory
import com.paulrybitskyi.hiltbinder.compiler.processing.factories.XTypeElementFactory
import com.paulrybitskyi.hiltbinder.compiler.processing.factories.XTypeFactory
import com.paulrybitskyi.hiltbinder.compiler.processing.ksp.utils.getTypeByName

internal class KspProcessingEnv(
    private val delegate: SymbolProcessorEnvironment,
    val resolver: Resolver
) : XProcessingEnv {

    override val backend = XBackend.KSP

    override val logger: XLogger by lazy {
        XLoggerFactory.createKspLogger(delegate.logger)
    }

    override val roundEnv: XRoundEnv by lazy {
        XRoundEnvFactory.createKspEnv(this)
    }

    override val filer: XFiler by lazy {
        XFilerFactory.createKspFiler(delegate.codeGenerator)
    }

    override fun getType(qualifiedName: String): XType? {
        return resolver.getTypeByName(qualifiedName)?.let {
            XTypeFactory.createKspType(this, it)
        }
    }

    override fun getTypeElement(qualifiedName: String): XTypeElement? {
        return resolver.getClassDeclarationByName(qualifiedName)?.let {
            XTypeElementFactory.createKspTypeElement(this, it)
        }
    }
}
