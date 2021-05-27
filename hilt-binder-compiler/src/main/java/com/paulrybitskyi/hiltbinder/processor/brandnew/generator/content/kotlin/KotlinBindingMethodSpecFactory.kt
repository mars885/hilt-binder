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

package com.paulrybitskyi.hiltbinder.processor.brandnew.generator.content.kotlin

import com.paulrybitskyi.hiltbinder.compiler.processing.XAnnotation
import com.paulrybitskyi.hiltbinder.processor.brandnew.generator.content.BindingMethodSpecFactory
import com.paulrybitskyi.hiltbinder.processor.brandnew.generator.content.utils.DAGGER_TYPE_BINDS_KOTLIN_CLASS_NAME
import com.paulrybitskyi.hiltbinder.processor.brandnew.generator.content.utils.DAGGER_TYPE_INTO_MAP_KOTLIN_CLASS_NAME
import com.paulrybitskyi.hiltbinder.processor.brandnew.generator.content.utils.DAGGER_TYPE_INTO_SET_KOTLIN_CLASS_NAME
import com.paulrybitskyi.hiltbinder.processor.brandnew.model.BindingSchema
import com.paulrybitskyi.hiltbinder.processor.brandnew.model.ContributionType
import com.paulrybitskyi.hiltbinder.processor.brandnew.model.ReturnType
import com.paulrybitskyi.hiltbinder.processor.brandnew.utils.typeElement
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.STAR
import com.squareup.kotlinpoet.TypeName

internal class KotlinBindingMethodSpecFactory : BindingMethodSpecFactory<FunSpec> {


    override fun createMethodSpec(bindingSchema: BindingSchema): FunSpec {
        return FunSpec.builder(bindingSchema.methodName)
            .addAnnotation(DAGGER_TYPE_BINDS_KOTLIN_CLASS_NAME)
            .addMultibindingAnnotationsIfExist(bindingSchema.contributionType)
            .addQualifierAnnotationIfExists(bindingSchema.qualifierAnnotation)
            .addModifiers(KModifier.ABSTRACT, KModifier.PUBLIC)
            .addParameter(bindingSchema.paramName, bindingSchema.paramType.kotlinClassName)
            .returns(bindingSchema.returnType.toTypeName())
            .build()
    }


    private fun FunSpec.Builder.addMultibindingAnnotationsIfExist(
        contributionType: ContributionType?
    ): FunSpec.Builder = apply {
        if(contributionType == null) return@apply

        when(contributionType) {
            is ContributionType.Set -> contributeToSet()
            is ContributionType.Map -> contributeToMap(contributionType.mapKeyAnnotation)
        }
    }


    private fun FunSpec.Builder.contributeToSet(): FunSpec.Builder = apply {
        addAnnotation(DAGGER_TYPE_INTO_SET_KOTLIN_CLASS_NAME)
    }


    private fun FunSpec.Builder.contributeToMap(
        mapKeyAnnotation: XAnnotation
    ): FunSpec.Builder = apply {
        addAnnotation(DAGGER_TYPE_INTO_MAP_KOTLIN_CLASS_NAME)
        addAnnotation(mapKeyAnnotation.kotlinAnnoSpec)
    }


    private fun FunSpec.Builder.addQualifierAnnotationIfExists(
        qualifierAnnotation: XAnnotation?
    ): FunSpec.Builder = apply {
        if(qualifierAnnotation == null) return@apply

        addAnnotation(qualifierAnnotation.kotlinAnnoSpec)
    }


    private fun ReturnType.toTypeName(): TypeName {
        return when(this) {
            is ReturnType.Standard -> this.type.typeElement.kotlinClassName
            is ReturnType.Generic -> when(this) {
                is ReturnType.Generic.Parameterized -> this.type.kotlinTypeName
                is ReturnType.Generic.UnboundedWildcard -> {
                    val typeArgs = List(typeParamCount) { STAR }

                    this.type.typeElement.kotlinClassName.parameterizedBy(typeArgs)
                }
            }
        }
    }


}