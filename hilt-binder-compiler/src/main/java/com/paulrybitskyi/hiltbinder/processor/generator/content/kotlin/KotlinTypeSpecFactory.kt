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

package com.paulrybitskyi.hiltbinder.processor.generator.content.kotlin

import com.paulrybitskyi.hiltbinder.processor.generator.content.TypeSpecFactory
import com.paulrybitskyi.hiltbinder.processor.generator.content.createMethodSpecs
import com.paulrybitskyi.hiltbinder.processor.generator.content.utils.DAGGER_TYPE_INSTALL_IN_KOTLIN_CLASS_NAME
import com.paulrybitskyi.hiltbinder.processor.generator.content.utils.DAGGER_TYPE_MODULE_KOTLIN_CLASS_NAME
import com.paulrybitskyi.hiltbinder.processor.model.ModuleSchema
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec

internal class KotlinTypeSpecFactory(
    private val bindingMethodSpecFactory: KotlinBindingMethodSpecFactory,
) : TypeSpecFactory<TypeSpec> {

    override fun createTypeSpec(moduleSchema: ModuleSchema): TypeSpec {
        return TypeSpec.interfaceBuilder(moduleSchema.interfaceName)
            .addModifiers(KModifier.INTERNAL)
            .addAnnotation(DAGGER_TYPE_MODULE_KOTLIN_CLASS_NAME)
            .addAnnotation(moduleSchema.createComponentInstallationAnnotation())
            .addFunctions(bindingMethodSpecFactory.createMethodSpecs(moduleSchema.bindings))
            .build()
    }

    private fun ModuleSchema.createComponentInstallationAnnotation(): AnnotationSpec {
        return AnnotationSpec.builder(DAGGER_TYPE_INSTALL_IN_KOTLIN_CLASS_NAME)
            .addMember("%T::class", componentType.kotlinClassName)
            .build()
    }
}
