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

package com.paulrybitskyi.hiltbinder.processor.javac.parser.detectors

import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.hiltbinder.processor.javac.model.OBJECT_TYPE_CANON_NAME
import com.paulrybitskyi.hiltbinder.processor.javac.model.ReturnType
import com.paulrybitskyi.hiltbinder.processor.javac.model.VOID_TYPE_CANON_NAME
import com.paulrybitskyi.hiltbinder.processor.javac.parser.HiltBinderException
import com.paulrybitskyi.hiltbinder.processor.javac.parser.providers.MessageProvider
import com.paulrybitskyi.hiltbinder.processor.javac.utils.*
import com.paulrybitskyi.hiltbinder.processor.javac.utils.asTypeElement
import com.paulrybitskyi.hiltbinder.processor.javac.utils.getQualifiedName
import com.paulrybitskyi.hiltbinder.processor.javac.utils.getTypeSafely
import com.paulrybitskyi.hiltbinder.processor.javac.utils.isGenericType
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

internal class BindingReturnTypeDetector(
    private val elementUtils: Elements,
    private val typeUtils: Types,
    private val messageProvider: MessageProvider
) {


    fun detectReturnType(annotatedElement: TypeElement): ReturnType {
        val bindAnnotation = annotatedElement.getAnnotation(BindType::class.java)
        val returnType = detectExplicitReturnType(bindAnnotation, annotatedElement)
            ?: inferReturnType(annotatedElement)

        checkSubtypeRelation(annotatedElement.asType(), returnType)

        return when {
            !typeUtils.isGenericType(returnType) -> ReturnType.Standard(returnType)
            (bindAnnotation.contributesTo == BindType.Collection.NONE) -> ReturnType.Generic.Parameterized(returnType)
            else -> ReturnType.Generic.UnboundedWildcard(
                type = returnType,
                typeParamCount = typeUtils.asTypeElement(returnType).typeParameters.size
            )
        }
    }


    private fun detectExplicitReturnType(
        bindAnnotation: BindType,
        annotatedElement: TypeElement
    ): TypeMirror? {
        if(!bindAnnotation.hasExplicitReturnType()) return null

        val explicitReturnType = elementUtils.getTypeSafely(bindAnnotation::to)

        if(!typeUtils.isGenericType(explicitReturnType)) return explicitReturnType

        val parameterizedReturnType = findParameterizedReturnType(
            annotatedElement,
            typeUtils.getQualifiedName(explicitReturnType)
        )

        return (parameterizedReturnType ?: explicitReturnType)
    }


    private fun BindType.hasExplicitReturnType(): Boolean {
        val toParamType = elementUtils.getTypeSafely(::to)
        val voidType = elementUtils.getType(VOID_TYPE_CANON_NAME)

        return !typeUtils.isSameType(toParamType, voidType)
    }


    private fun findParameterizedReturnType(
        annotatedElement: TypeElement,
        parameterizedTypeName: String
    ): TypeMirror? {
        fun MutableList<TypeMirror>.addTypeElement(typeElement: TypeElement) {
            if(typeElement.kind == ElementKind.CLASS) {
                add(typeElement.superclass)
            }

            addAll(typeElement.interfaces)
        }

        val possibleReturnTypes = mutableListOf<TypeMirror>().apply {
            addTypeElement(annotatedElement)
        }
        val traversedPossibleReturnTypes = mutableSetOf<String>()
        val objectType = elementUtils.getType(OBJECT_TYPE_CANON_NAME)

        while(possibleReturnTypes.isNotEmpty()) {
            val possibleReturnType = possibleReturnTypes.removeFirst()
            val possibleReturnTypeName = typeUtils.getQualifiedName(possibleReturnType)

            when {
                typeUtils.isSameType(possibleReturnType, objectType) -> continue
                traversedPossibleReturnTypes.contains(possibleReturnTypeName) -> continue
                (possibleReturnTypeName == parameterizedTypeName) -> return possibleReturnType
                else -> {
                    traversedPossibleReturnTypes.add(possibleReturnTypeName)
                    possibleReturnTypes.addTypeElement(typeUtils.asTypeElement(possibleReturnType))
                }
            }
        }

        return null
    }


    private fun inferReturnType(annotatedElement: TypeElement): TypeMirror {
        val superclass = annotatedElement.superclass
        val interfaces = annotatedElement.interfaces

        val hasSuperclass = (superclass != elementUtils.getType(OBJECT_TYPE_CANON_NAME))
        val hasInterfaces = interfaces.isNotEmpty()

        if(hasSuperclass && !hasInterfaces) return superclass
        if(!hasSuperclass && (interfaces.size == 1)) return interfaces.single()

        throw HiltBinderException(messageProvider.undefinedReturnTypeError(), annotatedElement)
    }


    private fun checkSubtypeRelation(bindingType: TypeMirror, returnType: TypeMirror) {
        if(typeUtils.isSubtype(bindingType, returnType)) return

        val bindingTypeElement = typeUtils.asTypeElement(bindingType)
        val bindingTypeName = bindingTypeElement.getQualifiedNameStr()
        val returnTypeName = typeUtils.getQualifiedName(returnType)
        val errorMessage = messageProvider.noSubtypeRelationError(
            bindingTypeName = bindingTypeName,
            returnTypeName = returnTypeName
        )

        throw HiltBinderException(errorMessage, bindingTypeElement)
    }


}