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

import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinFragmentDep1
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinFragmentDep2
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewDep12
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewDep13
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinViewDep14
import javax.inject.Inject

internal class KspKotlinViewWithFragmentDeps @Inject constructor(
    private val kspKotlinViewDep12: KspKotlinViewDep12,
    private val kspKotlinViewDep13: KspKotlinViewDep13,
    private val kspKotlinViewDep14: KspKotlinViewDep14,
    private val kspKotlinFragmentDep1: KspKotlinFragmentDep1,
    private val kspKotlinFragmentDep2: KspKotlinFragmentDep2,
)
