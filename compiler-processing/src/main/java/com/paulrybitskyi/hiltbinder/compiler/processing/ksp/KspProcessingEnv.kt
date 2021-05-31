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
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.paulrybitskyi.hiltbinder.compiler.processing.*
import com.paulrybitskyi.hiltbinder.compiler.processing.factories.*
import com.paulrybitskyi.hiltbinder.compiler.processing.ksp.utils.getTypeByName

internal class KspProcessingEnv(
    val resolver: Resolver,
    private val codeGenerator: CodeGenerator,
    private val kspLogger: KSPLogger
): XProcessingEnv {


    override val backend = XBackend.KSP

    override val logger: XLogger by lazy {
        XLoggerFactory.createKspLogger(kspLogger)
    }

    override val roundEnv: XRoundEnv by lazy {
        XRoundEnvFactory.createKspEnv(this)
    }

    override val filer: XFiler by lazy {
        XFilerFactory.createKspFiler(codeGenerator)
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