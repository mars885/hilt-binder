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

import com.paulrybitskyi.hiltbinder.sample.kapt.java.activity.KaptJavaActivityDep1
import com.paulrybitskyi.hiltbinder.sample.kapt.java.activity.KaptJavaActivityDep10
import com.paulrybitskyi.hiltbinder.sample.kapt.java.activity.KaptJavaActivityDep11
import com.paulrybitskyi.hiltbinder.sample.kapt.java.activity.KaptJavaActivityDep2
import com.paulrybitskyi.hiltbinder.sample.kapt.java.activity.KaptJavaActivityDep3
import com.paulrybitskyi.hiltbinder.sample.kapt.java.activity.KaptJavaActivityDep4
import com.paulrybitskyi.hiltbinder.sample.kapt.java.activity.KaptJavaActivityDep5
import com.paulrybitskyi.hiltbinder.sample.kapt.java.activity.KaptJavaActivityDep6
import com.paulrybitskyi.hiltbinder.sample.kapt.java.activity.KaptJavaActivityDep7
import com.paulrybitskyi.hiltbinder.sample.kapt.java.activity.KaptJavaActivityDep8
import com.paulrybitskyi.hiltbinder.sample.kapt.java.activity.KaptJavaActivityDep9
import javax.inject.Inject
import javax.inject.Named

internal class KaptJavaActivityDeps @Inject constructor(
    private val kaptJavaActivityDep1: KaptJavaActivityDep1,
    private val kaptJavaActivityDep2: KaptJavaActivityDep2,
    private val kaptJavaActivityDep3: KaptJavaActivityDep3,
    private val kaptJavaActivityDep4: KaptJavaActivityDep4,
    private val kaptJavaActivityDep5: KaptJavaActivityDep5,
    private val kaptJavaActivityDeps6: Set<@JvmSuppressWildcards KaptJavaActivityDep6>,
    private val kaptJavaActivityDeps7: Map<String, @JvmSuppressWildcards KaptJavaActivityDep7>,
    @Named("dep8") private val kaptJavaActivityDep8: KaptJavaActivityDep8,
    private val kaptJavaActivityDep9: KaptJavaActivityDep9<Float>,
    private val kaptJavaActivityDep10: KaptJavaActivityDep10<Float>,
    private val kaptJavaActivityDeps11: Set<@JvmSuppressWildcards KaptJavaActivityDep11<*>>,
) {

    fun check() {
        check(kaptJavaActivityDeps6.size == 3)
        check(kaptJavaActivityDeps7.size == 3)
        check(kaptJavaActivityDeps11.size == 3)
    }
}
