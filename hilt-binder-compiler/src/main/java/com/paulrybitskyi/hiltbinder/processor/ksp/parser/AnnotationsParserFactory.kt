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

package com.paulrybitskyi.hiltbinder.processor.ksp.parser

import com.google.devtools.ksp.processing.Resolver
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.detectors.BindingReturnTypeDetector
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.detectors.ContributionTypeDetector
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.detectors.HiltComponentDetector
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.detectors.QualifierAnnotationDetector
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.factories.BindingMethodNameFactory
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.factories.BindingSchemaFactory
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.factories.ModuleInterfaceNameFactory
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.factories.ModuleSchemaFactory
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.providers.MessageProvider
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.providers.PackageNameProvider

internal object AnnotationsParserFactory {


    fun create(resolver: Resolver): AnnotationsParser {
        return AnnotationsParser(
            bindingSchemaFactory = createBindingSchemaFactory(resolver),
            moduleSchemaFactory = createModuleSchemaFactory(resolver),
            packageNameProvider = createPackageNameProvider()
        )
    }


    private fun createBindingSchemaFactory(resolver: Resolver): BindingSchemaFactory {
        return BindingSchemaFactory(
            hiltComponentDetector = createHiltComponentDetector(resolver),
            contributionTypeDetector = createContributionTypeDetector(resolver),
            qualifierAnnotationDetector = createQualifierAnnotationDetector(resolver),
            bindingReturnTypeDetector = createBindingReturnTypeDetector(resolver),
            bindingMethodNameFactory = createBindingMethodNameFactory()
        )
    }


    private fun createHiltComponentDetector(resolver: Resolver): HiltComponentDetector {
        return HiltComponentDetector(
            resolver = resolver,
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


    private fun createContributionTypeDetector(resolver: Resolver): ContributionTypeDetector {
        return ContributionTypeDetector(
            resolver = resolver,
            messageProvider = createMessageProvider()
        )
    }


    private fun createQualifierAnnotationDetector(resolver: Resolver): QualifierAnnotationDetector {
        return QualifierAnnotationDetector(
            resolver = resolver,
            messageProvider = createMessageProvider()
        )
    }


    private fun createBindingReturnTypeDetector(resolver: Resolver): BindingReturnTypeDetector {
        return BindingReturnTypeDetector(
            resolver = resolver,
            messageProvider = createMessageProvider()
        )
    }


    private fun createBindingMethodNameFactory(): BindingMethodNameFactory {
        return BindingMethodNameFactory()
    }


    private fun createModuleSchemaFactory(resolver: Resolver): ModuleSchemaFactory {
        return ModuleSchemaFactory(
            resolver = resolver,
            moduleInterfaceNameFactory = createModuleInterfaceNameFactory()
        )
    }


    private fun createModuleInterfaceNameFactory(): ModuleInterfaceNameFactory {
        return ModuleInterfaceNameFactory()
    }


    private fun createPackageNameProvider(): PackageNameProvider {
        return PackageNameProvider()
    }


}