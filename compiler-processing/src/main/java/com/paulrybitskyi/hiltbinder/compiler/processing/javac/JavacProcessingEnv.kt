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

package com.paulrybitskyi.hiltbinder.compiler.processing.javac

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
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.utils.getType
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

internal class JavacProcessingEnv(
    private val delegate: ProcessingEnvironment,
    private val roundEnvironment: RoundEnvironment,
) : XProcessingEnv {

    val elementUtils: Elements = delegate.elementUtils
    val typeUtils: Types = delegate.typeUtils

    override val backend = XBackend.JAVAC

    override val logger: XLogger by lazy {
        XLoggerFactory.createJavacLogger(delegate.messager)
    }

    override val roundEnv: XRoundEnv by lazy {
        XRoundEnvFactory.createJavacEnv(this, roundEnvironment)
    }

    override val filer: XFiler by lazy {
        XFilerFactory.createJavacFiler(delegate.filer)
    }

    override fun getType(qualifiedName: String): XType? {
        return elementUtils.getType(qualifiedName)?.let {
            XTypeFactory.createJavacType(this, it)
        }
    }

    override fun getTypeElement(qualifiedName: String): XTypeElement? {
        return elementUtils.getTypeElement(qualifiedName)?.let {
            XTypeElementFactory.createJavacTypeElement(this, it)
        }
    }
}
