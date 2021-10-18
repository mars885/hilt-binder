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

package com.paulrybitskyi.hiltbinder.processor.generator.content.kotlin

import com.paulrybitskyi.hiltbinder.processor.generator.content.ModuleFileContentGenerator
import com.paulrybitskyi.hiltbinder.processor.generator.content.utils.addNewlineCharacterAfterComment
import com.paulrybitskyi.hiltbinder.processor.generator.content.utils.removeTrailingNewline
import com.paulrybitskyi.hiltbinder.processor.model.ModuleSchema
import com.paulrybitskyi.hiltbinder.processor.utils.BIND_TYPE_SIMPLE_NAME
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec

internal class KotlinModuleFileContentGenerator(
    private val typeSpecFactory: KotlinTypeSpecFactory
): ModuleFileContentGenerator {


    override fun generateFileContent(moduleSchema: ModuleSchema): String {
        val typeSpec = typeSpecFactory.createTypeSpec(moduleSchema)
        val kotlinFile = typeSpec.generateKotlinFile(moduleSchema)
        val kotlinCode = kotlinFile.toString().reformatKotlinCode()

        return kotlinCode
    }


    private fun TypeSpec.generateKotlinFile(moduleSchema: ModuleSchema): FileSpec {
        return FileSpec.builder(moduleSchema.packageName, moduleSchema.interfaceName)
            .addComment("Generated by @$BIND_TYPE_SIMPLE_NAME. Do not modify!")
            .addType(this)
            .build()
    }


    private fun String.reformatKotlinCode(): String {
        return addNewlineCharacterAfterComment()
            .removePublicKeywordFromMethods()
            .reformatLineBreaksForLongMethodNames()
            .removeTrailingNewline()
    }


    private fun String.removePublicKeywordFromMethods(): String {
        return replace("public( |\n      )".toRegex(), "")
    }


    private fun String.reformatLineBreaksForLongMethodNames(): String {
        return replace("\\(binding: (\\w+)\\):\n      ".toRegex()) { match ->
            // Apparently, [0] group is always the entire expression.
            // That's why [1] group is used here to grab the binding type.
            "(\n      binding: ${match.groupValues[1]}): "
        }
    }


}
