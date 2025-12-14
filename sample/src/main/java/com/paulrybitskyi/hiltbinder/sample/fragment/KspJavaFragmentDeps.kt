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

import com.paulrybitskyi.hiltbinder.sample.ksp.java.fragment.KspJavaFragmentDep1
import com.paulrybitskyi.hiltbinder.sample.ksp.java.fragment.KspJavaFragmentDep10
import com.paulrybitskyi.hiltbinder.sample.ksp.java.fragment.KspJavaFragmentDep11
import com.paulrybitskyi.hiltbinder.sample.ksp.java.fragment.KspJavaFragmentDep2
import com.paulrybitskyi.hiltbinder.sample.ksp.java.fragment.KspJavaFragmentDep3
import com.paulrybitskyi.hiltbinder.sample.ksp.java.fragment.KspJavaFragmentDep4
import com.paulrybitskyi.hiltbinder.sample.ksp.java.fragment.KspJavaFragmentDep5
import com.paulrybitskyi.hiltbinder.sample.ksp.java.fragment.KspJavaFragmentDep6
import com.paulrybitskyi.hiltbinder.sample.ksp.java.fragment.KspJavaFragmentDep7
import com.paulrybitskyi.hiltbinder.sample.ksp.java.fragment.KspJavaFragmentDep8
import com.paulrybitskyi.hiltbinder.sample.ksp.java.fragment.KspJavaFragmentDep9
import javax.inject.Inject
import javax.inject.Named

internal class KspJavaFragmentDeps @Inject constructor(
    private val kspJavaFragmentDep1: KspJavaFragmentDep1,
    private val kspJavaFragmentDep2: KspJavaFragmentDep2,
    private val kspJavaFragmentDep3: KspJavaFragmentDep3,
    private val kspJavaFragmentDep4: KspJavaFragmentDep4,
    private val kspJavaFragmentDep5: KspJavaFragmentDep5,
    private val kspJavaFragmentDeps6: Set<@JvmSuppressWildcards KspJavaFragmentDep6>,
    private val kspJavaFragmentDeps7: Map<Long, @JvmSuppressWildcards KspJavaFragmentDep7>,
    @param:Named("dep8") private val kspJavaFragmentDep8: KspJavaFragmentDep8,
    private val kspJavaFragmentDep9: KspJavaFragmentDep9<Float>,
    private val kspJavaFragmentDep10: KspJavaFragmentDep10<Float>,
    private val kspJavaFragmentDeps11: Set<@JvmSuppressWildcards KspJavaFragmentDep11<*>>,
) {

    fun check() {
        check(kspJavaFragmentDeps6.size == 3)
        check(kspJavaFragmentDeps7.size == 3)
        check(kspJavaFragmentDeps11.size == 3)
    }
}
