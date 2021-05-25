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

package com.paulrybitskyi.hiltbinder.processor.ksp.parser.factories

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.paulrybitskyi.hiltbinder.processor.ksp.model.BindingSchema
import com.paulrybitskyi.hiltbinder.processor.ksp.model.HiltComponent
import com.paulrybitskyi.hiltbinder.processor.ksp.model.ModuleSchema
import com.paulrybitskyi.hiltbinder.processor.ksp.model.qualifiedName

internal class ModuleSchemaFactory(
    private val resolver: Resolver,
    private val moduleInterfaceNameFactory: ModuleInterfaceNameFactory
) {


    fun createModuleSchema(
        packageName: String,
        component: HiltComponent,
        bindings: List<BindingSchema>
    ): ModuleSchema {
        return ModuleSchema(
            packageName = packageName,
            interfaceName = moduleInterfaceNameFactory.createInterfaceName(component),
            componentType = component.toClassDeclaration(),
            bindings = bindings.sortedBy(BindingSchema::methodName)
        )
    }


    private fun HiltComponent.toClassDeclaration(): KSClassDeclaration {
        return checkNotNull(resolver.getClassDeclarationByName(qualifiedName))
    }


}