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

package com.paulrybitskyi.hiltbinder.sample.application

import com.paulrybitskyi.hiltbinder.sample.ksp.java.singleton.KspJavaSingletonDep1
import com.paulrybitskyi.hiltbinder.sample.ksp.java.singleton.KspJavaSingletonDep10
import com.paulrybitskyi.hiltbinder.sample.ksp.java.singleton.KspJavaSingletonDep11
import com.paulrybitskyi.hiltbinder.sample.ksp.java.singleton.KspJavaSingletonDep12
import com.paulrybitskyi.hiltbinder.sample.ksp.java.singleton.KspJavaSingletonDep13
import com.paulrybitskyi.hiltbinder.sample.ksp.java.singleton.KspJavaSingletonDep2
import com.paulrybitskyi.hiltbinder.sample.ksp.java.singleton.KspJavaSingletonDep3
import com.paulrybitskyi.hiltbinder.sample.ksp.java.singleton.KspJavaSingletonDep4
import com.paulrybitskyi.hiltbinder.sample.ksp.java.singleton.KspJavaSingletonDep5
import com.paulrybitskyi.hiltbinder.sample.ksp.java.singleton.KspJavaSingletonDep6
import com.paulrybitskyi.hiltbinder.sample.ksp.java.singleton.KspJavaSingletonDep7
import com.paulrybitskyi.hiltbinder.sample.ksp.java.singleton.KspJavaSingletonDep8
import com.paulrybitskyi.hiltbinder.sample.ksp.java.singleton.KspJavaSingletonDep9
import javax.inject.Inject
import javax.inject.Named

internal class KspJavaSingletonDeps @Inject constructor(
    private val kspJavaSingletonDep1: KspJavaSingletonDep1,
    private val kspJavaSingletonDep2: KspJavaSingletonDep2,
    private val kspJavaSingletonDep3: KspJavaSingletonDep3,
    private val kspJavaSingletonDep4: KspJavaSingletonDep4,
    private val kspJavaSingletonDep5: KspJavaSingletonDep5,
    private val kspJavaSingletonDep6: KspJavaSingletonDep6,
    private val kspJavaSingletonDep7: KspJavaSingletonDep7,
    private val kspJavaSingletonDeps8: Set<@JvmSuppressWildcards KspJavaSingletonDep8>,
    private val kspJavaSingletonDeps9: Map<Class<*>, @JvmSuppressWildcards KspJavaSingletonDep9>,
    @Named("dep10") private val kspJavaSingletonDep10: KspJavaSingletonDep10,
    private val kspJavaSingletonDep11: KspJavaSingletonDep11<Int>,
    private val kspJavaSingletonDep12: KspJavaSingletonDep12<Int>,
    private val kspJavaSingletonDeps13: Set<@JvmSuppressWildcards KspJavaSingletonDep13<*>>,
) {

    fun check() {
        check(kspJavaSingletonDeps8.size == 3)
        check(kspJavaSingletonDeps9.size == 3)
        check(kspJavaSingletonDeps13.size == 3)
    }
}
