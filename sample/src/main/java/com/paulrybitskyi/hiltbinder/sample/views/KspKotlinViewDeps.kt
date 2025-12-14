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

import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewDep1
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewDep10
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewDep11
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewDep2
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewDep3
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewDep4
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewDep5
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewDep6
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewDep7
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewDep8
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewDep9
import javax.inject.Inject
import javax.inject.Named

internal class KspKotlinViewDeps @Inject constructor(
    private val kspKotlinViewDep1: KspKotlinViewDep1,
    private val kspKotlinViewDep2: KspKotlinViewDep2,
    private val kspKotlinViewDep3: KspKotlinViewDep3,
    private val kspKotlinViewDep4: KspKotlinViewDep4,
    private val kspKotlinViewDep5: KspKotlinViewDep5,
    private val kspKotlinViewDeps6: Set<@JvmSuppressWildcards KspKotlinViewDep6>,
    private val kspKotlinViewDeps7: Map<Long, @JvmSuppressWildcards KspKotlinViewDep7>,
    @param:Named("dep8") private val kspKotlinViewDep8: KspKotlinViewDep8,
    private val kspKotlinViewDep9: KspKotlinViewDep9<Double>,
    private val kspKotlinViewDep10: KspKotlinViewDep10<Double>,
    private val kspKotlinViewDeps11: Set<@JvmSuppressWildcards KspKotlinViewDep11<*>>,
) {

    fun check() {
        check(kspKotlinViewDeps6.size == 3)
        check(kspKotlinViewDeps7.size == 3)
        check(kspKotlinViewDeps11.size == 3)
    }
}
