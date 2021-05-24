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
import com.paulrybitskyi.hiltbinder.processor.ksp.TestProcessorProvider
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.symbolProcessorProviders
import org.junit.Test

class Test {


    @Test
    fun `Test crash`() {
        val compilation = KotlinCompilation().apply {
            sources = listOf(
                SourceFile.java(
                    "TestMapKey.java",
                    """
                    import dagger.MapKey;
                    import java.lang.annotation.Retention;
                    import java.lang.annotation.RetentionPolicy;

                    @MapKey
                    @Retention(RetentionPolicy.RUNTIME)
                    public @interface TestMapKey {

                        Type type();

                        enum Type { ONE, TWO, THREE }

                    }
                    """.trimIndent()
                ),
                SourceFile.java(
                    "Test.java",
                    """
                    import com.paulrybitskyi.hiltbinder.InterestingAnnotation;
                    
                    @TestMapKey(type = TestMapKey.Type.ONE)
                    @InterestingAnnotation
                    public class Test {}
                    """.trimIndent()
                )
            )
            verbose = true
            inheritClassPath = true
            symbolProcessorProviders = listOf(TestProcessorProvider())
        }
        val result = compilation.compile()

        assertThat(result).isEqualTo(KotlinCompilation.ExitCode.COMPILATION_ERROR)
    }


    @Test
    fun `Another one`() {

    }


}