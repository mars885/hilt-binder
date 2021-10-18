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

import com.paulrybitskyi.hiltbinder.common.utils.PACKAGE_SEPARATOR
import com.paulrybitskyi.hiltbinder.common.utils.safeCast
import com.paulrybitskyi.hiltbinder.compiler.processing.XFiler
import javax.annotation.processing.Filer
import javax.lang.model.element.Element

internal class JavacFiler(
    private val delegate: Filer
) : XFiler {

    override fun createSourceFile(file: XFiler.File) {
        val qualifiedFileName = file.createQualifiedFileName()
        val originatingElements = file.getOriginatingElements()

        @Suppress("SpreadOperator")
        delegate.createSourceFile(qualifiedFileName, *originatingElements)
            .openWriter()
            .use { writer -> writer.write(file.content) }
    }

    private fun XFiler.File.createQualifiedFileName(): String {
        return when {
            packageName.isEmpty() -> name
            else -> "$packageName$PACKAGE_SEPARATOR$name"
        }
    }

    private fun XFiler.File.getOriginatingElements(): Array<Element> {
        return originatingElements
            .mapNotNull { it.safeCast<JavacOriginatingElement>()?.element }
            .toTypedArray()
    }
}
