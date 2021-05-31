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

package com.paulrybitskyi.hiltbinder.processor.brandnew

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.paulrybitskyi.hiltbinder.compiler.processing.XProcessingEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.factories.XProcessingEnvFactory
import com.paulrybitskyi.hiltbinder.processor.brandnew.generator.ModuleFileGeneratorFactory
import com.paulrybitskyi.hiltbinder.processor.brandnew.parser.AnnotationsParserFactory
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment

internal object HiltBinderProcessorFactory {


    fun createJavacProcessor(
        processingEnv: ProcessingEnvironment,
        roundEnv: RoundEnvironment
    ): HiltBinderProcessor {
        return createProcessor(
            processingEnv = XProcessingEnvFactory.createJavacEnv(processingEnv, roundEnv)
        )
    }


    fun createKspProcessor(
        resolver: Resolver,
        codeGenerator: CodeGenerator,
        kspLogger: KSPLogger
    ): HiltBinderProcessor {
        return createProcessor(
            processingEnv = XProcessingEnvFactory.createKspEnv(resolver, codeGenerator, kspLogger)
        )
    }


    private fun createProcessor(processingEnv: XProcessingEnv): HiltBinderProcessor {
        return HiltBinderProcessor(
            roundEnv = processingEnv.roundEnv,
            logger = processingEnv.logger,
            annotationsParser = AnnotationsParserFactory.create(processingEnv),
            moduleFileGenerator = ModuleFileGeneratorFactory.create(processingEnv)
        )
    }


}