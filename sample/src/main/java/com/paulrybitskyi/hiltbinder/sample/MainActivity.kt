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

import androidx.appcompat.app.AppCompatActivity
import com.paulrybitskyi.hiltbinder.sample.deps.*
import com.paulrybitskyi.hiltbinder.sample.deps.ActivityDep1
import com.paulrybitskyi.hiltbinder.sample.deps.ActivityDep2
import com.paulrybitskyi.hiltbinder.sample.deps.ActivityDep3
import com.paulrybitskyi.hiltbinder.sample.deps.ActivityDep4
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
internal class MainActivity : AppCompatActivity(R.layout.activity_main) {


    @Inject lateinit var singletonDep1: SingletonDep1
    @Inject lateinit var singletonDep2: SingletonDep2

    @Inject lateinit var dep1: ActivityDep1
    @Inject lateinit var dep2: ActivityDep2
    @Named("dep3") @Inject lateinit var dep3: ActivityDep3
    @Named("dep4") @Inject lateinit var dep4: ActivityDep4
    @Inject lateinit var superDeps: Set<@JvmSuppressWildcards ActivitySuperDep>


}