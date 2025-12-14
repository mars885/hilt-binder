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

import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinFragmentDep1
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinFragmentDep10
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinFragmentDep11
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinFragmentDep2
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinFragmentDep3
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinFragmentDep4
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinFragmentDep5
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinFragmentDep6
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinFragmentDep7
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinFragmentDep8
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinFragmentDep9
import javax.inject.Inject
import javax.inject.Named

internal class KaptKotlinFragmentDeps @Inject constructor(
    private val kaptKotlinFragmentDep1: KaptKotlinFragmentDep1,
    private val kaptKotlinFragmentDep2: KaptKotlinFragmentDep2,
    private val kaptKotlinFragmentDep3: KaptKotlinFragmentDep3,
    private val kaptKotlinFragmentDep4: KaptKotlinFragmentDep4,
    private val kaptKotlinFragmentDep5: KaptKotlinFragmentDep5,
    private val kaptKotlinFragmentDeps6: Set<@JvmSuppressWildcards KaptKotlinFragmentDep6>,
    private val kaptKotlinFragmentDeps7: Map<Long, @JvmSuppressWildcards KaptKotlinFragmentDep7>,
    @param:Named("dep8") private val kaptKotlinFragmentDep8: KaptKotlinFragmentDep8,
    private val kaptKotlinFragmentDep9: KaptKotlinFragmentDep9<Float>,
    private val kaptKotlinFragmentDep10: KaptKotlinFragmentDep10<Float>,
    private val kaptKotlinFragmentDeps11: Set<@JvmSuppressWildcards KaptKotlinFragmentDep11<*>>,
) {

    fun check() {
        check(kaptKotlinFragmentDeps6.size == 3)
        check(kaptKotlinFragmentDeps7.size == 3)
        check(kaptKotlinFragmentDeps11.size == 3)
    }
}
