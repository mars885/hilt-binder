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

package com.paulrybitskyi.hiltbinder.processor.brandnew.generator.content.java

import com.paulrybitskyi.hiltbinder.processor.brandnew.generator.content.ModuleFileContentGenerator
import com.paulrybitskyi.hiltbinder.processor.brandnew.generator.content.utils.addNewlineCharacterAfterComment
import com.paulrybitskyi.hiltbinder.processor.brandnew.generator.content.utils.removeTrailingNewline
import com.paulrybitskyi.hiltbinder.processor.brandnew.model.ModuleSchema
import com.paulrybitskyi.hiltbinder.processor.brandnew.utils.BIND_TYPE_SIMPLE_NAME
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec

internal class JavaModuleFileContentGenerator(
    private val typeSpecFactory: JavaTypeSpecFactory
): ModuleFileContentGenerator {


    override fun generateFileContent(moduleSchema: ModuleSchema): String {
        val typeSpec = typeSpecFactory.createTypeSpec(moduleSchema)
        val javaFile = typeSpec.generateJavaFile(moduleSchema.packageName)
        val javaCode = javaFile.toString().reformatJavaCode()

        return javaCode
    }


    private fun TypeSpec.generateJavaFile(packageName: String): JavaFile {
        return JavaFile.builder(packageName, this)
            .addFileComment("Generated by @$BIND_TYPE_SIMPLE_NAME. Do not modify!")
            .build()
    }


    private fun String.reformatJavaCode(): String {
        return addNewlineCharacterAfterComment()
            .removeTrailingNewline()
    }


}