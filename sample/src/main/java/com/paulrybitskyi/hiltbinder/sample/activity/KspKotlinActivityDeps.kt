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

import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinActivityDep1
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinActivityDep10
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinActivityDep11
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinActivityDep2
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinActivityDep3
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinActivityDep4
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinActivityDep5
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinActivityDep6
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinActivityDep7
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinActivityDep8
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinActivityDep9
import javax.inject.Inject
import javax.inject.Named

internal class KspKotlinActivityDeps @Inject constructor(
    private val kspKotlinActivityDep1: KspKotlinActivityDep1,
    private val kspKotlinActivityDep2: KspKotlinActivityDep2,
    private val kspKotlinActivityDep3: KspKotlinActivityDep3,
    private val kspKotlinActivityDep4: KspKotlinActivityDep4,
    private val kspKotlinActivityDep5: KspKotlinActivityDep5,
    private val kspKotlinActivityDeps6: Set<@JvmSuppressWildcards KspKotlinActivityDep6>,
    private val kspKotlinActivityDeps7: Map<String, @JvmSuppressWildcards KspKotlinActivityDep7>,
    @Named("dep8") private val kspKotlinActivityDep8: KspKotlinActivityDep8,
    private val kspKotlinActivityDep9: KspKotlinActivityDep9<Float>,
    private val kspKotlinActivityDep10: KspKotlinActivityDep10<Float>,
    private val kspKotlinActivityDeps11: Set<@JvmSuppressWildcards KspKotlinActivityDep11<*>>
) {

    fun check() {
        check(kspKotlinActivityDeps6.size == 3)
        check(kspKotlinActivityDeps7.size == 3)
        check(kspKotlinActivityDeps11.size == 3)
    }
}
