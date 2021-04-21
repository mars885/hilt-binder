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

import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.hiltbinder.processor.model.ContributionType
import com.paulrybitskyi.hiltbinder.processor.model.MAP_KEY_TYPE_CANON_NAME
import com.paulrybitskyi.hiltbinder.processor.parser.providers.MessageProvider
import com.paulrybitskyi.hiltbinder.processor.utils.HiltBinderException
import com.paulrybitskyi.hiltbinder.processor.utils.getType
import com.paulrybitskyi.hiltbinder.processor.utils.hasAnnotation
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

internal class ContributionTypeDetector(
    private val elementUtils: Elements,
    private val typeUtils: Types,
    private val messageProvider: MessageProvider
) {


    fun detectType(typeElement: TypeElement): ContributionType? {
        val mainAnnotation = typeElement.getAnnotation(BindType::class.java)

        return when(mainAnnotation.contributesTo) {
            BindType.Collection.NONE -> null
            BindType.Collection.SET -> ContributionType.Set
            BindType.Collection.MAP -> typeElement.createMapContributionType()
        }
    }


    private fun TypeElement.createMapContributionType(): ContributionType {
        val daggerMapKeyType = elementUtils.getType(MAP_KEY_TYPE_CANON_NAME)
        val mapKeyAnnotation = annotationMirrors
            .firstOrNull { it.annotationType.asElement().hasAnnotation(daggerMapKeyType, typeUtils) }
            ?: throw HiltBinderException(messageProvider.noMapKeyError(), this)

        return ContributionType.Map(mapKeyAnnotation)
    }


}