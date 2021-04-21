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

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.paulrybitskyi.hiltbinder.sample.deps.*
import com.paulrybitskyi.hiltbinder.sample.deps.SingletonDep1
import com.paulrybitskyi.hiltbinder.sample.deps.SingletonDep2
import com.paulrybitskyi.hiltbinder.sample.deps.SingletonDep3
import com.paulrybitskyi.hiltbinder.sample.deps.SingletonDep6
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
internal class SampleApplication : Application() {

    @Inject lateinit var singletonDep1: SingletonDep1
    @Inject lateinit var singletonDep2: SingletonDep2
    @Inject lateinit var singletonDep3: SingletonDep3<Int>
    @Inject lateinit var singletonDep4: SingletonDep4<Float>
    @Inject lateinit var singletonDep5: SingletonDep5<AppCompatActivity, Fragment>
    @Inject lateinit var singletonDep6: SingletonDep6<Fragment, AppCompatActivity>

    @Inject lateinit var singletonDeps7: Set<@JvmSuppressWildcards SingletonDep7<*>>
    @Inject lateinit var singletonDeps8: Map<Class<*>, @JvmSuppressWildcards SingletonDep8<*, *>>

}