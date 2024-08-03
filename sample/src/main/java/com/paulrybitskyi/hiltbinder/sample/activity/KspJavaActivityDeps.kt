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

package com.paulrybitskyi.hiltbinder.sample.activity

import com.paulrybitskyi.hiltbinder.sample.ksp.java.activity.KspJavaActivityDep1
import com.paulrybitskyi.hiltbinder.sample.ksp.java.activity.KspJavaActivityDep10
import com.paulrybitskyi.hiltbinder.sample.ksp.java.activity.KspJavaActivityDep11
import com.paulrybitskyi.hiltbinder.sample.ksp.java.activity.KspJavaActivityDep2
import com.paulrybitskyi.hiltbinder.sample.ksp.java.activity.KspJavaActivityDep3
import com.paulrybitskyi.hiltbinder.sample.ksp.java.activity.KspJavaActivityDep4
import com.paulrybitskyi.hiltbinder.sample.ksp.java.activity.KspJavaActivityDep5
import com.paulrybitskyi.hiltbinder.sample.ksp.java.activity.KspJavaActivityDep6
import com.paulrybitskyi.hiltbinder.sample.ksp.java.activity.KspJavaActivityDep7
import com.paulrybitskyi.hiltbinder.sample.ksp.java.activity.KspJavaActivityDep8
import com.paulrybitskyi.hiltbinder.sample.ksp.java.activity.KspJavaActivityDep9
import javax.inject.Inject
import javax.inject.Named

internal class KspJavaActivityDeps @Inject constructor(
    private val kspJavaActivityDep1: KspJavaActivityDep1,
    private val kspJavaActivityDep2: KspJavaActivityDep2,
    private val kspJavaActivityDep3: KspJavaActivityDep3,
    private val kspJavaActivityDep4: KspJavaActivityDep4,
    private val kspJavaActivityDep5: KspJavaActivityDep5,
    private val kspJavaActivityDeps6: Set<@JvmSuppressWildcards KspJavaActivityDep6>,
    private val kspJavaActivityDeps7: Map<String, @JvmSuppressWildcards KspJavaActivityDep7>,
    @Named("dep8") private val kspJavaActivityDep8: KspJavaActivityDep8,
    private val kspJavaActivityDep9: KspJavaActivityDep9<Float>,
    private val kspJavaActivityDep10: KspJavaActivityDep10<Float>,
    private val kspJavaActivityDeps11: Set<@JvmSuppressWildcards KspJavaActivityDep11<*>>,
) {

    fun check() {
        check(kspJavaActivityDeps6.size == 3)
        check(kspJavaActivityDeps7.size == 3)
        check(kspJavaActivityDeps11.size == 3)
    }
}
