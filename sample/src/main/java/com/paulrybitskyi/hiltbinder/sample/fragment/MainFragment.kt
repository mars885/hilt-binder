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

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.paulrybitskyi.hiltbinder.sample.R
import com.paulrybitskyi.hiltbinder.sample.viewmodel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class MainFragment : Fragment(R.layout.fragment_main) {


    private val viewModel by viewModels<MainFragmentViewModel>()

    @Inject lateinit var javacJavaFragmentDeps: JavacJavaFragmentDeps
    @Inject lateinit var kaptJavaFragmentDeps: KaptJavaFragmentDeps
    @Inject lateinit var kaptKotlinFragmentDeps: KaptKotlinFragmentDeps
    @Inject lateinit var kspJavaFragmentDeps: KspJavaFragmentDeps
    @Inject lateinit var kspKotlinFragmentDeps: KspKotlinFragmentDeps


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.check()
        javacJavaFragmentDeps.check()
        kaptJavaFragmentDeps.check()
        kaptKotlinFragmentDeps.check()
        kspJavaFragmentDeps.check()
        kspKotlinFragmentDeps.check()
    }


}
