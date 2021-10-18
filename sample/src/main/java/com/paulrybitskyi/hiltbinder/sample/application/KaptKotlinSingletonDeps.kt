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

package com.paulrybitskyi.hiltbinder.sample.application

import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinSingletonDep1
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinSingletonDep10
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinSingletonDep11
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinSingletonDep12
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinSingletonDep13
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinSingletonDep2
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinSingletonDep3
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinSingletonDep4
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinSingletonDep5
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinSingletonDep6
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinSingletonDep7
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinSingletonDep8
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinSingletonDep9
import javax.inject.Inject
import javax.inject.Named

internal class KaptKotlinSingletonDeps @Inject constructor(
    private val kaptKotlinSingletonDep1: KaptKotlinSingletonDep1,
    private val kaptKotlinSingletonDep2: KaptKotlinSingletonDep2,
    private val kaptKotlinSingletonDep3: KaptKotlinSingletonDep3,
    private val kaptKotlinSingletonDep4: KaptKotlinSingletonDep4,
    private val kaptKotlinSingletonDep5: KaptKotlinSingletonDep5,
    private val kaptKotlinSingletonDep6: KaptKotlinSingletonDep6,
    private val kaptKotlinSingletonDep7: KaptKotlinSingletonDep7,
    private val kaptKotlinSingletonDeps8: Set<@JvmSuppressWildcards KaptKotlinSingletonDep8>,
    private val kaptKotlinSingletonDeps9: Map<Class<*>, @JvmSuppressWildcards KaptKotlinSingletonDep9>,
    @Named("dep10") private val kaptKotlinSingletonDep10: KaptKotlinSingletonDep10,
    private val kaptKotlinSingletonDep11: KaptKotlinSingletonDep11<Int>,
    private val kaptKotlinSingletonDep12: KaptKotlinSingletonDep12<Int>,
    private val kaptKotlinSingletonDeps13: Set<@JvmSuppressWildcards KaptKotlinSingletonDep13<*>>
) {


    fun check() {
        check(kaptKotlinSingletonDeps8.size == 3)
        check(kaptKotlinSingletonDeps9.size == 3)
        check(kaptKotlinSingletonDeps13.size == 3)
    }


}
