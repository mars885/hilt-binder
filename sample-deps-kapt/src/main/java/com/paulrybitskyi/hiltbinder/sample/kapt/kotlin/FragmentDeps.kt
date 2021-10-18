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

package com.paulrybitskyi.hiltbinder.sample.kapt.kotlin

import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.hiltbinder.keys.MapLongKey
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import javax.inject.Named



interface KaptKotlinFragmentDep1

@BindType(installIn = BindType.Component.FRAGMENT)
internal class KaptKotlinFragmentDep1Impl @Inject constructor(): KaptKotlinFragmentDep1



interface KaptKotlinFragmentDep2

@BindType(
    to = KaptKotlinFragmentDep2::class,
    installIn = BindType.Component.FRAGMENT
)
internal class KaptKotlinFragmentDep2Impl @Inject constructor(): KaptKotlinFragmentDep2



interface KaptKotlinFragmentDep3

@FragmentScoped
@BindType
internal class KaptKotlinFragmentDep3Impl @Inject constructor(): KaptKotlinFragmentDep3



interface KaptKotlinFragmentDep4

@FragmentScoped
@BindType(to = KaptKotlinFragmentDep4::class)
internal class KaptKotlinFragmentDep4Impl @Inject constructor(): KaptKotlinFragmentDep4



interface KaptKotlinFragmentDep5

@FragmentScoped
@BindType(
    to = KaptKotlinFragmentDep5::class,
    installIn = BindType.Component.FRAGMENT
)
internal class KaptKotlinFragmentDep5Impl @Inject constructor(): KaptKotlinFragmentDep5



interface KaptKotlinFragmentDep6

@FragmentScoped
@BindType(
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.SET
)
internal class KaptKotlinFragmentDep6Impl1 @Inject constructor(): KaptKotlinFragmentDep6

@FragmentScoped
@BindType(
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.SET
)
internal class KaptKotlinFragmentDep6Impl2 @Inject constructor(): KaptKotlinFragmentDep6

@FragmentScoped
@BindType(
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.SET
)
internal class KaptKotlinFragmentDep6Impl3 @Inject constructor(): KaptKotlinFragmentDep6



interface KaptKotlinFragmentDep7

@BindType(
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.MAP
)
@MapLongKey(1L)
internal class KaptKotlinFragmentDep7Impl1 @Inject constructor(): KaptKotlinFragmentDep7

@BindType(
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.MAP
)
@MapLongKey(2L)
internal class KaptKotlinFragmentDep7Impl2 @Inject constructor(): KaptKotlinFragmentDep7

@BindType(
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.MAP
)
@MapLongKey(3L)
internal class KaptKotlinFragmentDep7Impl3 @Inject constructor(): KaptKotlinFragmentDep7



interface KaptKotlinFragmentDep8

@BindType(
    installIn = BindType.Component.FRAGMENT,
    withQualifier = true
)
@Named("dep8")
internal class KaptKotlinFragmentDep8Impl @Inject constructor (): KaptKotlinFragmentDep8



abstract class KaptKotlinFragmentDep9<T>

@BindType(installIn = BindType.Component.FRAGMENT)
internal class KaptKotlinFragmentDep9Impl @Inject constructor(): KaptKotlinFragmentDep9<Float>()



abstract class KaptKotlinFragmentDep10<T>

@BindType(
    to = KaptKotlinFragmentDep10::class,
    installIn = BindType.Component.FRAGMENT
)
internal class KaptKotlinFragmentDep10Impl @Inject constructor(): KaptKotlinFragmentDep10<Float>()



abstract class KaptKotlinFragmentDep11<T>

@BindType(
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.SET
)
internal class KaptKotlinFragmentDep11Impl1 @Inject constructor(): KaptKotlinFragmentDep11<Float>()

@BindType(
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.SET
)
internal class KaptKotlinFragmentDep11Impl2 @Inject constructor(): KaptKotlinFragmentDep11<Float>()

@BindType(
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.SET
)
internal class KaptKotlinFragmentDep11Impl3 @Inject constructor(): KaptKotlinFragmentDep11<Float>()
