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

package com.paulrybitskyi.hiltbinder.sample.views

import com.paulrybitskyi.hiltbinder.sample.ksp.java.view.KspJavaViewDep1
import com.paulrybitskyi.hiltbinder.sample.ksp.java.view.KspJavaViewDep10
import com.paulrybitskyi.hiltbinder.sample.ksp.java.view.KspJavaViewDep11
import com.paulrybitskyi.hiltbinder.sample.ksp.java.view.KspJavaViewDep2
import com.paulrybitskyi.hiltbinder.sample.ksp.java.view.KspJavaViewDep3
import com.paulrybitskyi.hiltbinder.sample.ksp.java.view.KspJavaViewDep4
import com.paulrybitskyi.hiltbinder.sample.ksp.java.view.KspJavaViewDep5
import com.paulrybitskyi.hiltbinder.sample.ksp.java.view.KspJavaViewDep6
import com.paulrybitskyi.hiltbinder.sample.ksp.java.view.KspJavaViewDep7
import com.paulrybitskyi.hiltbinder.sample.ksp.java.view.KspJavaViewDep8
import com.paulrybitskyi.hiltbinder.sample.ksp.java.view.KspJavaViewDep9
import javax.inject.Inject
import javax.inject.Named

internal class KspJavaViewDeps @Inject constructor(
    private val kspJavaViewDep1: KspJavaViewDep1,
    private val kspJavaViewDep2: KspJavaViewDep2,
    private val kspJavaViewDep3: KspJavaViewDep3,
    private val kspJavaViewDep4: KspJavaViewDep4,
    private val kspJavaViewDep5: KspJavaViewDep5,
    private val kspJavaViewDeps6: Set<@JvmSuppressWildcards KspJavaViewDep6>,
    private val kspJavaViewDeps7: Map<Long, @JvmSuppressWildcards KspJavaViewDep7>,
    @Named("dep8") private val kspJavaViewDep8: KspJavaViewDep8,
    private val kspJavaViewDep9: KspJavaViewDep9<Double>,
    private val kspJavaViewDep10: KspJavaViewDep10<Double>,
    private val kspJavaViewDeps11: Set<@JvmSuppressWildcards KspJavaViewDep11<*>>,
) {

    fun check() {
        check(kspJavaViewDeps6.size == 3)
        check(kspJavaViewDeps7.size == 3)
        check(kspJavaViewDeps11.size == 3)
    }
}
