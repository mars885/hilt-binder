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

package com.paulrybitskyi.hiltbinder.processor.ksp.utils

import com.google.devtools.ksp.symbol.*
import com.paulrybitskyi.hiltbinder.processor.javac.utils.cast


internal val KSType.isGenericType: Boolean
    get() = arguments.isNotEmpty()

internal val KSType.simpleName: String
    get() = declaration.simpleNameStr

internal val KSType.qualifiedName: String
    get() = declaration.qualifiedNameStr

internal val KSType.classDeclaration: KSClassDeclaration
    get() = declaration.cast()

internal val KSAnnotation.args: Map<String, Any?>
    get() = arguments
        .filter { it.name != null }
        .associate {it.name!!.asString() to it.value }

internal val KSAnnotation.declaration: KSDeclaration
    get() = annotationType.resolve().declaration

internal val KSDeclaration.simpleNameStr: String
    get() = simpleName.asString()

internal val KSDeclaration.qualifiedNameStr: String
    get() = checkNotNull(qualifiedName).asString()

//TODO to be removed once KSP fixes this
internal val KSDeclaration.validPackageName: String
    get() = packageName.asString().let {
        if(it == "<root>") "" else it
    }


internal fun KSAnnotated.hasAnnotation(annotationType: KSType): Boolean {
    return (getAnnotation(annotationType) != null)
}


internal fun KSAnnotated.getAnnotation(annotationType: KSType): KSAnnotation? {
    return annotations.firstOrNull {
        it.annotationType.resolve() == annotationType
    }
}


internal fun KSAnnotated.getAnnoMarkedWithAnotherAnno(
    anotherAnnoType: KSType
): KSAnnotation? {
    return annotations.firstOrNull {
        it.annotationType.resolve().declaration.hasAnnotation(anotherAnnoType)
    }
}


internal fun KSClassDeclaration.getSuperclass(): KSType? {
    return superTypes
        .firstOrNull { it.resolve().classDeclaration.classKind == ClassKind.CLASS }
        ?.resolve()
}


internal fun KSClassDeclaration.getInterfaces(): Sequence<KSType> {
    return superTypes
        .filter { it.resolve().classDeclaration.classKind == ClassKind.INTERFACE }
        .map(KSTypeReference::resolve)
}


internal fun KSClassDeclaration.asType(): KSType {
    return asType(emptyList())
}