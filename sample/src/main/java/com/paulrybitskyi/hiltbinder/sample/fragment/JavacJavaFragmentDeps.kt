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

package com.paulrybitskyi.hiltbinder.sample.fragment

import com.paulrybitskyi.hiltbinder.sample.javac.fragment.JavacJavaFragmentDep1
import com.paulrybitskyi.hiltbinder.sample.javac.fragment.JavacJavaFragmentDep10
import com.paulrybitskyi.hiltbinder.sample.javac.fragment.JavacJavaFragmentDep11
import com.paulrybitskyi.hiltbinder.sample.javac.fragment.JavacJavaFragmentDep2
import com.paulrybitskyi.hiltbinder.sample.javac.fragment.JavacJavaFragmentDep3
import com.paulrybitskyi.hiltbinder.sample.javac.fragment.JavacJavaFragmentDep4
import com.paulrybitskyi.hiltbinder.sample.javac.fragment.JavacJavaFragmentDep5
import com.paulrybitskyi.hiltbinder.sample.javac.fragment.JavacJavaFragmentDep6
import com.paulrybitskyi.hiltbinder.sample.javac.fragment.JavacJavaFragmentDep7
import com.paulrybitskyi.hiltbinder.sample.javac.fragment.JavacJavaFragmentDep8
import com.paulrybitskyi.hiltbinder.sample.javac.fragment.JavacJavaFragmentDep9
import javax.inject.Inject
import javax.inject.Named

internal class JavacJavaFragmentDeps @Inject constructor(
    private val javacJavaFragmentDep1: JavacJavaFragmentDep1,
    private val javacJavaFragmentDep2: JavacJavaFragmentDep2,
    private val javacJavaFragmentDep3: JavacJavaFragmentDep3,
    private val javacJavaFragmentDep4: JavacJavaFragmentDep4,
    private val javacJavaFragmentDep5: JavacJavaFragmentDep5,
    private val javacJavaFragmentDeps6: Set<@JvmSuppressWildcards JavacJavaFragmentDep6>,
    private val javacJavaFragmentDeps7: Map<Long, @JvmSuppressWildcards JavacJavaFragmentDep7>,
    @Named("dep8") private val javacJavaFragmentDep8: JavacJavaFragmentDep8,
    private val javacJavaFragmentDep9: JavacJavaFragmentDep9<Float>,
    private val javacJavaFragmentDep10: JavacJavaFragmentDep10<Float>,
    private val javacJavaFragmentDeps11: Set<@JvmSuppressWildcards JavacJavaFragmentDep11<*>>,
) {

    fun check() {
        check(javacJavaFragmentDeps6.size == 3)
        check(javacJavaFragmentDeps7.size == 3)
        check(javacJavaFragmentDeps11.size == 3)
    }
}
