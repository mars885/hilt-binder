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

package com.paulrybitskyi.hiltbinder.sample.views.withfragment

import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinFragmentDep1
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinFragmentDep2
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewDep12
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewDep13
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinViewDep14
import javax.inject.Inject

internal class KaptKotlinViewWithFragmentDeps @Inject constructor(
    private val kaptKotlinViewDep12: KaptKotlinViewDep12,
    private val kaptKotlinViewDep13: KaptKotlinViewDep13,
    private val kaptKotlinViewDep14: KaptKotlinViewDep14,
    private val kaptKotlinFragmentDep1: KaptKotlinFragmentDep1,
    private val kaptKotlinFragmentDep2: KaptKotlinFragmentDep2,
)
