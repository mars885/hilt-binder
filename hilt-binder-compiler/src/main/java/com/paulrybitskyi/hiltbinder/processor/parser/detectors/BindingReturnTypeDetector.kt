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

import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.hiltbinder.processor.model.OBJECT_TYPE_CANON_NAME
import com.paulrybitskyi.hiltbinder.processor.model.ReturnType
import com.paulrybitskyi.hiltbinder.processor.model.VOID_TYPE_CANON_NAME
import com.paulrybitskyi.hiltbinder.processor.parser.providers.MessageProvider
import com.paulrybitskyi.hiltbinder.processor.parser.HiltBinderException
import com.paulrybitskyi.hiltbinder.processor.utils.*
import com.paulrybitskyi.hiltbinder.processor.utils.asTypeElement
import com.paulrybitskyi.hiltbinder.processor.utils.getType
import com.paulrybitskyi.hiltbinder.processor.utils.getTypeSafely
import com.paulrybitskyi.hiltbinder.processor.utils.isGenericType
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


    fun detectReturnType(typeElement: TypeElement): ReturnType {
        val mainAnnotation = typeElement.getAnnotation(BindType::class.java)
        val bindingType = typeElement.asType()
        val returnType = (mainAnnotation.fetchExplicitReturnType(typeElement) ?: typeElement.deduceReturnType())

        checkSubtypeRelation(bindingType, returnType)

        return when {
            !typeUtils.isGenericType(returnType) -> ReturnType.Standard(returnType)
            (mainAnnotation.contributesTo == BindType.Collection.NONE) -> ReturnType.Generic.Parameterized(returnType)
            else -> {
                val typeParamCount = typeUtils.asTypeElement(returnType).typeParameters.size

                ReturnType.Generic.UnboundedWildcard(
                    type = returnType,
                    typeParamCount = typeParamCount
                )
            }
        }
    }


    private fun BindType.fetchExplicitReturnType(typeElement: TypeElement): TypeMirror? {
        if(!hasExplicitReturnType()) return null

        val explicitReturnType = elementUtils.getTypeSafely(::to)

        if(!typeUtils.isGenericType(explicitReturnType)) return explicitReturnType

        val parameterizedReturnType = findParameterizedReturnType(
            typeElement,
            typeUtils.getQualifiedName(explicitReturnType)
        )

        return (parameterizedReturnType ?: explicitReturnType)
    }


    private fun BindType.hasExplicitReturnType(): Boolean {
        val toFieldType = elementUtils.getTypeSafely(::to)
        val voidType = elementUtils.getType(VOID_TYPE_CANON_NAME)

        return !typeUtils.isSameType(toFieldType, voidType)
    }


    private fun findParameterizedReturnType(
        typeElement: TypeElement,
        parameterizedTypeName: String
    ): TypeMirror? {
        fun ArrayDeque<TypeMirror>.addTypeElement(typeElement: TypeElement) {
            if(typeElement.kind == ElementKind.CLASS) {
                add(typeElement.superclass)
            }

            addAll(typeElement.interfaces)
        }

        val possibleReturnTypes = ArrayDeque<TypeMirror>().apply {
            addTypeElement(typeElement)
        }
        val traversedPossibleReturnTypes = mutableSetOf<String>()
        val objectType = elementUtils.getType(OBJECT_TYPE_CANON_NAME)

        while(!possibleReturnTypes.isEmpty()) {
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


    private fun TypeElement.deduceReturnType(): TypeMirror {
        val hasSuperclass = (superclass != elementUtils.getType(OBJECT_TYPE_CANON_NAME))
        val hasInterfaces = interfaces.isNotEmpty()

        if(hasSuperclass && !hasInterfaces) return superclass
        if(!hasSuperclass && (interfaces.size == 1)) return interfaces.single()

        throw HiltBinderException(messageProvider.undefinedReturnTypeError(), this)
    }


    private fun checkSubtypeRelation(bindingType: TypeMirror, returnType: TypeMirror) {
        if(typeUtils.isSubtype(bindingType, returnType)) return

        val bindingTypeElement = typeUtils.asTypeElement(bindingType)
        val bindingTypeName = bindingTypeElement.qualifiedName.toString()
        val returnTypeName = typeUtils.getQualifiedName(returnType)
        val errorMessage = messageProvider.noSubtypeRelationError(
            bindingTypeName = bindingTypeName,
            returnTypeName = returnTypeName
        )

        throw HiltBinderException(errorMessage, bindingTypeElement)
    }


}