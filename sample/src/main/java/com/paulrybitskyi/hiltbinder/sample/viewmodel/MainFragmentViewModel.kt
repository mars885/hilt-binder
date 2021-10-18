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

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainFragmentViewModel @Inject constructor(
    private val javacJavaDeps: JavacJavaViewModelDeps,
    private val kaptJavaDeps: KaptJavaViewModelDeps,
    private val kaptKotlinDeps: KaptKotlinViewModelDeps,
    private val kspJavaDeps: KspJavaViewModelDeps,
    private val kspKotlinDeps: KspKotlinViewModelDeps
): ViewModel() {


    fun check() {
        javacJavaDeps.check()
        kaptJavaDeps.check()
        kaptKotlinDeps.check()
        kspJavaDeps.check()
        kspKotlinDeps.check()
    }


}
