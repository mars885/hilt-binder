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

package com.paulrybitskyi.hiltbinder.sample.views

import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewDep1
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewDep10
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewDep11
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewDep2
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewDep3
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewDep4
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewDep5
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewDep6
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewDep7
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewDep8
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewDep9
import javax.inject.Inject
import javax.inject.Named

internal class KaptKotlinViewDeps @Inject constructor(
    private val kaptKotlinViewDep1: KaptKotlinViewDep1,
    private val kaptKotlinViewDep2: KaptKotlinViewDep2,
    private val kaptKotlinViewDep3: KaptKotlinViewDep3,
    private val kaptKotlinViewDep4: KaptKotlinViewDep4,
    private val kaptKotlinViewDep5: KaptKotlinViewDep5,
    private val kaptKotlinViewDeps6: Set<@JvmSuppressWildcards KaptKotlinViewDep6>,
    private val kaptKotlinViewDeps7: Map<Long, @JvmSuppressWildcards KaptKotlinViewDep7>,
    @Named("dep8") private val kaptKotlinViewDep8: KaptKotlinViewDep8,
    private val kaptKotlinViewDep9: KaptKotlinViewDep9<Double>,
    private val kaptKotlinViewDep10: KaptKotlinViewDep10<Double>,
    private val kaptKotlinViewDeps11: Set<@JvmSuppressWildcards KaptKotlinViewDep11<*>>,
) {

    fun check() {
        check(kaptKotlinViewDeps6.size == 3)
        check(kaptKotlinViewDeps7.size == 3)
        check(kaptKotlinViewDeps11.size == 3)
    }
}
