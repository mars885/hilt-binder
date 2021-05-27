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

import com.paulrybitskyi.hiltbinder.common.utils.unsafeCast
import com.paulrybitskyi.hiltbinder.compiler.processing.XElement
import com.paulrybitskyi.hiltbinder.compiler.processing.XLogger
import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.tools.Diagnostic

internal class JavacLogger(
    private val messager: Messager
): XLogger {


    override fun info(message: String, element: XElement?) {
        messager.printMessage(Diagnostic.Kind.NOTE, message, element?.asJavacElement())
    }


    override fun warning(message: String, element: XElement?) {
        messager.printMessage(Diagnostic.Kind.WARNING, message, element?.asJavacElement())
    }


    override fun error(message: String, element: XElement?) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element?.asJavacElement())
    }


    private fun XElement.asJavacElement(): Element {
        return unsafeCast<JavacElement>().delegate
    }


}