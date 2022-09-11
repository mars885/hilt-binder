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

package com.paulrybitskyi.hiltbinder.processor.parser

import com.paulrybitskyi.hiltbinder.common.utils.safeCastEach
import com.paulrybitskyi.hiltbinder.compiler.processing.XElement
import com.paulrybitskyi.hiltbinder.compiler.processing.XTypeElement
import com.paulrybitskyi.hiltbinder.processor.model.BindingSchema
import com.paulrybitskyi.hiltbinder.processor.model.ModuleSchema
import com.paulrybitskyi.hiltbinder.processor.parser.factories.BindingSchemaFactory
import com.paulrybitskyi.hiltbinder.processor.parser.factories.ModuleSchemaFactory
import com.paulrybitskyi.hiltbinder.processor.parser.providers.PackageNameProvider

internal class AnnotationsParser(
    private val bindingSchemaFactory: BindingSchemaFactory,
    private val moduleSchemaFactory: ModuleSchemaFactory,
    private val packageNameProvider: PackageNameProvider
) {

    fun parse(
        primaryAnnotatedElements: Sequence<XElement>,
        predefinedAnnotatedElements: Sequence<XElement>,
    ): List<ModuleSchema> {
        val primaryElements = primaryAnnotatedElements.safeCastEach<XTypeElement>()
        val predefinedElements = predefinedAnnotatedElements.safeCastEach<XTypeElement>()
        val bindings = primaryElements.map(bindingSchemaFactory::createBindingSchema) +
                predefinedElements.flatMap { bindingSchemaFactory.createBindingSchemaWith(it) }
        val packageName = packageNameProvider.providePackageName(bindings)

        return bindings
            .groupBy(BindingSchema::component)
            .map { (component, bindings) ->
                moduleSchemaFactory.createModuleSchema(packageName, component, bindings)
            }
    }
}
