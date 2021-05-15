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

package com.paulrybitskyi.hiltbinder.processor.ksp

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.paulrybitskyi.hiltbinder.processor.common.BIND_TYPE_QUALIFIED_NAME
import com.paulrybitskyi.hiltbinder.processor.ksp.generator.ModuleFileGenerator
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.AnnotationsParserFactory
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.HiltBinderException

internal class HiltBinderKspProcessor(
    private val annotationsParserFactory: AnnotationsParserFactory,
    private val moduleFileGenerator: ModuleFileGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {


    override fun process(resolver: Resolver): List<KSAnnotated> {
        try {
            val annotationsParser = annotationsParserFactory.create(resolver)
            val symbols = resolver.getSymbolsWithAnnotation(BIND_TYPE_QUALIFIED_NAME)
            val moduleSchemas = annotationsParser.parse(symbols)

            moduleFileGenerator.generateFiles(moduleSchemas)
        } catch(error: Throwable) {
            reportError(error)
        }

        return emptyList()
    }


    private fun reportError(error: Throwable) {
        if(error is HiltBinderException) {
            logger.error(checkNotNull(error.message), error.symbol)
        } else {
            logger.error(error.message ?: error.toString())
        }
    }


}