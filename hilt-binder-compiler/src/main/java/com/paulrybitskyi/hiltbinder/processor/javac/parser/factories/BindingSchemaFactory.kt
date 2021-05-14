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

package com.paulrybitskyi.hiltbinder.processor.javac.parser.factories

import com.paulrybitskyi.hiltbinder.processor.javac.model.BindingSchema
import com.paulrybitskyi.hiltbinder.processor.javac.parser.detectors.BindingReturnTypeDetector
import com.paulrybitskyi.hiltbinder.processor.javac.parser.detectors.ContributionTypeDetector
import com.paulrybitskyi.hiltbinder.processor.javac.parser.detectors.HiltComponentDetector
import com.paulrybitskyi.hiltbinder.processor.javac.parser.detectors.QualifierAnnotationDetector
import com.paulrybitskyi.hiltbinder.processor.javac.utils.getPackageName
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

internal class BindingSchemaFactory(
    private val elementUtils: Elements,
    private val hiltComponentDetector: HiltComponentDetector,
    private val contributionTypeDetector: ContributionTypeDetector,
    private val qualifierAnnotationDetector: QualifierAnnotationDetector,
    private val bindingReturnTypeDetector: BindingReturnTypeDetector,
    private val bindingMethodNameFactory: BindingMethodNameFactory
) {


    private companion object {

        private const val BINDING_PARAM_NAME = "binding"

    }


    fun createBindingSchema(annotatedElement: TypeElement): BindingSchema {
        val packageName = elementUtils.getPackageName(annotatedElement)
        val component = hiltComponentDetector.detectComponent(annotatedElement)
        val contributionType = contributionTypeDetector.detectType(annotatedElement)
        val qualifierAnnotation = qualifierAnnotationDetector.detectAnnotation(annotatedElement)
        val returnType = bindingReturnTypeDetector.detectReturnType(annotatedElement)
        val methodName = bindingMethodNameFactory.createMethodName(annotatedElement)

        return BindingSchema(
            packageName = packageName,
            component = component,
            contributionType = contributionType,
            qualifierAnnotation = qualifierAnnotation,
            methodName = methodName,
            paramType = annotatedElement,
            paramName = BINDING_PARAM_NAME,
            returnType = returnType
        )
    }


}