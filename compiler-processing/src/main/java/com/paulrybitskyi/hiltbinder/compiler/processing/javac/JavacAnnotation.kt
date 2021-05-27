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

package com.paulrybitskyi.hiltbinder.compiler.processing.javac

import com.paulrybitskyi.hiltbinder.compiler.processing.XAnnotation
import com.paulrybitskyi.hiltbinder.compiler.processing.XAnnotationValue
import com.paulrybitskyi.hiltbinder.compiler.processing.XType
import com.paulrybitskyi.hiltbinder.compiler.processing.factories.XAnnotationValueFactory
import com.paulrybitskyi.hiltbinder.compiler.processing.factories.XTypeFactory
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.utils.getSimpleNameStr
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.utils.toJavaAnnoSpec
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.utils.toKotlinAnnoSpec
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.JavaAnnotationSpec
import com.paulrybitskyi.hiltbinder.compiler.processing.utils.KotlinAnnotationSpec
import javax.lang.model.element.AnnotationMirror

internal class JavacAnnotation(
    private val env: JavacProcessingEnv,
    val delegate: AnnotationMirror
): XAnnotation {


    override val type: XType by lazy {
        XTypeFactory.createJavacType(env, delegate.annotationType)
    }

    override val args: Map<String, XAnnotationValue> by lazy {
        delegate.elementValues
            .mapKeys { it.key.getSimpleNameStr() }
            .mapValues { XAnnotationValueFactory.createJavacValue(env, it.value.value) }
    }

    override val javaAnnoSpec: JavaAnnotationSpec by lazy {
        delegate.toJavaAnnoSpec()
    }

    override val kotlinAnnoSpec: KotlinAnnotationSpec by lazy {
        delegate.toKotlinAnnoSpec()
    }


}
