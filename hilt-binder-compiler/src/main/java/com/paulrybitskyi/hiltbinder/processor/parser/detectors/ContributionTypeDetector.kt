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
import com.paulrybitskyi.hiltbinder.compiler.processing.XProcessingEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.XTypeElement
import com.paulrybitskyi.hiltbinder.processor.model.ContributionType
import com.paulrybitskyi.hiltbinder.processor.model.MAP_KEY_TYPE_QUALIFIED_NAME
import com.paulrybitskyi.hiltbinder.processor.parser.HiltBinderException
import com.paulrybitskyi.hiltbinder.processor.parser.providers.MessageProvider
import com.paulrybitskyi.hiltbinder.processor.utils.getAnnoMarkedWithAnotherAnno
import com.paulrybitskyi.hiltbinder.processor.utils.getBindAnnotation
import com.paulrybitskyi.hiltbinder.processor.utils.getContributesToArg
import com.paulrybitskyi.hiltbinder.processor.utils.getTypeUnsafely

internal class ContributionTypeDetector(
    private val processingEnv: XProcessingEnv,
    private val messageProvider: MessageProvider
) {


    fun detectType(annotatedElement: XTypeElement): ContributionType? {
        val bindAnnotation = annotatedElement.getBindAnnotation()
        val collection = bindAnnotation.getContributesToArg()

        return when(collection) {
            Collection.NONE -> null
            Collection.SET -> ContributionType.Set
            Collection.MAP -> annotatedElement.createMapContributionType()
        }
    }


    private fun XTypeElement.createMapContributionType(): ContributionType {
        val daggerMapKeyType = processingEnv.getTypeUnsafely(MAP_KEY_TYPE_QUALIFIED_NAME)
        val mapKeyAnnotation = getAnnoMarkedWithAnotherAnno(daggerMapKeyType)
            ?: throw HiltBinderException(messageProvider.noMapKeyError(), this)

        return ContributionType.Map(mapKeyAnnotation)
    }


}
