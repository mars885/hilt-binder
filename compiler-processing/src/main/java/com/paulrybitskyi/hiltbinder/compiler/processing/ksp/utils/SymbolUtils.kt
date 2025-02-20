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

package com.paulrybitskyi.hiltbinder.compiler.processing.ksp.utils

import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSTypeReference
import com.google.devtools.ksp.symbol.Nullability
import com.paulrybitskyi.hiltbinder.common.utils.KOTLIN_ENUM_TYPE_QUALIFIED_NAME
import com.paulrybitskyi.hiltbinder.common.utils.unsafeCast

internal val KSType.simpleName: String
    get() = declaration.simpleNameStr

internal val KSType.qualifiedName: String
    get() = declaration.qualifiedNameStr

internal val KSType.classDeclaration: KSClassDeclaration
    get() = declaration.unsafeCast()

internal val KSDeclaration.simpleNameStr: String
    get() = simpleName.asString()

internal val KSDeclaration.qualifiedNameStr: String
    get() = checkNotNull(qualifiedName).asString()

internal val KSClassDeclaration.isClass: Boolean
    get() = classKind in listOf(
        ClassKind.CLASS,
        ClassKind.ENUM_CLASS,
        ClassKind.OBJECT,
    )

internal val KSDeclaration.packageNameStr: String
    get() = packageName.asString()

internal fun KSAnnotated.getAnnotation(annotationType: KSType): KSAnnotation? {
    return annotations.firstOrNull {
        it.annotationType.resolve() == annotationType
    }
}

internal fun KSClassDeclaration.getSuperclass(anyType: KSType): KSType? {
    return superTypes
        .withNotNullablePlatformTypes()
        .firstOrNull {
            it.classDeclaration.isClass &&
            (it != anyType) &&
            !it.qualifiedName.startsWith(KOTLIN_ENUM_TYPE_QUALIFIED_NAME)
        }
}

internal fun KSClassDeclaration.getInterfaces(): Sequence<KSType> {
    return superTypes
        .withNotNullablePlatformTypes()
        .filter { it.classDeclaration.classKind == ClassKind.INTERFACE }
}

private fun Sequence<KSTypeReference>.withNotNullablePlatformTypes(): Sequence<KSType> {
    return map { typeReference ->
        val type = typeReference.resolve()

        when (type.nullability) {
            Nullability.PLATFORM -> type.makeNotNullable()
            else -> type
        }
    }
}

internal fun KSClassDeclaration.asType(): KSType {
    return asType(emptyList())
}
