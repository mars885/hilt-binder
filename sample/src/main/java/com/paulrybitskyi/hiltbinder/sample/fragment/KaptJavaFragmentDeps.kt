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

package com.paulrybitskyi.hiltbinder.sample.fragment

import com.paulrybitskyi.hiltbinder.sample.kapt.java.fragment.KaptJavaFragmentDep1
import com.paulrybitskyi.hiltbinder.sample.kapt.java.fragment.KaptJavaFragmentDep10
import com.paulrybitskyi.hiltbinder.sample.kapt.java.fragment.KaptJavaFragmentDep11
import com.paulrybitskyi.hiltbinder.sample.kapt.java.fragment.KaptJavaFragmentDep2
import com.paulrybitskyi.hiltbinder.sample.kapt.java.fragment.KaptJavaFragmentDep3
import com.paulrybitskyi.hiltbinder.sample.kapt.java.fragment.KaptJavaFragmentDep4
import com.paulrybitskyi.hiltbinder.sample.kapt.java.fragment.KaptJavaFragmentDep5
import com.paulrybitskyi.hiltbinder.sample.kapt.java.fragment.KaptJavaFragmentDep6
import com.paulrybitskyi.hiltbinder.sample.kapt.java.fragment.KaptJavaFragmentDep7
import com.paulrybitskyi.hiltbinder.sample.kapt.java.fragment.KaptJavaFragmentDep8
import com.paulrybitskyi.hiltbinder.sample.kapt.java.fragment.KaptJavaFragmentDep9
import javax.inject.Inject
import javax.inject.Named

internal class KaptJavaFragmentDeps @Inject constructor(
    private val kaptJavaFragmentDep1: KaptJavaFragmentDep1,
    private val kaptJavaFragmentDep2: KaptJavaFragmentDep2,
    private val kaptJavaFragmentDep3: KaptJavaFragmentDep3,
    private val kaptJavaFragmentDep4: KaptJavaFragmentDep4,
    private val kaptJavaFragmentDep5: KaptJavaFragmentDep5,
    private val kaptJavaFragmentDeps6: Set<@JvmSuppressWildcards KaptJavaFragmentDep6>,
    private val kaptJavaFragmentDeps7: Map<Long, @JvmSuppressWildcards KaptJavaFragmentDep7>,
    @Named("dep8") private val kaptJavaFragmentDep8: KaptJavaFragmentDep8,
    private val kaptJavaFragmentDep9: KaptJavaFragmentDep9<Float>,
    private val kaptJavaFragmentDep10: KaptJavaFragmentDep10<Float>,
    private val kaptJavaFragmentDeps11: Set<@JvmSuppressWildcards KaptJavaFragmentDep11<*>>,
) {

    fun check() {
        check(kaptJavaFragmentDeps6.size == 3)
        check(kaptJavaFragmentDeps7.size == 3)
        check(kaptJavaFragmentDeps11.size == 3)
    }
}
