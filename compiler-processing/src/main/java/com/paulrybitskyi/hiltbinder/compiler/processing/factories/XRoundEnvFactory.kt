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

package com.paulrybitskyi.hiltbinder.compiler.processing.factories

import com.paulrybitskyi.hiltbinder.compiler.processing.XRoundEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.JavacProcessingEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.JavacRoundEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.ksp.KspProcessingEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.ksp.KspRoundEnv
import javax.annotation.processing.RoundEnvironment

internal object XRoundEnvFactory {

    fun createJavacEnv(env: JavacProcessingEnv, roundEnv: RoundEnvironment): XRoundEnv {
        return JavacRoundEnv(env, roundEnv)
    }

    fun createKspEnv(env: KspProcessingEnv): XRoundEnv {
        return KspRoundEnv(env)
    }
}
