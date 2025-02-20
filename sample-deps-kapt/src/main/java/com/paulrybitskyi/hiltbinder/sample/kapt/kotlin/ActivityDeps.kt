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

package com.paulrybitskyi.hiltbinder.sample.kapt.kotlin

import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.hiltbinder.keys.MapStringKey
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Named

// ######################################################################################################

interface KaptKotlinActivityDep1

@BindType(installIn = BindType.Component.ACTIVITY)
internal class KaptKotlinActivityDep1Impl @Inject constructor() : KaptKotlinActivityDep1

// ######################################################################################################

interface KaptKotlinActivityDep2

@BindType(
    to = KaptKotlinActivityDep2::class,
    installIn = BindType.Component.ACTIVITY,
)
internal class KaptKotlinActivityDep2Impl @Inject constructor() : KaptKotlinActivityDep2

// ######################################################################################################

interface KaptKotlinActivityDep3

@ActivityScoped
@BindType
internal class KaptKotlinActivityDep3Impl @Inject constructor() : KaptKotlinActivityDep3

// ######################################################################################################

interface KaptKotlinActivityDep4

@ActivityScoped
@BindType(to = KaptKotlinActivityDep4::class)
internal class KaptKotlinActivityDep4Impl @Inject constructor() : KaptKotlinActivityDep4

// ######################################################################################################

interface KaptKotlinActivityDep5

@ActivityScoped
@BindType(
    to = KaptKotlinActivityDep5::class,
    installIn = BindType.Component.ACTIVITY,
)
internal class KaptKotlinActivityDep5Impl @Inject constructor() : KaptKotlinActivityDep5

// ######################################################################################################

interface KaptKotlinActivityDep6

@ActivityScoped
@BindType(
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.SET,
)
internal class KaptKotlinActivityDep6Impl1 @Inject constructor() : KaptKotlinActivityDep6

@ActivityScoped
@BindType(
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.SET,
)
internal class KaptKotlinActivityDep6Impl2 @Inject constructor() : KaptKotlinActivityDep6

@ActivityScoped
@BindType(
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.SET,
)
internal class KaptKotlinActivityDep6Impl3 @Inject constructor() : KaptKotlinActivityDep6

// ######################################################################################################

interface KaptKotlinActivityDep7

@BindType(
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.MAP,
)
@MapStringKey("dep1_1")
internal class KaptKotlinActivityDep7Impl1 @Inject constructor() : KaptKotlinActivityDep7

@BindType(
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.MAP,
)
@MapStringKey("dep1_2")
internal class KaptKotlinActivityDep7Impl2 @Inject constructor() : KaptKotlinActivityDep7

@BindType(
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.MAP,
)
@MapStringKey("dep1_3")
internal class KaptKotlinActivityDep7Impl3 @Inject constructor() : KaptKotlinActivityDep7

// ######################################################################################################

interface KaptKotlinActivityDep8

@BindType(
    installIn = BindType.Component.ACTIVITY,
    withQualifier = true,
)
@Named("dep8")
internal class KaptKotlinActivityDep8Impl @Inject constructor() : KaptKotlinActivityDep8

// ######################################################################################################

abstract class KaptKotlinActivityDep9<T>

@BindType(installIn = BindType.Component.ACTIVITY)
internal class KaptKotlinActivityDep9Impl @Inject constructor() : KaptKotlinActivityDep9<Float>()

// ######################################################################################################

abstract class KaptKotlinActivityDep10<T>

@BindType(
    to = KaptKotlinActivityDep10::class,
    installIn = BindType.Component.ACTIVITY,
)
internal class KaptKotlinActivityDep10Impl @Inject constructor() : KaptKotlinActivityDep10<Float>()

// ######################################################################################################

abstract class KaptKotlinActivityDep11<T>

@BindType(
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.SET,
)
internal class KaptKotlinActivityDep11Impl1 @Inject constructor() : KaptKotlinActivityDep11<Float>()

@BindType(
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.SET,
)
internal class KaptKotlinActivityDep11Impl2 @Inject constructor() : KaptKotlinActivityDep11<Float>()

@BindType(
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.SET,
)
internal class KaptKotlinActivityDep11Impl3 @Inject constructor() : KaptKotlinActivityDep11<Float>()
