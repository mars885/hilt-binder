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

package com.paulrybitskyi.hiltbinder.processor.parser.detectors

import com.paulrybitskyi.hiltbinder.BindType.Collection
import com.paulrybitskyi.hiltbinder.compiler.processing.XAnnotation
import com.paulrybitskyi.hiltbinder.compiler.processing.XProcessingEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.XType
import com.paulrybitskyi.hiltbinder.compiler.processing.XTypeElement
import com.paulrybitskyi.hiltbinder.keys.MapClassKey
import com.paulrybitskyi.hiltbinder.processor.model.ContributionType
import com.paulrybitskyi.hiltbinder.processor.model.MAP_KEY_TYPE_QUALIFIED_NAME
import com.paulrybitskyi.hiltbinder.processor.parser.HiltBinderException
import com.paulrybitskyi.hiltbinder.processor.parser.providers.MessageProvider
import com.paulrybitskyi.hiltbinder.processor.utils.*

private val MAP_CLASS_QUALIFIED_NAME = MapClassKey::class.qualifiedName!!
private val MAP_CLASS_AUTO_QUALIFIED_NAME = MapClassKey.Auto::class.qualifiedName!!
private val MAP_CLASS_PARAM_VALUE = MapClassKey::value.name

internal class ContributionTypeDetector(
    private val processingEnv: XProcessingEnv,
    private val messageProvider: MessageProvider
) {

    fun detectType(annotatedElement: XTypeElement): ContributionType? {
        val bindAnnotation = annotatedElement.getBindAnnotation()
        val collection = bindAnnotation.getContributesToArg()

        return when (collection) {
            Collection.NONE -> null
            Collection.SET -> ContributionType.Set
            Collection.MAP -> annotatedElement.createMapContributionType()
        }
    }

    private fun XTypeElement.createMapContributionType(): ContributionType {
        val daggerMapKeyType = processingEnv.getTypeUnsafely(MAP_KEY_TYPE_QUALIFIED_NAME)
        val mapKeyAnnotation = wrapKeyAnnotation(getAnnoMarkedWithAnotherAnno(daggerMapKeyType))
            ?: throw HiltBinderException(messageProvider.noMapKeyError(), this)

        return ContributionType.Map(mapKeyAnnotation)
    }

    private fun XTypeElement.wrapKeyAnnotation(annotation: XAnnotation?): XAnnotation? {
        annotation ?: return null

        if (annotation.type.element.qualifiedName == MAP_CLASS_QUALIFIED_NAME) {
            val value = annotation.getTypeValue(MAP_CLASS_PARAM_VALUE, null)
            if (value == null || value.element.qualifiedName == MAP_CLASS_AUTO_QUALIFIED_NAME) {
                return XMapClassKeyWrapper(annotation, type)
            }
        }
        return annotation
    }

    private class XMapClassKeyWrapper(
        private val annotation: XAnnotation,
        private val keyType: XType,
    ) : XAnnotation by annotation {

        override val javaAnnoSpec get() = wrapJavaAnnoSpec()

        override val kotlinAnnoSpec get() = wrapKotlinAnnoSpec()

        private fun wrapJavaAnnoSpec() = annotation.javaAnnoSpec.toBuilder().apply {
            members.clear()
            addMember(MAP_CLASS_PARAM_VALUE, "\$T.class", keyType.javaTypeName)
        }.build()

        private fun wrapKotlinAnnoSpec() = annotation.kotlinAnnoSpec.toBuilder().apply {
            members.clear()
            addMember("%T::class", keyType.kotlinTypeName)
        }.build()
    }
}
