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

package com.paulrybitskyi.hiltbinder.processor.ksp.parser.detectors

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.paulrybitskyi.hiltbinder.processor.ksp.model.QUALIFIER_TYPE_CANON_NAME
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.HiltBinderException
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.providers.MessageProvider
import com.paulrybitskyi.hiltbinder.processor.ksp.utils.getAnnoMarkedWithAnotherAnno
import com.paulrybitskyi.hiltbinder.processor.ksp.utils.getBindAnnotation
import com.paulrybitskyi.hiltbinder.processor.ksp.utils.getTypeByName
import com.paulrybitskyi.hiltbinder.processor.ksp.utils.getWithQualifierArg

internal class QualifierAnnotationDetector(
    private val resolver: Resolver,
    private val messageProvider: MessageProvider
) {


    fun detectAnnotation(annotatedSymbol: KSClassDeclaration): KSAnnotation? {
        if(!resolver.getBindAnnotation(annotatedSymbol).getWithQualifierArg()) return null

        val qualifierType = resolver.getTypeByName(QUALIFIER_TYPE_CANON_NAME)

        return annotatedSymbol.getAnnoMarkedWithAnotherAnno(qualifierType)
            ?: throw HiltBinderException(messageProvider.qualifierAbsentError(), annotatedSymbol)
    }


}