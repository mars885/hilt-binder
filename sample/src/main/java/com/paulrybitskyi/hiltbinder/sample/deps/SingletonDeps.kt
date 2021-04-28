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

package com.paulrybitskyi.hiltbinder.sample.deps

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.hiltbinder.keys.MapClassKey
import javax.inject.Inject
import javax.inject.Singleton


internal interface SingletonDep1

@BindType
internal class SingletonDep1Impl @Inject constructor(): SingletonDep1



internal interface SingletonDep2

@Singleton
@BindType
internal class SingletonDep2Impl @Inject constructor(): SingletonDep2



internal interface SingletonDep3<T>

@BindType
internal class SingletonDep3Impl @Inject constructor(): SingletonDep3<Int>



internal interface SingletonDep4<T>

@BindType(to = SingletonDep4::class)
internal class SingletonDep4Impl @Inject constructor(): SingletonDep4<Float>



internal interface SingletonDep5<T1, T2>

@BindType
internal class SingletonDep5Impl @Inject constructor(): SingletonDep5<AppCompatActivity, Fragment>



internal interface SingletonDep6<T1, T2>

@BindType(to = SingletonDep6::class)
internal class SingletonDep6Impl @Inject constructor(): SingletonDep6<Fragment, AppCompatActivity>



internal interface SingletonDep7<T>

@BindType(contributesTo = BindType.Collection.SET)
internal class SingletonDep7Impl1 @Inject constructor(): SingletonDep7<Int>

@BindType(
    to = SingletonDep7::class,
    contributesTo = BindType.Collection.SET
)
internal class SingletonDep7Impl2 @Inject constructor(): SingletonDep7<Float>

@BindType(contributesTo = BindType.Collection.SET)
internal class SingletonDep7Impl3 @Inject constructor(): SingletonDep7<Double>



internal interface SingletonDep8<T1, T2>

@BindType(contributesTo = BindType.Collection.MAP)
@MapClassKey(SingletonDep8Impl1::class)
internal class SingletonDep8Impl1 @Inject constructor(): SingletonDep8<Int, AppCompatActivity>

@BindType(
    to = SingletonDep8::class,
    contributesTo = BindType.Collection.MAP
)
@MapClassKey(SingletonDep8Impl2::class)
internal class SingletonDep8Impl2 @Inject constructor(): SingletonDep8<Int, Fragment>

@BindType(contributesTo = BindType.Collection.MAP)
@MapClassKey(SingletonDep8Impl3::class)
internal class SingletonDep8Impl3 @Inject constructor(): SingletonDep8<Int, View>