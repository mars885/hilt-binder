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

import com.paulrybitskyi.hiltbinder.sample.ksp.java.fragment.KspJavaFragmentDep1
import com.paulrybitskyi.hiltbinder.sample.ksp.java.fragment.KspJavaFragmentDep2
import com.paulrybitskyi.hiltbinder.sample.ksp.java.view.KspJavaViewDep12
import com.paulrybitskyi.hiltbinder.sample.ksp.java.view.KspJavaViewDep13
import com.paulrybitskyi.hiltbinder.sample.ksp.java.view.KspJavaViewDep14
import javax.inject.Inject

internal class KspJavaViewWithFragmentDeps @Inject constructor(
    private val kspJavaViewDep12: KspJavaViewDep12,
    private val kspJavaViewDep13: KspJavaViewDep13,
    private val kspJavaViewDep14: KspJavaViewDep14,
    private val kspJavaFragmentDep1: KspJavaFragmentDep1,
    private val kspJavaFragmentDep2: KspJavaFragmentDep2
)