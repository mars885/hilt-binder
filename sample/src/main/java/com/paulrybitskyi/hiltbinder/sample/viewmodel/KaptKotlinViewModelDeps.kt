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

package com.paulrybitskyi.hiltbinder.sample.viewmodel

import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewModelDep1
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewModelDep10
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewModelDep11
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewModelDep2
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewModelDep3
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewModelDep4
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewModelDep5
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewModelDep6
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewModelDep7
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewModelDep8
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewModelDep9
import javax.inject.Inject
import javax.inject.Named

internal class KaptKotlinViewModelDeps @Inject constructor(
    private val kaptKotlinViewModelDep1: KaptKotlinViewModelDep1,
    private val kaptKotlinViewModelDep2: KaptKotlinViewModelDep2,
    private val kaptKotlinViewModelDep3: KaptKotlinViewModelDep3,
    private val kaptKotlinViewModelDep4: KaptKotlinViewModelDep4,
    private val kaptKotlinViewModelDep5: KaptKotlinViewModelDep5,
    private val kaptKotlinViewModelDeps6: Set<@JvmSuppressWildcards KaptKotlinViewModelDep6>,
    private val kaptKotlinViewModelDeps7: Map<Int, @JvmSuppressWildcards KaptKotlinViewModelDep7>,
    @Named("dep8") private val kaptKotlinViewModelDep8: KaptKotlinViewModelDep8,
    private val kaptKotlinViewModelDep9: KaptKotlinViewModelDep9<Float>,
    private val kaptKotlinViewModelDep10: KaptKotlinViewModelDep10<Float>,
    private val kaptKotlinViewModelDeps11: Set<@JvmSuppressWildcards KaptKotlinViewModelDep11<*>>,
) {

    fun check() {
        check(kaptKotlinViewModelDeps6.size == 3)
        check(kaptKotlinViewModelDeps7.size == 3)
        check(kaptKotlinViewModelDeps11.size == 3)
    }
}
