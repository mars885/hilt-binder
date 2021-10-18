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

package com.paulrybitskyi.hiltbinder.processor.utils

import com.paulrybitskyi.hiltbinder.BindType.Collection
import com.paulrybitskyi.hiltbinder.BindType.Component
import com.paulrybitskyi.hiltbinder.common.utils.VOID_TYPE_QUALIFIED_NAME
import com.paulrybitskyi.hiltbinder.compiler.processing.XAnnotation
import com.paulrybitskyi.hiltbinder.compiler.processing.XProcessingEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.XType
import com.paulrybitskyi.hiltbinder.compiler.processing.XTypeElement

private const val BIND_ANNOTATION_PARAM_TO = "to"
private const val BIND_ANNOTATION_PARAM_INSTALL_IN = "installIn"
private const val BIND_ANNOTATION_PARAM_CUSTOM_COMPONENT = "customComponent"
private const val BIND_ANNOTATION_PARAM_CONTRIBUTES_TO = "contributesTo"
private const val BIND_ANNOTATION_PARAM_WITH_QUALIFIER = "withQualifier"

internal fun XProcessingEnv.getBindAnnotationDefaultType(): XType {
    return getTypeUnsafely(VOID_TYPE_QUALIFIED_NAME)
}

internal fun XTypeElement.getBindAnnotation(): XAnnotation {
    return checkNotNull(getAnnotation(BIND_TYPE_QUALIFIED_NAME))
}

internal fun XAnnotation.getToArg(default: XType? = null): XType? {
    return getTypeValue(BIND_ANNOTATION_PARAM_TO, default)
}

internal fun XAnnotation.getInstallInArg(): Component {
    return getEnumValue(
        BIND_ANNOTATION_PARAM_INSTALL_IN,
        Component::valueOf,
        Component.NONE
    )
}

internal fun XAnnotation.getCustomComponentArg(default: XType? = null): XType? {
    return getTypeValue(BIND_ANNOTATION_PARAM_CUSTOM_COMPONENT, default)
}

internal fun XAnnotation.getContributesToArg(): Collection {
    return getEnumValue(
        BIND_ANNOTATION_PARAM_CONTRIBUTES_TO,
        Collection::valueOf,
        Collection.NONE
    )
}

internal fun XAnnotation.getWithQualifierArg(): Boolean {
    return getBooleanValue(BIND_ANNOTATION_PARAM_WITH_QUALIFIER, false)
}
