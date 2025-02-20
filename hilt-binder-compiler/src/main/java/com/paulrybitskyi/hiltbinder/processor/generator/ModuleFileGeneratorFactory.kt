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

package com.paulrybitskyi.hiltbinder.processor.generator

import com.paulrybitskyi.hiltbinder.compiler.processing.XBackend
import com.paulrybitskyi.hiltbinder.compiler.processing.XProcessingEnv
import com.paulrybitskyi.hiltbinder.processor.generator.content.ModuleFileContentGenerator
import com.paulrybitskyi.hiltbinder.processor.generator.content.java.JavaBindingMethodSpecFactory
import com.paulrybitskyi.hiltbinder.processor.generator.content.java.JavaModuleFileContentGenerator
import com.paulrybitskyi.hiltbinder.processor.generator.content.java.JavaTypeSpecFactory
import com.paulrybitskyi.hiltbinder.processor.generator.content.kotlin.KotlinBindingMethodSpecFactory
import com.paulrybitskyi.hiltbinder.processor.generator.content.kotlin.KotlinModuleFileContentGenerator
import com.paulrybitskyi.hiltbinder.processor.generator.content.kotlin.KotlinTypeSpecFactory

internal object ModuleFileGeneratorFactory {

    fun create(processingEnv: XProcessingEnv): ModuleFileGenerator {
        val outputLanguage = getOutputLanguage(processingEnv.backend)

        return ModuleFileGenerator(
            outputLanguage = outputLanguage,
            moduleFileContentGenerator = createModuleFileContentGenerator(outputLanguage),
            filer = processingEnv.filer,
        )
    }

    private fun getOutputLanguage(backend: XBackend): Language {
        // At the moment, for Javac only Java is generated while
        // for KSP only Kotlin is generated.

        return when (backend) {
            XBackend.JAVAC -> Language.JAVA
            XBackend.KSP -> Language.KOTLIN
        }
    }

    private fun createModuleFileContentGenerator(language: Language): ModuleFileContentGenerator {
        return when (language) {
            Language.JAVA -> createJavaModuleFileContentGenerator()
            Language.KOTLIN -> createKotlinModuleFileContentGenerator()
        }
    }

    private fun createJavaModuleFileContentGenerator(): JavaModuleFileContentGenerator {
        return JavaModuleFileContentGenerator(
            typeSpecFactory = createJavaTypeSpecFactory(),
        )
    }

    private fun createJavaTypeSpecFactory(): JavaTypeSpecFactory {
        return JavaTypeSpecFactory(
            bindingMethodSpecFactory = createJavaBindingMethodSpecFactory(),
        )
    }

    private fun createJavaBindingMethodSpecFactory(): JavaBindingMethodSpecFactory {
        return JavaBindingMethodSpecFactory()
    }

    private fun createKotlinModuleFileContentGenerator(): KotlinModuleFileContentGenerator {
        return KotlinModuleFileContentGenerator(
            typeSpecFactory = createKotlinTypeSpecFactory(),
        )
    }

    private fun createKotlinTypeSpecFactory(): KotlinTypeSpecFactory {
        return KotlinTypeSpecFactory(
            bindingMethodSpecFactory = createKotlinBindingMethodSpecFactory(),
        )
    }

    private fun createKotlinBindingMethodSpecFactory(): KotlinBindingMethodSpecFactory {
        return KotlinBindingMethodSpecFactory()
    }
}
