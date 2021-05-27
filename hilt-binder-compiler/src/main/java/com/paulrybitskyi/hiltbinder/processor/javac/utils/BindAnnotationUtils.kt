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

import com.paulrybitskyi.hiltbinder.BindType
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.Element
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Elements
import javax.lang.model.util.Types


private const val BIND_ANNOTATION_PARAM_TO = "to"
private const val BIND_ANNOTATION_PARAM_INSTALL_IN = "installIn"
private const val BIND_ANNOTATION_PARAM_CUSTOM_COMPONENT = "customComponent"
private const val BIND_ANNOTATION_PARAM_CONTRIBUTES_TO = "contributesTo"
private const val BIND_ANNOTATION_PARAM_WITH_QUALIFIER = "withQualifier"


internal fun Element.getBindAnnotation(
    elementUtils: Elements,
    typeUtils: Types
): AnnotationMirror {
    val bindAnnotationType = elementUtils.getType("com.paulrybitskyi.hiltbinder.BindType")
    val bindAnnotation = typeUtils.getAnnotation(this, bindAnnotationType)

    return checkNotNull(bindAnnotation)
}


internal fun AnnotationMirror.getToArg(): TypeMirror? {
    return getArgs()[BIND_ANNOTATION_PARAM_TO]?.cast()
}


internal fun AnnotationMirror.getInstallInArg(): BindType.Component {
    return try {
        BindType.Component.valueOf(getArgs()[BIND_ANNOTATION_PARAM_INSTALL_IN].toString())
    } catch(error: Throwable) {
        BindType.Component.NONE
    }
}


internal fun AnnotationMirror.getCustomComponentArg(): TypeMirror? {
    return getArgs()[BIND_ANNOTATION_PARAM_CUSTOM_COMPONENT]?.cast()
}


internal fun AnnotationMirror.getContributesToArg(): BindType.Collection {
    return try {
        BindType.Collection.valueOf(getArgs()[BIND_ANNOTATION_PARAM_CONTRIBUTES_TO].toString())
    } catch(error: Throwable) {
        BindType.Collection.NONE
    }
}


internal fun AnnotationMirror.getWithQualifierArg(): Boolean {
    return (getArgs()[BIND_ANNOTATION_PARAM_WITH_QUALIFIER]?.cast() ?: false)
}