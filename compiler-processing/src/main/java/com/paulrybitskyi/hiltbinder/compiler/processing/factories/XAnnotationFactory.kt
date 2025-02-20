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

import com.google.devtools.ksp.symbol.KSAnnotation
import com.paulrybitskyi.hiltbinder.compiler.processing.XAnnotation
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.JavacAnnotation
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.JavacProcessingEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.ksp.KspAnnotation
import com.paulrybitskyi.hiltbinder.compiler.processing.ksp.KspProcessingEnv
import javax.lang.model.element.AnnotationMirror

internal object XAnnotationFactory {

    fun createJavacAnnotation(env: JavacProcessingEnv, delegate: AnnotationMirror): XAnnotation {
        return JavacAnnotation(env, delegate)
    }

    fun createKspAnnotation(env: KspProcessingEnv, delegate: KSAnnotation): XAnnotation {
        return KspAnnotation(env, delegate)
    }
}
