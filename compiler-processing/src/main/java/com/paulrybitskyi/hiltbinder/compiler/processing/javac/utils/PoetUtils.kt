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

package com.paulrybitskyi.hiltbinder.compiler.processing.javac.utils

import com.paulrybitskyi.hiltbinder.compiler.processing.utils.JavaAnnotationSpec
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.JavaClassName
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.JavaTypeName
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.KotlinAnnotationSpec
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.KotlinClassName
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.KotlinTypeName
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror


private fun throwUnsupportedOpError(): Nothing {
    throw UnsupportedOperationException(
        "Converting Javac types to KotlinPoet types is currently not supported."
    )
}


internal fun TypeElement.toJavaClassName(): JavaClassName {
    return JavaClassName.get(this)
}

internal fun TypeMirror.toJavaTypeName(): JavaTypeName {
    return JavaTypeName.get(this)
}

internal fun AnnotationMirror.toJavaAnnoSpec(): JavaAnnotationSpec {
    return JavaAnnotationSpec.get(this)
}


internal fun TypeElement.toKotlinClassName(): KotlinClassName {
    throwUnsupportedOpError()
}

internal fun TypeMirror.toKotlinTypeName(): KotlinTypeName {
    throwUnsupportedOpError()
}

internal fun AnnotationMirror.toKotlinAnnoSpec(): KotlinAnnotationSpec {
    throwUnsupportedOpError()
}