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

package com.paulrybitskyi.hiltbinder.processor.javac

import com.google.auto.service.AutoService
import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.hiltbinder.processor.common.BIND_TYPE_QUALIFIED_NAME
import com.paulrybitskyi.hiltbinder.processor.javac.generator.ModuleFileGeneratorFactory
import com.paulrybitskyi.hiltbinder.processor.javac.parser.AnnotationsParserFactory
import com.paulrybitskyi.hiltbinder.processor.javac.parser.HiltBinderException
import net.ltgt.gradle.incap.IncrementalAnnotationProcessor
import net.ltgt.gradle.incap.IncrementalAnnotationProcessorType.AGGREGATING
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@IncrementalAnnotationProcessor(AGGREGATING)
@AutoService(Processor::class)
internal class HiltBinderJavacProcessor : AbstractProcessor() {


    private val annotationsParser by lazy { AnnotationsParserFactory.create(processingEnv) }
    private val moduleFileGenerator by lazy { ModuleFileGeneratorFactory.create(processingEnv) }
    private val logger by lazy { Logger(processingEnv.messager) }


    override fun getSupportedAnnotationTypes(): Set<String> {
        return setOf(BIND_TYPE_QUALIFIED_NAME)
    }


    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }


    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        try {
            roundEnv.getElementsAnnotatedWith(BindType::class.java)
                .let(annotationsParser::parse)
                .let(moduleFileGenerator::generateFiles)
        } catch(error: Throwable) {
            reportError(error)
        }

        return false
    }


    private fun reportError(error: Throwable) {
        if(error is HiltBinderException) {
            logger.error(checkNotNull(error.message), error.element)
        } else {
            logger.error(error.message ?: error.toString())
        }
    }


}