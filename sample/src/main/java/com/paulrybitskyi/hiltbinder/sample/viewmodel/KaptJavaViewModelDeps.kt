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

package com.paulrybitskyi.hiltbinder.sample.viewmodel

import com.paulrybitskyi.hiltbinder.sample.kapt.java.viewmodel.KaptJavaViewModelDep1
import com.paulrybitskyi.hiltbinder.sample.kapt.java.viewmodel.KaptJavaViewModelDep10
import com.paulrybitskyi.hiltbinder.sample.kapt.java.viewmodel.KaptJavaViewModelDep11
import com.paulrybitskyi.hiltbinder.sample.kapt.java.viewmodel.KaptJavaViewModelDep2
import com.paulrybitskyi.hiltbinder.sample.kapt.java.viewmodel.KaptJavaViewModelDep3
import com.paulrybitskyi.hiltbinder.sample.kapt.java.viewmodel.KaptJavaViewModelDep4
import com.paulrybitskyi.hiltbinder.sample.kapt.java.viewmodel.KaptJavaViewModelDep5
import com.paulrybitskyi.hiltbinder.sample.kapt.java.viewmodel.KaptJavaViewModelDep6
import com.paulrybitskyi.hiltbinder.sample.kapt.java.viewmodel.KaptJavaViewModelDep7
import com.paulrybitskyi.hiltbinder.sample.kapt.java.viewmodel.KaptJavaViewModelDep8
import com.paulrybitskyi.hiltbinder.sample.kapt.java.viewmodel.KaptJavaViewModelDep9
import javax.inject.Inject
import javax.inject.Named

internal class KaptJavaViewModelDeps @Inject constructor(
    private val kaptJavaViewModelDep1: KaptJavaViewModelDep1,
    private val kaptJavaViewModelDep2: KaptJavaViewModelDep2,
    private val kaptJavaViewModelDep3: KaptJavaViewModelDep3,
    private val kaptJavaViewModelDep4: KaptJavaViewModelDep4,
    private val kaptJavaViewModelDep5: KaptJavaViewModelDep5,
    private val kaptJavaViewModelDeps6: Set<@JvmSuppressWildcards KaptJavaViewModelDep6>,
    private val kaptJavaViewModelDeps7: Map<Int, @JvmSuppressWildcards KaptJavaViewModelDep7>,
    @Named("dep8") private val kaptJavaViewModelDep8: KaptJavaViewModelDep8,
    private val kaptJavaViewModelDep9: KaptJavaViewModelDep9<Float>,
    private val kaptJavaViewModelDep10: KaptJavaViewModelDep10<Float>,
    private val kaptJavaViewModelDeps11: Set<@JvmSuppressWildcards KaptJavaViewModelDep11<*>>
) {


    fun check() {
        check(kaptJavaViewModelDeps6.size == 3)
        check(kaptJavaViewModelDeps7.size == 3)
        check(kaptJavaViewModelDeps11.size == 3)
    }


}
