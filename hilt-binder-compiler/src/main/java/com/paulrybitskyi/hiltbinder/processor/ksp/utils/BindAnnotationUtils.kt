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

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSType
import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.hiltbinder.processor.common.BIND_TYPE_QUALIFIED_NAME
import com.paulrybitskyi.hiltbinder.processor.javac.utils.cast


private const val BIND_ANNOTATION_PARAM_TO = "to"
private const val BIND_ANNOTATION_PARAM_INSTALL_IN = "installIn"
private const val BIND_ANNOTATION_PARAM_CUSTOM_COMPONENT = "customComponent"
private const val BIND_ANNOTATION_PARAM_CONTRIBUTES_TO = "contributesTo"
private const val BIND_ANNOTATION_PARAM_WITH_QUALIFIER = "withQualifier"



internal fun Resolver.getBindAnnotation(symbol: KSAnnotated): KSAnnotation {
    val bindAnnotationType = getTypeByName(BIND_TYPE_QUALIFIED_NAME)
    val bindAnnotation = symbol.getAnnotation(bindAnnotationType)

    return checkNotNull(bindAnnotation)
}


internal fun KSAnnotation.getToArg(): KSType? {
    return args[BIND_ANNOTATION_PARAM_TO]?.cast()
}


internal fun KSAnnotation.getInstallInArg(): BindType.Component {
    val componentType = args[BIND_ANNOTATION_PARAM_INSTALL_IN]
        ?.cast<KSType>()
        ?: return BindType.Component.NONE

    val componentName = componentType.declaration.simpleName.asString()

    return BindType.Component.valueOf(componentName)
}


internal fun KSAnnotation.getCustomComponentArg(): KSType? {
    return args[BIND_ANNOTATION_PARAM_CUSTOM_COMPONENT]?.cast()
}


internal fun KSAnnotation.getContributesToArg(): BindType.Collection {
    val collectionType = args[BIND_ANNOTATION_PARAM_CONTRIBUTES_TO]
        ?.cast<KSType>()
        ?: return BindType.Collection.NONE

    val collectionName = collectionType.declaration.simpleName.asString()

    return BindType.Collection.valueOf(collectionName)
}


internal fun KSAnnotation.getWithQualifierArg(): Boolean {
    return (args[BIND_ANNOTATION_PARAM_WITH_QUALIFIER]?.cast() ?: false)
}