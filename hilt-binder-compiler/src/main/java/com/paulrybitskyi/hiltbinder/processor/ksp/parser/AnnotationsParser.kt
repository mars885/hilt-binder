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

package com.paulrybitskyi.hiltbinder.processor.ksp.parser

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.paulrybitskyi.hiltbinder.processor.javac.utils.castEach
import com.paulrybitskyi.hiltbinder.processor.ksp.model.BindingSchema
import com.paulrybitskyi.hiltbinder.processor.ksp.model.ModuleSchema
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.factories.BindingSchemaFactory
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.factories.ModuleSchemaFactory
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.providers.PackageNameProvider

internal class AnnotationsParser(
    private val bindingSchemaFactory: BindingSchemaFactory,
    private val moduleSchemaFactory: ModuleSchemaFactory,
    private val packageNameProvider: PackageNameProvider
) {


    fun parse(annotatedSymbols: Sequence<KSAnnotated>): List<ModuleSchema> {
        val classSymbols = annotatedSymbols.castEach<KSClassDeclaration>()
        val bindings = classSymbols.map(bindingSchemaFactory::createBindingSchema)
        val packageName = packageNameProvider.providePackageName(bindings)

        return bindings
            .groupBy(BindingSchema::component)
            .map { (component, bindings) ->
                moduleSchemaFactory.createModuleSchema(packageName, component, bindings)
            }
    }


}