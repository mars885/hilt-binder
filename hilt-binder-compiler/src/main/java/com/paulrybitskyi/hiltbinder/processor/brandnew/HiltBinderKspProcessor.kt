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

import com.google.auto.service.AutoService
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated

internal class HiltBinderKspProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
): SymbolProcessor {


    override fun process(resolver: Resolver): List<KSAnnotated> {
        HiltBinderProcessorFactory
            .createKspProcessor(resolver, codeGenerator, logger)
            .process()

        return emptyList()
    }


    @AutoService(SymbolProcessorProvider::class)
    class Provider : SymbolProcessorProvider {

        override fun create(
            options: Map<String, String>,
            kotlinVersion: KotlinVersion,
            codeGenerator: CodeGenerator,
            logger: KSPLogger
        ): SymbolProcessor {
            return HiltBinderKspProcessor(
                codeGenerator = codeGenerator,
                logger = logger
            )
        }

    }


}