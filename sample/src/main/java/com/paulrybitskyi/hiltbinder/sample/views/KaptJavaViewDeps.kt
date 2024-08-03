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

import com.paulrybitskyi.hiltbinder.sample.kapt.java.view.KaptJavaViewDep1
import com.paulrybitskyi.hiltbinder.sample.kapt.java.view.KaptJavaViewDep10
import com.paulrybitskyi.hiltbinder.sample.kapt.java.view.KaptJavaViewDep11
import com.paulrybitskyi.hiltbinder.sample.kapt.java.view.KaptJavaViewDep2
import com.paulrybitskyi.hiltbinder.sample.kapt.java.view.KaptJavaViewDep3
import com.paulrybitskyi.hiltbinder.sample.kapt.java.view.KaptJavaViewDep4
import com.paulrybitskyi.hiltbinder.sample.kapt.java.view.KaptJavaViewDep5
import com.paulrybitskyi.hiltbinder.sample.kapt.java.view.KaptJavaViewDep6
import com.paulrybitskyi.hiltbinder.sample.kapt.java.view.KaptJavaViewDep7
import com.paulrybitskyi.hiltbinder.sample.kapt.java.view.KaptJavaViewDep8
import com.paulrybitskyi.hiltbinder.sample.kapt.java.view.KaptJavaViewDep9
import javax.inject.Inject
import javax.inject.Named

internal class KaptJavaViewDeps @Inject constructor(
    private val kaptJavaViewDep1: KaptJavaViewDep1,
    private val kaptJavaViewDep2: KaptJavaViewDep2,
    private val kaptJavaViewDep3: KaptJavaViewDep3,
    private val kaptJavaViewDep4: KaptJavaViewDep4,
    private val kaptJavaViewDep5: KaptJavaViewDep5,
    private val kaptJavaViewDeps6: Set<@JvmSuppressWildcards KaptJavaViewDep6>,
    private val kaptJavaViewDeps7: Map<Long, @JvmSuppressWildcards KaptJavaViewDep7>,
    @Named("dep8") private val kaptJavaViewDep8: KaptJavaViewDep8,
    private val kaptJavaViewDep9: KaptJavaViewDep9<Double>,
    private val kaptJavaViewDep10: KaptJavaViewDep10<Double>,
    private val kaptJavaViewDeps11: Set<@JvmSuppressWildcards KaptJavaViewDep11<*>>,
) {

    fun check() {
        check(kaptJavaViewDeps6.size == 3)
        check(kaptJavaViewDeps7.size == 3)
        check(kaptJavaViewDeps11.size == 3)
    }
}
