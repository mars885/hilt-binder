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

package com.paulrybitskyi.hiltbinder.compiler.processing.ksp.utils

import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSTypeArgument
import com.google.devtools.ksp.symbol.Variance
import com.google.devtools.ksp.visitor.KSEmptyVisitor
import com.paulrybitskyi.hiltbinder.common.utils.PACKAGE_SEPARATOR
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.JavaAnnotationSpec
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.JavaClassName
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.JavaTypeName
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.KotlinAnnotationSpec
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.KotlinClassName
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.KotlinTypeName
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.STAR
import com.squareup.kotlinpoet.TypeName

private const val INNER_TYPE_SEPARATOR = '.'
private const val ANNOTATION_INDICATOR_SYMBOL = '@'

internal fun KSClassDeclaration.toKotlinClassName(): KotlinClassName {
    val packageName = packageNameStr
    val typesStr = qualifiedNameStr.removePrefix("$packageName$PACKAGE_SEPARATOR")
    val simpleNames = typesStr.split(INNER_TYPE_SEPARATOR)

    return ClassName(packageName, simpleNames)
}

internal fun KSType.toKotlinTypeName(): KotlinTypeName {
    return toKotlinTypeName(mutableListOf())
}

private fun KSType.toKotlinTypeName(typeArguments: MutableList<TypeName>): KotlinTypeName {
    val className = classDeclaration.toKotlinClassName()

    if (arguments.isEmpty()) return className

    for (argument in arguments) {
        typeArguments.add(argument.toKotlinTypeName())
    }

    return className.parameterizedBy(typeArguments)
}

private fun KSTypeArgument.toKotlinTypeName(): KotlinTypeName {
    return accept(
        object : KSEmptyVisitor<Unit, TypeName>() {

            override fun visitTypeArgument(typeArgument: KSTypeArgument, data: Unit): TypeName {
                return when {
                    (typeArgument.variance == Variance.STAR) -> STAR
                    else -> typeArgument.type?.resolve()
                        ?.toKotlinTypeName()
                        ?: error("Cannot retrieve a type of the type argument = $typeArgument.")
                }
            }

            override fun defaultHandler(node: KSNode, data: Unit): TypeName {
                throw IllegalArgumentException("Unexpected node: $node.")
            }
        },
        Unit
    )
}

internal fun KSAnnotation.toKotlinAnnoSpec(): KotlinAnnotationSpec {
    val className = annotationType.resolve().classDeclaration.toKotlinClassName()
    val annotationSpec = AnnotationSpec.builder(className)

    for (argument in arguments) {
        val argValue = (argument.value ?: continue)
        val argName = argument.name?.asString()
        val adjustedArgName = when {
            ((argName == null) || (arguments.size == 1)) -> ""
            else -> "$argName = "
        }

        annotationSpec.addMember(argValue.toCodeBlock(adjustedArgName))
    }

    return annotationSpec.build()
}

private fun Any.toCodeBlock(argName: String = ""): CodeBlock {
    return when (this) {
        is Char -> CodeBlock.create(argName, "'%L'", this)
        is Float -> CodeBlock.create(argName, "%Lf", this)
        is String -> CodeBlock.create(argName, "%S", this)
        is KSType -> toCodeBlock(argName)
        is KSAnnotation -> CodeBlock.create(argName, "%L", toKotlinAnnoSpec())
        is ArrayList<*> -> toCodeBlock(argName)
        else -> CodeBlock.create(argName, "%L", this)
    }
}

private fun KSType.toCodeBlock(argName: String): CodeBlock {
    val argValueDeclaration = classDeclaration
    val argValueClassName = argValueDeclaration.toKotlinClassName()
    val argValueClassKind = argValueDeclaration.classKind
    val argValueFormat = when (argValueClassKind) {
        ClassKind.ENUM_ENTRY -> "%T"
        else -> "%T::class"
    }

    return CodeBlock.create(argName, argValueFormat, argValueClassName)
}

private fun ArrayList<*>.toCodeBlock(argName: String): CodeBlock {
    val members = mutableListOf<String>()

    for (member in this) {
        val codeBlock = member.toCodeBlock()
            .toString()
            .let { codeStr ->
                if (member is KSAnnotation) {
                    // Annotations wrapped in arrays/lists should
                    // not start with @ symbol
                    codeStr.trimStart(ANNOTATION_INDICATOR_SYMBOL)
                } else {
                    codeStr
                }
            }

        members.add(codeBlock)
    }

    return CodeBlock.create(argName, "%L", members)
}

private fun CodeBlock.Companion.create(
    argName: String,
    format: String,
    vararg args: Any?
): CodeBlock {
    return of("$argName$format", *args)
}

internal fun KSClassDeclaration.toJavaClassName(): JavaClassName {
    throwUnsupportedOpError()
}

internal fun KSType.toJavaTypeName(): JavaTypeName {
    throwUnsupportedOpError()
}

internal fun KSAnnotation.toJavaAnnoSpec(): JavaAnnotationSpec {
    throwUnsupportedOpError()
}

private fun throwUnsupportedOpError(): Nothing {
    throw UnsupportedOperationException(
        "Converting KSP types to JavaPoet types is currently not supported."
    )
}
