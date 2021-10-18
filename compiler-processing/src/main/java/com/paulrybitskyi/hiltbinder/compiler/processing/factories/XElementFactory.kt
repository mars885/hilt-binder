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

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.paulrybitskyi.hiltbinder.common.utils.unsafeCast
import com.paulrybitskyi.hiltbinder.compiler.processing.XElement
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.JavacProcessingEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.ksp.KspProcessingEnv
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind

internal object XElementFactory {


    fun createJavacElement(env: JavacProcessingEnv, delegate: Element): XElement {
        return when(delegate.kind) {
            ElementKind.CLASS,
            ElementKind.ENUM,
            ElementKind.INTERFACE,
            ElementKind.ANNOTATION_TYPE -> XTypeElementFactory.createJavacTypeElement(env, delegate.unsafeCast())
            else -> error("A Javac element with the kind = ${delegate.kind} is not supported.")
        }
    }


    fun createKspElement(env: KspProcessingEnv, delegate: KSAnnotated): XElement {
        return when(delegate) {
            is KSClassDeclaration -> XTypeElementFactory.createKspTypeElement(env, delegate)
            else -> error("A KSP element $delegate is not supported.")
        }
    }


}
