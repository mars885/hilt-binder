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

package com.paulrybitskyi.hiltbinder.processor.generator

import com.paulrybitskyi.hiltbinder.compiler.processing.XFiler
import com.paulrybitskyi.hiltbinder.compiler.processing.XOriginatingElement
import com.paulrybitskyi.hiltbinder.processor.generator.content.ModuleFileContentGenerator
import com.paulrybitskyi.hiltbinder.processor.model.ContributionType
import com.paulrybitskyi.hiltbinder.processor.model.HiltComponent
import com.paulrybitskyi.hiltbinder.processor.model.ModuleSchema

internal class ModuleFileGenerator(
    private val outputLanguage: Language,
    private val moduleFileContentGenerator: ModuleFileContentGenerator,
    private val filer: XFiler,
) {

    fun generateModuleFile(moduleSchema: ModuleSchema) {
        val fileContent = moduleFileContentGenerator.generateFileContent(moduleSchema)
        val file = XFiler.File(
            packageName = moduleSchema.packageName,
            name = moduleSchema.interfaceName,
            extension = outputLanguage.fileExtension,
            content = fileContent,
            originatingElements = moduleSchema.getOriginatingElements(),
        )

        filer.createSourceFile(file)
    }

    private fun ModuleSchema.getOriginatingElements(): List<XOriginatingElement> {
        val elements = buildList {
            add(componentType.originatingElement)

            for (binding in bindings) {
                if (binding.component is HiltComponent.Custom) {
                    add(binding.component.element.originatingElement)
                }

                if (binding.contributionType is ContributionType.Map) {
                    add(binding.contributionType.mapKeyAnnotation.type.element.originatingElement)
                }

                if (binding.qualifierAnnotation != null) {
                    add(binding.qualifierAnnotation.type.element.originatingElement)
                }

                add(binding.paramType.originatingElement)
                add(binding.returnType.type.element.originatingElement)
            }
        }

        return elements.filterNotNull()
    }
}

internal fun ModuleFileGenerator.generateModuleFiles(moduleSchemas: List<ModuleSchema>) {
    moduleSchemas.forEach(::generateModuleFile)
}
