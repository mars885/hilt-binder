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

package com.paulrybitskyi.hiltbinder.sample.application

import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinSingletonDep1
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinSingletonDep10
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinSingletonDep11
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinSingletonDep12
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinSingletonDep13
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinSingletonDep2
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinSingletonDep3
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinSingletonDep4
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinSingletonDep5
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinSingletonDep6
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinSingletonDep7
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinSingletonDep8
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinSingletonDep9
import javax.inject.Inject
import javax.inject.Named

internal class KspKotlinSingletonDeps @Inject constructor(
    private val kspKotlinSingletonDep1: KspKotlinSingletonDep1,
    private val kspKotlinSingletonDep2: KspKotlinSingletonDep2,
    private val kspKotlinSingletonDep3: KspKotlinSingletonDep3,
    private val kspKotlinSingletonDep4: KspKotlinSingletonDep4,
    private val kspKotlinSingletonDep5: KspKotlinSingletonDep5,
    private val kspKotlinSingletonDep6: KspKotlinSingletonDep6,
    private val kspKotlinSingletonDep7: KspKotlinSingletonDep7,
    private val kspKotlinSingletonDeps8: Set<@JvmSuppressWildcards KspKotlinSingletonDep8>,
    private val kspKotlinSingletonDeps9: Map<Class<*>, @JvmSuppressWildcards KspKotlinSingletonDep9>,
    @param:Named("dep10") private val kspKotlinSingletonDep10: KspKotlinSingletonDep10,
    private val kspKotlinSingletonDep11: KspKotlinSingletonDep11<Int>,
    private val kspKotlinSingletonDep12: KspKotlinSingletonDep12<Int>,
    private val kspKotlinSingletonDeps13: Set<@JvmSuppressWildcards KspKotlinSingletonDep13<*>>,
) {

    fun check() {
        check(kspKotlinSingletonDeps8.size == 3)
        check(kspKotlinSingletonDeps9.size == 3)
        check(kspKotlinSingletonDeps13.size == 3)
    }
}
