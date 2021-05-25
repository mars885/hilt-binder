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

package com.paulrybitskyi.hiltbinder.sample.ksp.kotlin

import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.hiltbinder.keys.MapLongKey
import dagger.hilt.android.WithFragmentBindings
import dagger.hilt.android.scopes.ViewScoped
import javax.inject.Inject
import javax.inject.Named



interface KspKotlinViewDep1

@BindType(installIn = BindType.Component.VIEW)
internal class KspKotlinViewDep1Impl @Inject constructor(): KspKotlinViewDep1



interface KspKotlinViewDep2

@BindType(to = KspKotlinViewDep2::class, installIn = BindType.Component.VIEW)
internal class KspKotlinViewDep2Impl @Inject constructor(): KspKotlinViewDep2



interface KspKotlinViewDep3

@ViewScoped
@BindType
internal class KspKotlinViewDep3Impl @Inject constructor(): KspKotlinViewDep3



interface KspKotlinViewDep4

@ViewScoped
@BindType(to = KspKotlinViewDep4::class)
internal class KspKotlinViewDep4Impl @Inject constructor(): KspKotlinViewDep4



interface KspKotlinViewDep5

@ViewScoped
@BindType(
    to = KspKotlinViewDep5::class,
    installIn = BindType.Component.VIEW
)
internal class KspKotlinViewDep5Impl @Inject constructor(): KspKotlinViewDep5



interface KspKotlinViewDep6

@ViewScoped
@BindType(
    installIn = BindType.Component.VIEW,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinViewDep6Impl1 @Inject constructor(): KspKotlinViewDep6

@ViewScoped
@BindType(
    installIn = BindType.Component.VIEW,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinViewDep6Impl2 @Inject constructor(): KspKotlinViewDep6

@ViewScoped
@BindType(
    installIn = BindType.Component.VIEW,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinViewDep6Impl3 @Inject constructor(): KspKotlinViewDep6



interface KspKotlinViewDep7

@BindType(
    installIn = BindType.Component.VIEW,
    contributesTo = BindType.Collection.MAP
)
@MapLongKey(1L)
internal class KspKotlinViewDep7Impl1 @Inject constructor(): KspKotlinViewDep7

@BindType(
    installIn = BindType.Component.VIEW,
    contributesTo = BindType.Collection.MAP
)
@MapLongKey(2L)
internal class KspKotlinViewDep7Impl2 @Inject constructor(): KspKotlinViewDep7

@BindType(
    installIn = BindType.Component.VIEW,
    contributesTo = BindType.Collection.MAP
)
@MapLongKey(3L)
internal class KspKotlinViewDep7Impl3 @Inject constructor(): KspKotlinViewDep7



interface KspKotlinViewDep8

@BindType(
    installIn = BindType.Component.VIEW,
    withQualifier = true
)
@Named("dep8")
internal class KspKotlinViewDep8Impl @Inject constructor(): KspKotlinViewDep8



abstract class KspKotlinViewDep9<T>

@BindType(installIn = BindType.Component.VIEW)
internal class KspKotlinViewDep9Impl @Inject constructor(): KspKotlinViewDep9<Double>()



abstract class KspKotlinViewDep10<T>

@BindType(
    to = KspKotlinViewDep10::class,
    installIn = BindType.Component.VIEW
)
internal class KspKotlinViewDep10Impl @Inject constructor(): KspKotlinViewDep10<Double>()



abstract class KspKotlinViewDep11<T>

@BindType(
    installIn = BindType.Component.VIEW,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinViewDep11Impl1 @Inject constructor(): KspKotlinViewDep11<Float>()

@BindType(
    installIn = BindType.Component.VIEW,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinViewDep11Impl2 @Inject constructor(): KspKotlinViewDep11<Float>()

@BindType(
    installIn = BindType.Component.VIEW,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinViewDep11Impl3 @Inject constructor(): KspKotlinViewDep11<Float>()



interface KspKotlinViewDep12

@BindType(installIn = BindType.Component.VIEW_WITH_FRAGMENT)
internal class KspKotlinViewDep12Impl @Inject constructor(): KspKotlinViewDep12



interface KspKotlinViewDep13

@ViewScoped
@dagger.hilt.android.WithFragmentBindings
@BindType
internal class KspKotlinViewDep13Impl @Inject constructor(): KspKotlinViewDep13



abstract class KspKotlinViewDep14

@ViewScoped
@WithFragmentBindings
@BindType(installIn = BindType.Component.VIEW_WITH_FRAGMENT)
internal class KspKotlinViewDep14Impl @Inject constructor(): KspKotlinViewDep14()