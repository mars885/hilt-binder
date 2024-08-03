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

import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewModelDep1
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewModelDep10
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewModelDep11
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewModelDep2
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewModelDep3
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewModelDep4
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewModelDep5
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewModelDep6
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewModelDep7
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewModelDep8
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewModelDep9
import javax.inject.Inject
import javax.inject.Named

internal class KspKotlinViewModelDeps @Inject constructor(
    private val kspKotlinViewModelDep1: KspKotlinViewModelDep1,
    private val kspKotlinViewModelDep2: KspKotlinViewModelDep2,
    private val kspKotlinViewModelDep3: KspKotlinViewModelDep3,
    private val kspKotlinViewModelDep4: KspKotlinViewModelDep4,
    private val kspKotlinViewModelDep5: KspKotlinViewModelDep5,
    private val kspKotlinViewModelDeps6: Set<@JvmSuppressWildcards KspKotlinViewModelDep6>,
    private val kspKotlinViewModelDeps7: Map<Int, @JvmSuppressWildcards KspKotlinViewModelDep7>,
    @Named("dep8") private val kspKotlinViewModelDep8: KspKotlinViewModelDep8,
    private val kspKotlinViewModelDep9: KspKotlinViewModelDep9<Float>,
    private val kspKotlinViewModelDep10: KspKotlinViewModelDep10<Float>,
    private val kspKotlinViewModelDeps11: Set<@JvmSuppressWildcards KspKotlinViewModelDep11<*>>,
) {

    fun check() {
        check(kspKotlinViewModelDeps6.size == 3)
        check(kspKotlinViewModelDeps7.size == 3)
        check(kspKotlinViewModelDeps11.size == 3)
    }
}
