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

package com.paulrybitskyi.hiltbinder.sample.views

import com.paulrybitskyi.hiltbinder.sample.javac.view.JavacJavaViewDep1
import com.paulrybitskyi.hiltbinder.sample.javac.view.JavacJavaViewDep10
import com.paulrybitskyi.hiltbinder.sample.javac.view.JavacJavaViewDep11
import com.paulrybitskyi.hiltbinder.sample.javac.view.JavacJavaViewDep2
import com.paulrybitskyi.hiltbinder.sample.javac.view.JavacJavaViewDep3
import com.paulrybitskyi.hiltbinder.sample.javac.view.JavacJavaViewDep4
import com.paulrybitskyi.hiltbinder.sample.javac.view.JavacJavaViewDep5
import com.paulrybitskyi.hiltbinder.sample.javac.view.JavacJavaViewDep6
import com.paulrybitskyi.hiltbinder.sample.javac.view.JavacJavaViewDep7
import com.paulrybitskyi.hiltbinder.sample.javac.view.JavacJavaViewDep8
import com.paulrybitskyi.hiltbinder.sample.javac.view.JavacJavaViewDep9
import javax.inject.Inject
import javax.inject.Named

internal class JavacJavaViewDeps @Inject constructor(
    private val javacJavaViewDep1: JavacJavaViewDep1,
    private val javacJavaViewDep2: JavacJavaViewDep2,
    private val javacJavaViewDep3: JavacJavaViewDep3,
    private val javacJavaViewDep4: JavacJavaViewDep4,
    private val javacJavaViewDep5: JavacJavaViewDep5,
    private val javacJavaViewDeps6: Set<@JvmSuppressWildcards JavacJavaViewDep6>,
    private val javacJavaViewDeps7: Map<Long, @JvmSuppressWildcards JavacJavaViewDep7>,
    @Named("dep8") private val javacJavaViewDep8: JavacJavaViewDep8,
    private val javacJavaViewDep9: JavacJavaViewDep9<Double>,
    private val javacJavaViewDep10: JavacJavaViewDep10<Double>,
    private val javacJavaViewDeps11: Set<@JvmSuppressWildcards JavacJavaViewDep11<*>>,
) {

    fun check() {
        check(javacJavaViewDeps6.size == 3)
        check(javacJavaViewDeps7.size == 3)
        check(javacJavaViewDeps11.size == 3)
    }
}
