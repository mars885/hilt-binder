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

import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinFragmentDep1
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinFragmentDep10
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinFragmentDep11
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinFragmentDep2
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinFragmentDep3
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinFragmentDep4
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinFragmentDep5
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinFragmentDep6
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinFragmentDep7
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinFragmentDep8
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinFragmentDep9
import javax.inject.Inject
import javax.inject.Named

internal class KspKotlinFragmentDeps @Inject constructor(
    private val kspKotlinFragmentDep1: KspKotlinFragmentDep1,
    private val kspKotlinFragmentDep2: KspKotlinFragmentDep2,
    private val kspKotlinFragmentDep3: KspKotlinFragmentDep3,
    private val kspKotlinFragmentDep4: KspKotlinFragmentDep4,
    private val kspKotlinFragmentDep5: KspKotlinFragmentDep5,
    private val kspKotlinFragmentDeps6: Set<@JvmSuppressWildcards KspKotlinFragmentDep6>,
    private val kspKotlinFragmentDeps7: Map<Long, @JvmSuppressWildcards KspKotlinFragmentDep7>,
    @param:Named("dep8") private val kspKotlinFragmentDep8: KspKotlinFragmentDep8,
    private val kspKotlinFragmentDep9: KspKotlinFragmentDep9<Float>,
    private val kspKotlinFragmentDep10: KspKotlinFragmentDep10<Float>,
    private val kspKotlinFragmentDeps11: Set<@JvmSuppressWildcards KspKotlinFragmentDep11<*>>,
) {

    fun check() {
        check(kspKotlinFragmentDeps6.size == 3)
        check(kspKotlinFragmentDeps7.size == 3)
        check(kspKotlinFragmentDeps11.size == 3)
    }
}
