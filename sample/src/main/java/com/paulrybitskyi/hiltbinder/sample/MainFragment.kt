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

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.paulrybitskyi.hiltbinder.sample.deps.*
import com.paulrybitskyi.hiltbinder.sample.deps.FragmentDep1
import com.paulrybitskyi.hiltbinder.sample.deps.FragmentDep2
import com.paulrybitskyi.hiltbinder.sample.deps.FragmentDep3
import com.paulrybitskyi.hiltbinder.sample.deps.FragmentDep4
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModels<MainFragmentViewModel>()

    @Inject lateinit var singletonDep1: SingletonDep1
    @Inject lateinit var singletonDep2: SingletonDep2

    @Inject lateinit var dep1: FragmentDep1
    @Inject lateinit var dep2: FragmentDep2
    @DepMapKey(DepType.DEP3) @Inject lateinit var dep3: FragmentDep3
    @DepMapKey(DepType.DEP4) @Inject lateinit var dep4: FragmentDep4
    @Inject lateinit var superDepsMap: Map<String, @JvmSuppressWildcards FragmentSuperDep>


}