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

package com.paulrybitskyi.hiltbinder.sample.viewmodel

import com.paulrybitskyi.hiltbinder.sample.javac.viewmodel.*
import javax.inject.Inject
import javax.inject.Named

internal class JavacJavaViewModelDeps @Inject constructor(
    private val javacJavaViewModelDep1: JavacJavaViewModelDep1,
    private val javacJavaViewModelDep2: JavacJavaViewModelDep2,
    private val javacJavaViewModelDep3: JavacJavaViewModelDep3,
    private val javacJavaViewModelDep4: JavacJavaViewModelDep4,
    private val javacJavaViewModelDep5: JavacJavaViewModelDep5,
    private val javacJavaViewModelDeps6: Set<@JvmSuppressWildcards JavacJavaViewModelDep6>,
    private val javacJavaViewModelDeps7: Map<Int, @JvmSuppressWildcards JavacJavaViewModelDep7>,
    @Named("dep8") private val javacJavaViewModelDep8: JavacJavaViewModelDep8,
    private val javacJavaViewModelDep9: JavacJavaViewModelDep9<Float>,
    private val javacJavaViewModelDep10: JavacJavaViewModelDep10<Float>,
    private val javacJavaViewModelDeps11: Set<@JvmSuppressWildcards JavacJavaViewModelDep11<*>>
) {


    fun check() {
        check(javacJavaViewModelDeps6.size == 3)
        check(javacJavaViewModelDeps7.size == 3)
        check(javacJavaViewModelDeps11.size == 3)
    }


}