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

package com.paulrybitskyi.hiltbinder.processor.javac.parser

import com.paulrybitskyi.hiltbinder.processor.javac.parser.detectors.BindingReturnTypeDetector
import com.paulrybitskyi.hiltbinder.processor.javac.parser.detectors.ContributionTypeDetector
import com.paulrybitskyi.hiltbinder.processor.javac.parser.detectors.HiltComponentDetector
import com.paulrybitskyi.hiltbinder.processor.javac.parser.detectors.QualifierAnnotationDetector
import com.paulrybitskyi.hiltbinder.processor.javac.parser.factories.BindingMethodNameFactory
import com.paulrybitskyi.hiltbinder.processor.javac.parser.factories.BindingSchemaFactory
import com.paulrybitskyi.hiltbinder.processor.javac.parser.factories.ModuleInterfaceNameFactory
import com.paulrybitskyi.hiltbinder.processor.javac.parser.factories.ModuleSchemaFactory
import com.paulrybitskyi.hiltbinder.processor.javac.parser.providers.MessageProvider
import com.paulrybitskyi.hiltbinder.processor.javac.parser.providers.PackageNameProvider
import javax.annotation.processing.ProcessingEnvironment

internal object AnnotationsParserFactory {


    fun create(env: ProcessingEnvironment): AnnotationsParser {
        return AnnotationsParser(
            bindingSchemaFactory = createBindingSchemaFactory(env),
            moduleSchemaFactory = createModuleSchemaFactory(env),
            packageNameProvider = createPackageNameProvider()
        )
    }


    private fun createBindingSchemaFactory(env: ProcessingEnvironment): BindingSchemaFactory {
        return BindingSchemaFactory(
            elementUtils = env.elementUtils,
            hiltComponentDetector = createHiltComponentDetector(env),
            contributionTypeDetector = createContributionTypeDetector(env),
            qualifierAnnotationDetector = createQualifierAnnotationDetector(env),
            bindingReturnTypeDetector = createBindingReturnTypeDetector(env),
            bindingMethodNameFactory = createBindingMethodNameFactory()
        )
    }


    private fun createHiltComponentDetector(env: ProcessingEnvironment): HiltComponentDetector {
        return HiltComponentDetector(
            elementUtils = env.elementUtils,
            typeUtils = env.typeUtils,
            predefinedHiltComponentMapper = createPredefinedHiltComponentMapper(),
            messageProvider = createMessageProvider()
        )
    }


    private fun createPredefinedHiltComponentMapper(): PredefinedHiltComponentMapper {
        return PredefinedHiltComponentMapper()
    }


    private fun createMessageProvider(): MessageProvider {
        return MessageProvider()
    }


    private fun createContributionTypeDetector(env: ProcessingEnvironment): ContributionTypeDetector {
        return ContributionTypeDetector(
            elementUtils = env.elementUtils,
            typeUtils = env.typeUtils,
            messageProvider = createMessageProvider()
        )
    }


    private fun createQualifierAnnotationDetector(env: ProcessingEnvironment): QualifierAnnotationDetector {
        return QualifierAnnotationDetector(
            elementUtils = env.elementUtils,
            typeUtils = env.typeUtils,
            messageProvider = createMessageProvider()
        )
    }


    private fun createBindingReturnTypeDetector(env: ProcessingEnvironment): BindingReturnTypeDetector {
        return BindingReturnTypeDetector(
            elementUtils = env.elementUtils,
            typeUtils = env.typeUtils,
            messageProvider = createMessageProvider()
        )
    }


    private fun createBindingMethodNameFactory(): BindingMethodNameFactory {
        return BindingMethodNameFactory()
    }


    private fun createModuleSchemaFactory(env: ProcessingEnvironment): ModuleSchemaFactory {
        return ModuleSchemaFactory(
            moduleInterfaceNameFactory = createModuleInterfaceNameFactory(),
            elementUtils = env.elementUtils
        )
    }


    private fun createModuleInterfaceNameFactory(): ModuleInterfaceNameFactory {
        return ModuleInterfaceNameFactory()
    }


    private fun createPackageNameProvider(): PackageNameProvider {
        return PackageNameProvider()
    }


}