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

package com.paulrybitskyi.hiltbinder.processor.ksp.parser.factories

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.paulrybitskyi.hiltbinder.processor.ksp.model.BindingSchema
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.detectors.BindingReturnTypeDetector
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.detectors.ContributionTypeDetector
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.detectors.HiltComponentDetector
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.detectors.QualifierAnnotationDetector
import com.paulrybitskyi.hiltbinder.processor.ksp.utils.validPackageName

internal class BindingSchemaFactory(
    private val hiltComponentDetector: HiltComponentDetector,
    private val contributionTypeDetector: ContributionTypeDetector,
    private val qualifierAnnotationDetector: QualifierAnnotationDetector,
    private val bindingReturnTypeDetector: BindingReturnTypeDetector,
    private val bindingMethodNameFactory: BindingMethodNameFactory
) {


    private companion object {

        private const val BINDING_PARAM_NAME = "binding"

    }


    fun createBindingSchema(annotatedSymbol: KSClassDeclaration): BindingSchema {
        val packageName = annotatedSymbol.validPackageName
        val component = hiltComponentDetector.detectComponent(annotatedSymbol)
        val contributionType = contributionTypeDetector.detectType(annotatedSymbol)
        val qualifierAnnotation = qualifierAnnotationDetector.detectAnnotation(annotatedSymbol)
        val returnType = bindingReturnTypeDetector.detectReturnType(annotatedSymbol)
        val methodName = bindingMethodNameFactory.createMethodName(annotatedSymbol)

        return BindingSchema(
            packageName = packageName,
            component = component,
            contributionType = contributionType,
            qualifierAnnotation = qualifierAnnotation,
            methodName = methodName,
            paramType = annotatedSymbol,
            paramName = BINDING_PARAM_NAME,
            returnType = returnType
        )
    }


}