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

package com.paulrybitskyi.hiltbinder.processor.ksp.parser.detectors

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.hiltbinder.processor.ksp.model.ReturnType
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.HiltBinderException
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.providers.MessageProvider
import com.paulrybitskyi.hiltbinder.processor.ksp.utils.*

internal class BindingReturnTypeDetector(
    private val resolver: Resolver,
    private val messageProvider: MessageProvider
) {


    fun detectReturnType(annotatedSymbol: KSClassDeclaration): ReturnType {
        val bindAnnotation = resolver.getBindAnnotation(annotatedSymbol)
        val collection = bindAnnotation.getContributesToArg()
        val returnType = detectExplicitReturnType(bindAnnotation, annotatedSymbol)
            ?: inferReturnType(annotatedSymbol)

        checkSubtypeRelation(annotatedSymbol.asType(), returnType)

        return when {
            !returnType.isGenericType -> ReturnType.Standard(returnType)
            (collection == BindType.Collection.NONE) -> ReturnType.Generic.Parameterized(returnType)
            else -> ReturnType.Generic.UnboundedWildcard(
                type = returnType,
                typeParamCount = returnType.arguments.size
            )
        }
    }


    private fun detectExplicitReturnType(
        bindAnnotation: KSAnnotation,
        annotatedSymbol: KSClassDeclaration
    ): KSType? {
        if(!bindAnnotation.hasExplicitReturnType()) return null

        val explicitReturnType = checkNotNull(bindAnnotation.getToArg())

        if(!explicitReturnType.isGenericType) return explicitReturnType

        val parameterizedReturnType = findParameterizedReturnType(
            annotatedSymbol,
            explicitReturnType.qualifiedName
        )

        return (parameterizedReturnType ?: explicitReturnType)
    }


    private fun KSAnnotation.hasExplicitReturnType(): Boolean {
        val toParamType = (getToArg() ?: resolver.builtIns.nothingType)
        val nothingType = resolver.builtIns.nothingType

        return (toParamType != nothingType)
    }


    private fun findParameterizedReturnType(
        annotatedSymbol: KSClassDeclaration,
        parameterizedTypeName: String
    ): KSType? {
        fun MutableList<KSType>.addType(classDeclaration: KSClassDeclaration) {
            if(classDeclaration.classKind == ClassKind.CLASS) {
                classDeclaration.getSuperclass()?.let(::add)
            }

            addAll(classDeclaration.getInterfaces())
        }

        val possibleReturnTypes = mutableListOf<KSType>().apply {
            addType(annotatedSymbol)
        }
        val traversedPossibleReturnTypes = mutableSetOf<String>()
        val anyType = resolver.builtIns.anyType

        while(possibleReturnTypes.isNotEmpty()) {
            val possibleReturnType = possibleReturnTypes.removeFirst()
            val possibleReturnTypeClassDeclaration = possibleReturnType.classDeclaration
            val possibleReturnTypeName = possibleReturnTypeClassDeclaration.qualifiedNameStr

            when {
                (possibleReturnType == anyType) -> continue
                traversedPossibleReturnTypes.contains(possibleReturnTypeName) -> continue
                (possibleReturnTypeName == parameterizedTypeName) -> return possibleReturnType
                else -> {
                    traversedPossibleReturnTypes.add(possibleReturnTypeName)
                    possibleReturnTypes.addType(possibleReturnTypeClassDeclaration)
                }
            }
        }

        return null
    }


    private fun inferReturnType(annotatedSymbol: KSClassDeclaration): KSType {
        val superclass = annotatedSymbol.getSuperclass()
        val interfaces = annotatedSymbol.getInterfaces().toList()

        val hasSuperclass = (superclass != null)
        val hasInterfaces = interfaces.isNotEmpty()

        if(hasSuperclass && !hasInterfaces) return checkNotNull(superclass)
        if(!hasSuperclass && (interfaces.size == 1)) return interfaces.single()

        throw HiltBinderException(messageProvider.undefinedReturnTypeError(), annotatedSymbol)
    }


    private fun checkSubtypeRelation(bindingType: KSType, returnType: KSType) {
        if(returnType.isAssignableFrom(bindingType)) return

        val bindingTypeName = bindingType.qualifiedName
        val returnTypeName = returnType.qualifiedName
        val errorMessage = messageProvider.noSubtypeRelationError(
            bindingTypeName = bindingTypeName,
            returnTypeName = returnTypeName
        )

        throw HiltBinderException(errorMessage, bindingType.declaration)
    }


}