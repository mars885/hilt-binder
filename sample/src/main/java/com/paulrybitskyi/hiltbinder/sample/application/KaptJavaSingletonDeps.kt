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

package com.paulrybitskyi.hiltbinder.sample.application

import com.paulrybitskyi.hiltbinder.sample.kapt.java.singleton.*
import javax.inject.Inject
import javax.inject.Named

internal class KaptJavaSingletonDeps @Inject constructor(
    private val kaptJavaSingletonDep1: KaptJavaSingletonDep1,
    private val kaptJavaSingletonDep2: KaptJavaSingletonDep2,
    private val kaptJavaSingletonDep3: KaptJavaSingletonDep3,
    private val kaptJavaSingletonDep4: KaptJavaSingletonDep4,
    private val kaptJavaSingletonDep5: KaptJavaSingletonDep5,
    private val kaptJavaSingletonDep6: KaptJavaSingletonDep6,
    private val kaptJavaSingletonDep7: KaptJavaSingletonDep7,
    private val kaptJavaSingletonDeps8: Set<@JvmSuppressWildcards KaptJavaSingletonDep8>,
    private val kaptJavaSingletonDeps9: Map<Class<*>, @JvmSuppressWildcards KaptJavaSingletonDep9>,
    @Named("dep10") private val kaptJavaSingletonDep10: KaptJavaSingletonDep10,
    private val kaptJavaSingletonDep11: KaptJavaSingletonDep11<Int>,
    private val kaptJavaSingletonDep12: KaptJavaSingletonDep12<Int>,
    private val kaptJavaSingletonDeps13: Set<@JvmSuppressWildcards KaptJavaSingletonDep13<*>>
) {


    fun check() {
        check(kaptJavaSingletonDeps8.size == 3)
        check(kaptJavaSingletonDeps9.size == 3)
        check(kaptJavaSingletonDeps13.size == 3)
    }


}