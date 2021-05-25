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
import dagger.hilt.android.WithFragmentBindings
import dagger.hilt.android.scopes.ViewScoped
import javax.inject.Inject
import javax.inject.Named



interface KaptKotlinViewDep1

@BindType(installIn = BindType.Component.VIEW)
internal class KaptKotlinViewDep1Impl @Inject constructor(): KaptKotlinViewDep1



interface KaptKotlinViewDep2

@BindType(to = KaptKotlinViewDep2::class, installIn = BindType.Component.VIEW)
internal class KaptKotlinViewDep2Impl @Inject constructor(): KaptKotlinViewDep2



interface KaptKotlinViewDep3

@ViewScoped
@BindType
internal class KaptKotlinViewDep3Impl @Inject constructor(): KaptKotlinViewDep3



interface KaptKotlinViewDep4

@ViewScoped
@BindType(to = KaptKotlinViewDep4::class)
internal class KaptKotlinViewDep4Impl @Inject constructor(): KaptKotlinViewDep4



interface KaptKotlinViewDep5

@ViewScoped
@BindType(
    to = KaptKotlinViewDep5::class,
    installIn = BindType.Component.VIEW
)
internal class KaptKotlinViewDep5Impl @Inject constructor(): KaptKotlinViewDep5



interface KaptKotlinViewDep6

@ViewScoped
@BindType(
    installIn = BindType.Component.VIEW,
    contributesTo = BindType.Collection.SET
)
internal class KaptKotlinViewDep6Impl1 @Inject constructor(): KaptKotlinViewDep6

@ViewScoped
@BindType(
    installIn = BindType.Component.VIEW,
    contributesTo = BindType.Collection.SET
)
internal class KaptKotlinViewDep6Impl2 @Inject constructor(): KaptKotlinViewDep6

@ViewScoped
@BindType(
    installIn = BindType.Component.VIEW,
    contributesTo = BindType.Collection.SET
)
internal class KaptKotlinViewDep6Impl3 @Inject constructor(): KaptKotlinViewDep6



interface KaptKotlinViewDep7

@BindType(
    installIn = BindType.Component.VIEW,
    contributesTo = BindType.Collection.MAP
)
@MapLongKey(1L)
internal class KaptKotlinViewDep7Impl1 @Inject constructor(): KaptKotlinViewDep7

@BindType(
    installIn = BindType.Component.VIEW,
    contributesTo = BindType.Collection.MAP
)
@MapLongKey(2L)
internal class KaptKotlinViewDep7Impl2 @Inject constructor(): KaptKotlinViewDep7

@BindType(
    installIn = BindType.Component.VIEW,
    contributesTo = BindType.Collection.MAP
)
@MapLongKey(3L)
internal class KaptKotlinViewDep7Impl3 @Inject constructor(): KaptKotlinViewDep7



interface KaptKotlinViewDep8

@BindType(
    installIn = BindType.Component.VIEW,
    withQualifier = true
)
@Named("dep8")
internal class KaptKotlinViewDep8Impl @Inject constructor(): KaptKotlinViewDep8



abstract class KaptKotlinViewDep9<T>

@BindType(installIn = BindType.Component.VIEW)
internal class KaptKotlinViewDep9Impl @Inject constructor(): KaptKotlinViewDep9<Double>()



abstract class KaptKotlinViewDep10<T>

@BindType(
    to = KaptKotlinViewDep10::class,
    installIn = BindType.Component.VIEW
)
internal class KaptKotlinViewDep10Impl @Inject constructor(): KaptKotlinViewDep10<Double>()



abstract class KaptKotlinViewDep11<T>

@BindType(
    installIn = BindType.Component.VIEW,
    contributesTo = BindType.Collection.SET
)
internal class KaptKotlinViewDep11Impl1 @Inject constructor(): KaptKotlinViewDep11<Float>()

@BindType(
    installIn = BindType.Component.VIEW,
    contributesTo = BindType.Collection.SET
)
internal class KaptKotlinViewDep11Impl2 @Inject constructor(): KaptKotlinViewDep11<Float>()

@BindType(
    installIn = BindType.Component.VIEW,
    contributesTo = BindType.Collection.SET
)
internal class KaptKotlinViewDep11Impl3 @Inject constructor(): KaptKotlinViewDep11<Float>()



interface KaptKotlinViewDep12

@BindType(installIn = BindType.Component.VIEW_WITH_FRAGMENT)
internal class KaptKotlinViewDep12Impl @Inject constructor(): KaptKotlinViewDep12



interface KaptKotlinViewDep13

@ViewScoped
@dagger.hilt.android.WithFragmentBindings
@BindType
internal class KaptKotlinViewDep13Impl @Inject constructor(): KaptKotlinViewDep13



abstract class KaptKotlinViewDep14

@ViewScoped
@WithFragmentBindings
@BindType(installIn = BindType.Component.VIEW_WITH_FRAGMENT)
internal class KaptKotlinViewDep14Impl @Inject constructor(): KaptKotlinViewDep14()