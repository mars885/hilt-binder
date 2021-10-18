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
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import javax.inject.Named

// ######################################################################################################

interface KspKotlinFragmentDep1

@BindType(installIn = BindType.Component.FRAGMENT)
internal class KspKotlinFragmentDep1Impl @Inject constructor() : KspKotlinFragmentDep1

// ######################################################################################################

interface KspKotlinFragmentDep2

@BindType(
    to = KspKotlinFragmentDep2::class,
    installIn = BindType.Component.FRAGMENT
)
internal class KspKotlinFragmentDep2Impl @Inject constructor() : KspKotlinFragmentDep2

// ######################################################################################################

interface KspKotlinFragmentDep3

@FragmentScoped
@BindType
internal class KspKotlinFragmentDep3Impl @Inject constructor() : KspKotlinFragmentDep3

// ######################################################################################################

interface KspKotlinFragmentDep4

@FragmentScoped
@BindType(to = KspKotlinFragmentDep4::class)
internal class KspKotlinFragmentDep4Impl @Inject constructor() : KspKotlinFragmentDep4

// ######################################################################################################

interface KspKotlinFragmentDep5

@FragmentScoped
@BindType(
    to = KspKotlinFragmentDep5::class,
    installIn = BindType.Component.FRAGMENT
)
internal class KspKotlinFragmentDep5Impl @Inject constructor() : KspKotlinFragmentDep5

// ######################################################################################################

interface KspKotlinFragmentDep6

@FragmentScoped
@BindType(
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinFragmentDep6Impl1 @Inject constructor() : KspKotlinFragmentDep6

@FragmentScoped
@BindType(
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinFragmentDep6Impl2 @Inject constructor() : KspKotlinFragmentDep6

@FragmentScoped
@BindType(
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinFragmentDep6Impl3 @Inject constructor() : KspKotlinFragmentDep6

// ######################################################################################################

interface KspKotlinFragmentDep7

@BindType(
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.MAP
)
@MapLongKey(1L)
internal class KspKotlinFragmentDep7Impl1 @Inject constructor() : KspKotlinFragmentDep7

@BindType(
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.MAP
)
@MapLongKey(2L)
internal class KspKotlinFragmentDep7Impl2 @Inject constructor() : KspKotlinFragmentDep7

@BindType(
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.MAP
)
@MapLongKey(3L)
internal class KspKotlinFragmentDep7Impl3 @Inject constructor() : KspKotlinFragmentDep7

// ######################################################################################################

interface KspKotlinFragmentDep8

@BindType(
    installIn = BindType.Component.FRAGMENT,
    withQualifier = true
)
@Named("dep8")
internal class KspKotlinFragmentDep8Impl @Inject constructor () : KspKotlinFragmentDep8

// ######################################################################################################

abstract class KspKotlinFragmentDep9<T>

@BindType(installIn = BindType.Component.FRAGMENT)
internal class KspKotlinFragmentDep9Impl @Inject constructor() : KspKotlinFragmentDep9<Float>()

// ######################################################################################################

abstract class KspKotlinFragmentDep10<T>

@BindType(
    to = KspKotlinFragmentDep10::class,
    installIn = BindType.Component.FRAGMENT
)
internal class KspKotlinFragmentDep10Impl @Inject constructor() : KspKotlinFragmentDep10<Float>()

// ######################################################################################################

abstract class KspKotlinFragmentDep11<T>

@BindType(
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinFragmentDep11Impl1 @Inject constructor() : KspKotlinFragmentDep11<Float>()

@BindType(
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinFragmentDep11Impl2 @Inject constructor() : KspKotlinFragmentDep11<Float>()

@BindType(
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinFragmentDep11Impl3 @Inject constructor() : KspKotlinFragmentDep11<Float>()
