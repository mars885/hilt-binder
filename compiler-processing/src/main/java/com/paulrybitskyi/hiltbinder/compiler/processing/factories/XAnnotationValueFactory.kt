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

package com.paulrybitskyi.hiltbinder.compiler.processing.factories

import com.paulrybitskyi.hiltbinder.compiler.processing.XAnnotationValue
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.JavacAnnotationValue
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.JavacProcessingEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.ksp.KspAnnotationValue
import com.paulrybitskyi.hiltbinder.compiler.processing.ksp.KspProcessingEnv

internal object XAnnotationValueFactory {

    fun createJavacValue(env: JavacProcessingEnv, value: Any?): XAnnotationValue {
        return JavacAnnotationValue(env, value)
    }

    fun createKspValue(env: KspProcessingEnv, value: Any?): XAnnotationValue {
        return KspAnnotationValue(env, value)
    }
}
