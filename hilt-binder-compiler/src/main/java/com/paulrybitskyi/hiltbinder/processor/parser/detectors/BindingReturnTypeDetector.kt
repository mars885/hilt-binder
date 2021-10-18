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

package com.paulrybitskyi.hiltbinder.processor.parser.detectors

import com.paulrybitskyi.hiltbinder.BindType.Collection
import com.paulrybitskyi.hiltbinder.compiler.processing.XAnnotation
import com.paulrybitskyi.hiltbinder.compiler.processing.XBackend
import com.paulrybitskyi.hiltbinder.compiler.processing.XProcessingEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.XType
import com.paulrybitskyi.hiltbinder.compiler.processing.XTypeElement
import com.paulrybitskyi.hiltbinder.processor.model.ReturnType
import com.paulrybitskyi.hiltbinder.processor.parser.HiltBinderException
import com.paulrybitskyi.hiltbinder.processor.parser.providers.MessageProvider
import com.paulrybitskyi.hiltbinder.processor.utils.getBindAnnotation
import com.paulrybitskyi.hiltbinder.processor.utils.getBindAnnotationDefaultType
import com.paulrybitskyi.hiltbinder.processor.utils.getContributesToArg
import com.paulrybitskyi.hiltbinder.processor.utils.getRootType
import com.paulrybitskyi.hiltbinder.processor.utils.getToArg
import com.paulrybitskyi.hiltbinder.processor.utils.isGeneric
import com.paulrybitskyi.hiltbinder.processor.utils.qualifiedName
import com.paulrybitskyi.hiltbinder.processor.utils.typeElement

internal class BindingReturnTypeDetector(
    private val processingEnv: XProcessingEnv,
    private val messageProvider: MessageProvider
) {

    fun detectReturnType(annotatedElement: XTypeElement): ReturnType {
        val bindAnnotation = annotatedElement.getBindAnnotation()
        val collection = bindAnnotation.getContributesToArg()
        val returnType = retrieveReturnType(bindAnnotation, annotatedElement)

        checkSubtypeRelation(annotatedElement.type, returnType)

        return when {
            !returnType.isGeneric -> ReturnType.Standard(returnType)
            (collection == Collection.NONE) -> ReturnType.Generic.Parameterized(returnType)
            else -> ReturnType.Generic.UnboundedWildcard(
                type = returnType,
                typeParamCount = returnType.element.typeParameterCount
            )
        }
    }

    private fun retrieveReturnType(
        bindAnnotation: XAnnotation,
        annotatedElement: XTypeElement
    ): XType {
        val returnType = detectExplicitReturnType(bindAnnotation, annotatedElement)
            ?: inferReturnType(annotatedElement)

        return returnType.let { type ->
            val shouldMapJavaRootTypeToKotlin = (
                (processingEnv.backend == XBackend.KSP) &&
                (type == processingEnv.getRootType(XBackend.JAVAC))
            )

            if (shouldMapJavaRootTypeToKotlin) {
                processingEnv.getRootType(XBackend.KSP)
            } else {
                type
            }
        }
    }

    private fun detectExplicitReturnType(
        bindAnnotation: XAnnotation,
        annotatedElement: XTypeElement
    ): XType? {
        if (!bindAnnotation.hasExplicitReturnType()) return null

        val explicitReturnType = checkNotNull(bindAnnotation.getToArg())

        if (!explicitReturnType.isGeneric) return explicitReturnType

        val parameterizedReturnType = findParameterizedReturnType(
            annotatedElement,
            explicitReturnType.qualifiedName
        )

        return (parameterizedReturnType ?: explicitReturnType)
    }

    private fun XAnnotation.hasExplicitReturnType(): Boolean {
        val defaultType = processingEnv.getBindAnnotationDefaultType()
        val toParamType = getToArg(defaultType)

        return (toParamType != defaultType)
    }

    private fun findParameterizedReturnType(
        annotatedElement: XTypeElement,
        parameterizedTypeName: String
    ): XType? {
        fun MutableList<XType>.addTypeElementSuperTypes(typeElement: XTypeElement) {
            if (typeElement.isClass) {
                typeElement.superclass?.let(::add)
            }

            addAll(typeElement.interfaces)
        }

        val possibleReturnTypes = mutableListOf<XType>().apply {
            addTypeElementSuperTypes(annotatedElement)
        }
        val traversedPossibleReturnTypes = mutableSetOf<String>()
        val rootType = processingEnv.getRootType()

        @Suppress("LoopWithTooManyJumpStatements")
        while (possibleReturnTypes.isNotEmpty()) {
            val possibleReturnType = possibleReturnTypes.removeFirst()
            val possibleReturnTypeElement = possibleReturnType.typeElement
            val possibleReturnTypeName = possibleReturnTypeElement.qualifiedName

            when {
                (possibleReturnType == rootType) -> continue
                traversedPossibleReturnTypes.contains(possibleReturnTypeName) -> continue
                (possibleReturnTypeName == parameterizedTypeName) -> return possibleReturnType
                else -> {
                    traversedPossibleReturnTypes.add(possibleReturnTypeName)
                    possibleReturnTypes.addTypeElementSuperTypes(possibleReturnTypeElement)
                }
            }
        }

        return null
    }

    private fun inferReturnType(annotatedElement: XTypeElement): XType {
        val superclass = annotatedElement.superclass
        val interfaces = annotatedElement.interfaces

        val hasSuperclass = (superclass != null)
        val hasInterfaces = interfaces.isNotEmpty()

        if (hasSuperclass && !hasInterfaces) return checkNotNull(superclass)
        if (!hasSuperclass && (interfaces.size == 1)) return interfaces.single()

        throw HiltBinderException(messageProvider.undefinedReturnTypeError(), annotatedElement)
    }

    private fun checkSubtypeRelation(bindingType: XType, returnType: XType) {
        if (returnType.isAssignableFrom(bindingType)) return

        val bindingTypeName = bindingType.qualifiedName
        val returnTypeName = returnType.qualifiedName
        val errorMessage = messageProvider.noSubtypeRelationError(
            bindingTypeName = bindingTypeName,
            returnTypeName = returnTypeName
        )

        throw HiltBinderException(errorMessage, bindingType.element)
    }
}
