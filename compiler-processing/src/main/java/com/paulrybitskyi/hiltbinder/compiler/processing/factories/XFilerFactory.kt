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

import com.google.devtools.ksp.processing.CodeGenerator
import com.paulrybitskyi.hiltbinder.compiler.processing.XFiler
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.JavacFiler
import com.paulrybitskyi.hiltbinder.compiler.processing.ksp.KspFiler
import javax.annotation.processing.Filer

internal object XFilerFactory {

    fun createJavacFiler(delegate: Filer): XFiler {
        return JavacFiler(delegate)
    }

    fun createKspFiler(delegate: CodeGenerator): XFiler {
        return KspFiler(delegate)
    }
}
