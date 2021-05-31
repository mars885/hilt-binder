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

package com.paulrybitskyi.hiltbinder.processor.ksp.generator

import com.google.devtools.ksp.symbol.*
import com.paulrybitskyi.hiltbinder.processor.ksp.model.BindingSchema
import com.paulrybitskyi.hiltbinder.processor.ksp.model.ContributionType
import com.paulrybitskyi.hiltbinder.processor.ksp.model.ReturnType
import com.paulrybitskyi.hiltbinder.processor.ksp.utils.classDeclaration
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

internal class BindingMethodSpecFactory {


    fun create(bindingSchemas: List<BindingSchema>): List<FunSpec> {
        return bindingSchemas.map {
            FunSpec.builder(it.methodName)
                .addAnnotation(DAGGER_TYPE_BINDS)
                .addMultibindingAnnotationsIfExist(it.contributionType)
                .addQualifierAnnotationIfExists(it.qualifierAnnotation)
                .addModifiers(KModifier.ABSTRACT, KModifier.PUBLIC)
                .addParameter(it.paramName, it.paramType.toClassName())
                .returns(it.returnType.toTypeName())
                .build()
        }
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
        addAnnotation(DAGGER_TYPE_INTO_SET)
    }


    private fun FunSpec.Builder.contributeToMap(
        mapKeyAnnotation: KSAnnotation
    ): FunSpec.Builder = apply {
        addAnnotation(DAGGER_TYPE_INTO_MAP)
        addAnnotation(mapKeyAnnotation.toAnnotationSpec())
    }


    private fun FunSpec.Builder.addQualifierAnnotationIfExists(
        qualifierAnnotation: KSAnnotation?
    ): FunSpec.Builder = apply {
        if(qualifierAnnotation == null) return@apply

        addAnnotation(qualifierAnnotation.toAnnotationSpec())
    }


    private fun ReturnType.toTypeName(): TypeName {
        return when(this) {
            is ReturnType.Standard -> type.classDeclaration.toClassName()
            is ReturnType.Generic -> when(this) {
                is ReturnType.Generic.Parameterized -> type.toTypeName()
                is ReturnType.Generic.UnboundedWildcard -> {
                    val typeArgs = List(typeParamCount) { STAR }

                    type.classDeclaration.toClassName().parameterizedBy(typeArgs)
                }
            }
        }
    }


}