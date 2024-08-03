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

package com.paulrybitskyi.hiltbinder.sample.views.withfragment

import com.paulrybitskyi.hiltbinder.sample.javac.fragment.JavacJavaFragmentDep1
import com.paulrybitskyi.hiltbinder.sample.javac.fragment.JavacJavaFragmentDep2
import com.paulrybitskyi.hiltbinder.sample.javac.view.JavacJavaViewDep12
import com.paulrybitskyi.hiltbinder.sample.javac.view.JavacJavaViewDep13
import com.paulrybitskyi.hiltbinder.sample.javac.view.JavacJavaViewDep14
import javax.inject.Inject

internal class JavacJavaViewWithFragmentDeps @Inject constructor(
    private val javacJavaViewDep12: JavacJavaViewDep12,
    private val javacJavaViewDep13: JavacJavaViewDep13,
    private val javacJavaViewDep14: JavacJavaViewDep14,
    private val javacJavaFragmentDep1: JavacJavaFragmentDep1,
    private val javacJavaFragmentDep2: JavacJavaFragmentDep2,
)
