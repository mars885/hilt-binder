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

package com.paulrybitskyi.hiltbinder.processor.generator.content.java

import com.paulrybitskyi.hiltbinder.compiler.processing.XAnnotation
import com.paulrybitskyi.hiltbinder.processor.generator.content.BindingMethodSpecFactory
import com.paulrybitskyi.hiltbinder.processor.generator.content.utils.DAGGER_TYPE_BINDS_JAVA_CLASS_NAME
import com.paulrybitskyi.hiltbinder.processor.generator.content.utils.DAGGER_TYPE_INTO_MAP_JAVA_CLASS_NAME
import com.paulrybitskyi.hiltbinder.processor.generator.content.utils.DAGGER_TYPE_INTO_SET_JAVA_CLASS_NAME
import com.paulrybitskyi.hiltbinder.processor.model.BindingSchema
import com.paulrybitskyi.hiltbinder.processor.model.ContributionType
import com.paulrybitskyi.hiltbinder.processor.model.ReturnType
import com.paulrybitskyi.hiltbinder.processor.utils.typeElement
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.WildcardTypeName
import javax.lang.model.element.Modifier

internal class JavaBindingMethodSpecFactory : BindingMethodSpecFactory<MethodSpec> {

    override fun createMethodSpec(bindingSchema: BindingSchema): MethodSpec {
        return MethodSpec.methodBuilder(bindingSchema.methodName)
            .addAnnotation(DAGGER_TYPE_BINDS_JAVA_CLASS_NAME)
            .addMultibindingAnnotationsIfExist(bindingSchema.contributionType)
            .addQualifierAnnotationIfExists(bindingSchema.qualifierAnnotation)
            .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
            .addParameter(bindingSchema.paramType.javaClassName, bindingSchema.paramName)
            .returns(bindingSchema.returnType.toTypeName())
            .build()
    }

    private fun MethodSpec.Builder.addMultibindingAnnotationsIfExist(
        contributionType: ContributionType?,
    ): MethodSpec.Builder = apply {
        if (contributionType == null) return@apply

        when (contributionType) {
            is ContributionType.Set -> contributeToSet()
            is ContributionType.Map -> contributeToMap(contributionType.mapKeyAnnotation)
        }
    }

    private fun MethodSpec.Builder.contributeToSet(): MethodSpec.Builder = apply {
        addAnnotation(DAGGER_TYPE_INTO_SET_JAVA_CLASS_NAME)
    }

    private fun MethodSpec.Builder.contributeToMap(
        mapKeyAnnotation: XAnnotation,
    ): MethodSpec.Builder = apply {
        addAnnotation(DAGGER_TYPE_INTO_MAP_JAVA_CLASS_NAME)
        addAnnotation(mapKeyAnnotation.javaAnnoSpec)
    }

    private fun MethodSpec.Builder.addQualifierAnnotationIfExists(
        qualifierAnnotation: XAnnotation?,
    ): MethodSpec.Builder = apply {
        if (qualifierAnnotation == null) return@apply

        addAnnotation(qualifierAnnotation.javaAnnoSpec)
    }

    private fun ReturnType.toTypeName(): TypeName {
        return when (this) {
            is ReturnType.Standard -> type.javaTypeName
            is ReturnType.Generic -> when (this) {
                is ReturnType.Generic.Parameterized -> type.javaTypeName
                is ReturnType.Generic.UnboundedWildcard -> {
                    val rawType = type.typeElement.javaClassName
                    val wildcards = List(typeParamCount) {
                        WildcardTypeName.subtypeOf(TypeName.OBJECT)
                    }

                    @Suppress("SpreadOperator")
                    ParameterizedTypeName.get(rawType, *wildcards.toTypedArray())
                }
            }
        }
    }
}
