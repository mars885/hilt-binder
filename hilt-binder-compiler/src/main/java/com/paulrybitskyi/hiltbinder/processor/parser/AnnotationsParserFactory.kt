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

package com.paulrybitskyi.hiltbinder.processor.parser

import com.paulrybitskyi.hiltbinder.compiler.processing.XProcessingEnv
import com.paulrybitskyi.hiltbinder.processor.parser.detectors.BindingReturnTypeDetector
import com.paulrybitskyi.hiltbinder.processor.parser.detectors.ContributionTypeDetector
import com.paulrybitskyi.hiltbinder.processor.parser.detectors.HiltComponentDetector
import com.paulrybitskyi.hiltbinder.processor.parser.detectors.QualifierAnnotationDetector
import com.paulrybitskyi.hiltbinder.processor.parser.factories.BindingMethodNameFactory
import com.paulrybitskyi.hiltbinder.processor.parser.factories.BindingSchemaFactory
import com.paulrybitskyi.hiltbinder.processor.parser.factories.ModuleInterfaceNameFactory
import com.paulrybitskyi.hiltbinder.processor.parser.factories.ModuleSchemaFactory
import com.paulrybitskyi.hiltbinder.processor.parser.providers.MessageProvider
import com.paulrybitskyi.hiltbinder.processor.parser.providers.PackageNameProvider

internal object AnnotationsParserFactory {

    fun create(processingEnv: XProcessingEnv): AnnotationsParser {
        return AnnotationsParser(
            bindingSchemaFactory = createBindingSchemaFactory(processingEnv),
            moduleSchemaFactory = createModuleSchemaFactory(processingEnv),
            packageNameProvider = createPackageNameProvider(),
        )
    }

    private fun createBindingSchemaFactory(processingEnv: XProcessingEnv): BindingSchemaFactory {
        return BindingSchemaFactory(
            hiltComponentDetector = createHiltComponentDetector(processingEnv),
            contributionTypeDetector = createContributionTypeDetector(processingEnv),
            qualifierAnnotationDetector = createQualifierAnnotationDetector(processingEnv),
            bindingReturnTypeDetector = createBindingReturnTypeDetector(processingEnv),
            bindingMethodNameFactory = createBindingMethodNameFactory(),
        )
    }

    private fun createHiltComponentDetector(processingEnv: XProcessingEnv): HiltComponentDetector {
        return HiltComponentDetector(
            processingEnv = processingEnv,
            predefinedHiltComponentMapper = createPredefinedHiltComponentMapper(),
            messageProvider = createMessageProvider(),
        )
    }

    private fun createPredefinedHiltComponentMapper(): PredefinedHiltComponentMapper {
        return PredefinedHiltComponentMapper()
    }

    private fun createMessageProvider(): MessageProvider {
        return MessageProvider()
    }

    private fun createContributionTypeDetector(processingEnv: XProcessingEnv): ContributionTypeDetector {
        return ContributionTypeDetector(
            processingEnv = processingEnv,
            messageProvider = createMessageProvider(),
        )
    }

    private fun createQualifierAnnotationDetector(processingEnv: XProcessingEnv): QualifierAnnotationDetector {
        return QualifierAnnotationDetector(
            processingEnv = processingEnv,
            messageProvider = createMessageProvider(),
        )
    }

    private fun createBindingReturnTypeDetector(processingEnv: XProcessingEnv): BindingReturnTypeDetector {
        return BindingReturnTypeDetector(
            processingEnv = processingEnv,
            messageProvider = createMessageProvider(),
        )
    }

    private fun createBindingMethodNameFactory(): BindingMethodNameFactory {
        return BindingMethodNameFactory()
    }

    private fun createModuleSchemaFactory(processingEnv: XProcessingEnv): ModuleSchemaFactory {
        return ModuleSchemaFactory(
            processingEnv = processingEnv,
            moduleInterfaceNameFactory = createModuleInterfaceNameFactory(),
        )
    }

    private fun createModuleInterfaceNameFactory(): ModuleInterfaceNameFactory {
        return ModuleInterfaceNameFactory()
    }

    private fun createPackageNameProvider(): PackageNameProvider {
        return PackageNameProvider()
    }
}
