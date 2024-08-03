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

package com.paulrybitskyi.hiltbinder.processor

import com.paulrybitskyi.hiltbinder.compiler.processing.XLogger
import com.paulrybitskyi.hiltbinder.compiler.processing.XRoundEnv
import com.paulrybitskyi.hiltbinder.processor.generator.ModuleFileGenerator
import com.paulrybitskyi.hiltbinder.processor.generator.generateModuleFiles
import com.paulrybitskyi.hiltbinder.processor.parser.AnnotationsParser
import com.paulrybitskyi.hiltbinder.processor.parser.HiltBinderException
import com.paulrybitskyi.hiltbinder.processor.utils.BIND_TYPE_QUALIFIED_NAME

internal class HiltBinderProcessor(
    private val roundEnv: XRoundEnv,
    private val logger: XLogger,
    private val annotationsParser: AnnotationsParser,
    private val moduleFileGenerator: ModuleFileGenerator,
) {

    fun process() {
        try {
            val elements = roundEnv.getElementsAnnotatedWith(BIND_TYPE_QUALIFIED_NAME)
            val moduleSchemas = annotationsParser.parse(elements)

            moduleFileGenerator.generateModuleFiles(moduleSchemas)
        } catch (expected: Throwable) {
            reportError(expected)
        }
    }

    private fun reportError(error: Throwable) {
        if (error is HiltBinderException) {
            logger.error(checkNotNull(error.message), error.element)
        } else {
            logger.error(error.message ?: error.toString())
        }
    }
}
