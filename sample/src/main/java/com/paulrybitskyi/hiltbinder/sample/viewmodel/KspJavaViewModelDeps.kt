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

import com.paulrybitskyi.hiltbinder.sample.ksp.java.viewmodel.*
import javax.inject.Inject
import javax.inject.Named

internal class KspJavaViewModelDeps @Inject constructor(
    private val kspJavaViewModelDep1: KspJavaViewModelDep1,
    private val kspJavaViewModelDep2: KspJavaViewModelDep2,
    private val kspJavaViewModelDep3: KspJavaViewModelDep3,
    private val kspJavaViewModelDep4: KspJavaViewModelDep4,
    private val kspJavaViewModelDep5: KspJavaViewModelDep5,
    private val kspJavaViewModelDeps6: Set<@JvmSuppressWildcards KspJavaViewModelDep6>,
    private val kspJavaViewModelDeps7: Map<Int, @JvmSuppressWildcards KspJavaViewModelDep7>,
    @Named("dep8") private val kspJavaViewModelDep8: KspJavaViewModelDep8,
    private val kspJavaViewModelDep9: KspJavaViewModelDep9<Float>,
    private val kspJavaViewModelDep10: KspJavaViewModelDep10<Float>,
    private val kspJavaViewModelDeps11: Set<@JvmSuppressWildcards KspJavaViewModelDep11<*>>
) {


    fun check() {
        check(kspJavaViewModelDeps6.size == 3)
        check(kspJavaViewModelDeps7.size == 3)
        check(kspJavaViewModelDeps11.size == 3)
    }


}