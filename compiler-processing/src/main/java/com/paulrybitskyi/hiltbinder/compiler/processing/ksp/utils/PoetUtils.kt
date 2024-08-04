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

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.JavaAnnotationSpec
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.JavaClassName
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.JavaTypeName
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.KotlinAnnotationSpec
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.KotlinClassName
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.KotlinTypeName
import com.squareup.kotlinpoet.ksp.toAnnotationSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName

internal fun KSClassDeclaration.toKotlinClassName(): KotlinClassName {
    return toClassName()
}

internal fun KSType.toKotlinTypeName(): KotlinTypeName {
    return toTypeName()
}

internal fun KSAnnotation.toKotlinAnnoSpec(): KotlinAnnotationSpec {
    return toAnnotationSpec()
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
        "Converting KSP types to JavaPoet types is currently not supported.",
    )
}
