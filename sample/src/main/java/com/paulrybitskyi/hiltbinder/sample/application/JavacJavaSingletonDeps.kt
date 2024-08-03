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

package com.paulrybitskyi.hiltbinder.sample.application

import com.paulrybitskyi.hiltbinder.sample.javac.singleton.JavacJavaSingletonDep1
import com.paulrybitskyi.hiltbinder.sample.javac.singleton.JavacJavaSingletonDep10
import com.paulrybitskyi.hiltbinder.sample.javac.singleton.JavacJavaSingletonDep11
import com.paulrybitskyi.hiltbinder.sample.javac.singleton.JavacJavaSingletonDep12
import com.paulrybitskyi.hiltbinder.sample.javac.singleton.JavacJavaSingletonDep13
import com.paulrybitskyi.hiltbinder.sample.javac.singleton.JavacJavaSingletonDep2
import com.paulrybitskyi.hiltbinder.sample.javac.singleton.JavacJavaSingletonDep3
import com.paulrybitskyi.hiltbinder.sample.javac.singleton.JavacJavaSingletonDep4
import com.paulrybitskyi.hiltbinder.sample.javac.singleton.JavacJavaSingletonDep5
import com.paulrybitskyi.hiltbinder.sample.javac.singleton.JavacJavaSingletonDep6
import com.paulrybitskyi.hiltbinder.sample.javac.singleton.JavacJavaSingletonDep7
import com.paulrybitskyi.hiltbinder.sample.javac.singleton.JavacJavaSingletonDep8
import com.paulrybitskyi.hiltbinder.sample.javac.singleton.JavacJavaSingletonDep9
import javax.inject.Inject
import javax.inject.Named

internal class JavacJavaSingletonDeps @Inject constructor(
    private val javacJavaSingletonDep1: JavacJavaSingletonDep1,
    private val javacJavaSingletonDep2: JavacJavaSingletonDep2,
    private val javacJavaSingletonDep3: JavacJavaSingletonDep3,
    private val javacJavaSingletonDep4: JavacJavaSingletonDep4,
    private val javacJavaSingletonDep5: JavacJavaSingletonDep5,
    private val javacJavaSingletonDep6: JavacJavaSingletonDep6,
    private val javacJavaSingletonDep7: JavacJavaSingletonDep7,
    private val javacJavaSingletonDeps8: Set<@JvmSuppressWildcards JavacJavaSingletonDep8>,
    private val javacJavaSingletonDeps9: Map<Class<*>, @JvmSuppressWildcards JavacJavaSingletonDep9>,
    @Named("dep10") private val javacJavaSingletonDep10: JavacJavaSingletonDep10,
    private val javacJavaSingletonDep11: JavacJavaSingletonDep11<Int>,
    private val javacJavaSingletonDep12: JavacJavaSingletonDep12<Int>,
    private val javacJavaSingletonDeps13: Set<@JvmSuppressWildcards JavacJavaSingletonDep13<*>>,
) {

    fun check() {
        check(javacJavaSingletonDeps8.size == 3)
        check(javacJavaSingletonDeps9.size == 3)
        check(javacJavaSingletonDeps13.size == 3)
    }
}
