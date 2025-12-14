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

package com.paulrybitskyi.hiltbinder.sample.activity

import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinActivityDep1
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinActivityDep10
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinActivityDep11
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinActivityDep2
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinActivityDep3
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinActivityDep4
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinActivityDep5
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinActivityDep6
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinActivityDep7
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinActivityDep8
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinActivityDep9
import javax.inject.Inject
import javax.inject.Named

internal class KaptKotlinActivityDeps @Inject constructor(
    private val kaptKotlinActivityDep1: KaptKotlinActivityDep1,
    private val kaptKotlinActivityDep2: KaptKotlinActivityDep2,
    private val kaptKotlinActivityDep3: KaptKotlinActivityDep3,
    private val kaptKotlinActivityDep4: KaptKotlinActivityDep4,
    private val kaptKotlinActivityDep5: KaptKotlinActivityDep5,
    private val kaptKotlinActivityDeps6: Set<@JvmSuppressWildcards KaptKotlinActivityDep6>,
    private val kaptKotlinActivityDeps7: Map<String, @JvmSuppressWildcards KaptKotlinActivityDep7>,
    @param:Named("dep8") private val kaptKotlinActivityDep8: KaptKotlinActivityDep8,
    private val kaptKotlinActivityDep9: KaptKotlinActivityDep9<Float>,
    private val kaptKotlinActivityDep10: KaptKotlinActivityDep10<Float>,
    private val kaptKotlinActivityDeps11: Set<@JvmSuppressWildcards KaptKotlinActivityDep11<*>>,
) {

    fun check() {
        check(kaptKotlinActivityDeps6.size == 3)
        check(kaptKotlinActivityDeps7.size == 3)
        check(kaptKotlinActivityDeps11.size == 3)
    }
}
