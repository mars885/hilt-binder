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

import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.hiltbinder.processor.ksp.HiltBinderKspProcessorProvider
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.KotlinCompilation.ExitCode
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.kspSourcesDir
import com.tschuchort.compiletesting.symbolProcessorProviders
import org.junit.Test
import java.io.File

internal class HiltBinderKspTest {


    @Test
    fun `Binds class implicitly to its single interface`() {
        val returnType = getSourceFile("Testable.java")
        val bindingType = getSourceFile("1/Test.java")
        val expectedModule = getFile("1/ExpectedModule.java")
        val compilation = KotlinCompilation().apply {
            sources = listOf(returnType, bindingType)
            symbolProcessorProviders = listOf(HiltBinderKspProcessorProvider())
            verbose = false
            inheritClassPath = true
        }

        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    private fun getSourceFile(path: String): SourceFile {
        return SourceFile.fromPath(getFile(path))
    }


    private fun getFile(path: String): File {
        return File(checkNotNull(javaClass.classLoader.getResource(path)).path)
    }


}