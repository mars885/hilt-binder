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

import com.paulrybitskyi.hiltbinder.common.utils.JAVA_ENUM_TYPE_QUALIFIED_NAME
import com.paulrybitskyi.hiltbinder.common.utils.OBJECT_TYPE_QUALIFIED_NAME
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Elements

internal val Element.simpleNameStr: String
    get() = simpleName.toString()

internal val TypeElement.qualifiedNameStr: String
    get() = qualifiedName.toString()

internal fun Elements.getType(qualifiedName: CharSequence): TypeMirror? {
    return getTypeElement(qualifiedName)?.asType()
}

internal fun Elements.getPackageName(type: Element): String {
    return getPackageOf(type).qualifiedName.toString()
}

internal fun Elements.getSuperclass(typeElement: TypeElement): TypeMirror? {
    return typeElement.superclass
        ?.let { type ->
            val isValidSuperclass = (
                (type.kind != TypeKind.NONE) &&
                (type != getType(OBJECT_TYPE_QUALIFIED_NAME)) &&
                !type.toString().startsWith(JAVA_ENUM_TYPE_QUALIFIED_NAME)
            )

            if (isValidSuperclass) type else null
        }
}
