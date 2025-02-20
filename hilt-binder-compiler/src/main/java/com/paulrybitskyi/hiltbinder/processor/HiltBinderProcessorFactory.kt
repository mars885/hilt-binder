/*
 * Copyright 2021 Paul Rybitskyi, oss@paulrybitskyi.com
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

package com.paulrybitskyi.hiltbinder.processor

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.paulrybitskyi.hiltbinder.compiler.processing.XProcessingEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.factories.XProcessingEnvFactory
import com.paulrybitskyi.hiltbinder.processor.generator.ModuleFileGeneratorFactory
import com.paulrybitskyi.hiltbinder.processor.parser.AnnotationsParserFactory
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment

internal object HiltBinderProcessorFactory {

    fun createJavacProcessor(
        processingEnv: ProcessingEnvironment,
        roundEnv: RoundEnvironment,
    ): HiltBinderProcessor {
        return createProcessor(
            processingEnv = XProcessingEnvFactory.createJavacEnv(processingEnv, roundEnv),
        )
    }

    fun createKspProcessor(
        processingEnv: SymbolProcessorEnvironment,
        resolver: Resolver,
    ): HiltBinderProcessor {
        return createProcessor(
            processingEnv = XProcessingEnvFactory.createKspEnv(processingEnv, resolver),
        )
    }

    private fun createProcessor(processingEnv: XProcessingEnv): HiltBinderProcessor {
        return HiltBinderProcessor(
            roundEnv = processingEnv.roundEnv,
            logger = processingEnv.logger,
            annotationsParser = AnnotationsParserFactory.create(processingEnv),
            moduleFileGenerator = ModuleFileGeneratorFactory.create(processingEnv),
        )
    }
}
