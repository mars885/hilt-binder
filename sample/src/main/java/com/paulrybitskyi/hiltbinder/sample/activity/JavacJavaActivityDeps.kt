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

package com.paulrybitskyi.hiltbinder.sample.activity

import com.paulrybitskyi.hiltbinder.sample.javac.activity.JavacJavaActivityDep1
import com.paulrybitskyi.hiltbinder.sample.javac.activity.JavacJavaActivityDep10
import com.paulrybitskyi.hiltbinder.sample.javac.activity.JavacJavaActivityDep11
import com.paulrybitskyi.hiltbinder.sample.javac.activity.JavacJavaActivityDep2
import com.paulrybitskyi.hiltbinder.sample.javac.activity.JavacJavaActivityDep3
import com.paulrybitskyi.hiltbinder.sample.javac.activity.JavacJavaActivityDep4
import com.paulrybitskyi.hiltbinder.sample.javac.activity.JavacJavaActivityDep5
import com.paulrybitskyi.hiltbinder.sample.javac.activity.JavacJavaActivityDep6
import com.paulrybitskyi.hiltbinder.sample.javac.activity.JavacJavaActivityDep7
import com.paulrybitskyi.hiltbinder.sample.javac.activity.JavacJavaActivityDep8
import com.paulrybitskyi.hiltbinder.sample.javac.activity.JavacJavaActivityDep9
import javax.inject.Inject
import javax.inject.Named

internal class JavacJavaActivityDeps @Inject constructor(
    private val javacJavaActivityDep1: JavacJavaActivityDep1,
    private val javacJavaActivityDep2: JavacJavaActivityDep2,
    private val javacJavaActivityDep3: JavacJavaActivityDep3,
    private val javacJavaActivityDep4: JavacJavaActivityDep4,
    private val javacJavaActivityDep5: JavacJavaActivityDep5,
    private val javacJavaActivityDeps6: Set<@JvmSuppressWildcards JavacJavaActivityDep6>,
    private val javacJavaActivityDeps7: Map<String, @JvmSuppressWildcards JavacJavaActivityDep7>,
    @Named("dep8") private val javacJavaActivityDep8: JavacJavaActivityDep8,
    private val javacJavaActivityDep9: JavacJavaActivityDep9<Float>,
    private val javacJavaActivityDep10: JavacJavaActivityDep10<Float>,
    private val javacJavaActivityDeps11: Set<@JvmSuppressWildcards JavacJavaActivityDep11<*>>
) {

    fun check() {
        check(javacJavaActivityDeps6.size == 3)
        check(javacJavaActivityDeps7.size == 3)
        check(javacJavaActivityDeps11.size == 3)
    }
}
