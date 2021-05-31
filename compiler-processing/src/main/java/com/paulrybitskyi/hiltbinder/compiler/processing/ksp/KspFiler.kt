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

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSFile
import com.paulrybitskyi.hiltbinder.common.utils.safeCast
import com.paulrybitskyi.hiltbinder.compiler.processing.XFiler

internal class KspFiler(
    private val delegate: CodeGenerator
): XFiler {


    override fun createSourceFile(file: XFiler.File) {
        delegate.createNewFile(
            dependencies = Dependencies(
                aggregating = true,
                sources = file.getOriginatingSourceFiles()
            ),
            packageName = file.packageName,
            fileName = file.name,
            extensionName = file.extension
        )
        .writer()
        .use { writer -> writer.write(file.content) }
    }


    private fun XFiler.File.getOriginatingSourceFiles(): Array<KSFile> {
        return originatingElements
            .mapNotNull { it.safeCast<KspOriginatingElement>()?.file }
            .toTypedArray()
    }


}