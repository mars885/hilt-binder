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

package com.paulrybitskyi.hiltbinder.sample

import androidx.lifecycle.ViewModel
import com.paulrybitskyi.hiltbinder.sample.deps.ViewModelDep1
import com.paulrybitskyi.hiltbinder.sample.deps.ViewModelDep2
import com.paulrybitskyi.hiltbinder.sample.deps.ViewModelDep3
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainFragmentViewModel @Inject constructor(
    private val dep1: ViewModelDep1,
    private val dep2: ViewModelDep2,
    private val dep3: ViewModelDep3
): ViewModel() {


    fun testCallback() {
        // ignore
    }


}