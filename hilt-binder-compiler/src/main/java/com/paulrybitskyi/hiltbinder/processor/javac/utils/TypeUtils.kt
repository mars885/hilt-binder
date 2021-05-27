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

package com.paulrybitskyi.hiltbinder.processor.javac.utils

import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Types


internal fun AnnotationMirror.getArgs(): Map<String, Any?> {
    return elementValues
        .mapKeys { it.key.simpleName.toString() }
        .mapValues { it.value.value }
}


internal fun Types.hasAnnotation(element: Element, annotationType: TypeMirror): Boolean {
    return (getAnnotation(element, annotationType) != null)
}


internal fun Types.getAnnotation(element: Element, annotationType: TypeMirror): AnnotationMirror? {
    return element.annotationMirrors.firstOrNull {
        isSameType(it.annotationType, annotationType)
    }
}


internal fun Types.getAnnoMarkedWithSpecificAnno(
    element: Element,
    specificAnnoType: TypeMirror
): AnnotationMirror? {
    return element.annotationMirrors
        .firstOrNull { hasAnnotation(it.annotationType.asElement(), specificAnnoType) }
}


internal fun Types.asTypeElement(type: TypeMirror): TypeElement {
    return asElement(type).cast()
}


internal fun Types.isGenericType(type: TypeMirror): Boolean {
    return asTypeElement(type).typeParameters.isNotEmpty()
}


internal fun Types.getQualifiedName(type: TypeMirror): String {
    return asTypeElement(type).getQualifiedNameStr()
}