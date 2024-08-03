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
import com.paulrybitskyi.hiltbinder.keys.MapIntKey
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Named

// ######################################################################################################

interface KaptKotlinViewModelDep1

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class KaptKotlinViewModelDep1Impl @Inject constructor() : KaptKotlinViewModelDep1

// ######################################################################################################

interface KaptKotlinViewModelDep2

@BindType(
    to = KaptKotlinViewModelDep2::class,
    installIn = BindType.Component.VIEW_MODEL,
)
internal class KaptKotlinViewModelDep2Impl @Inject constructor() : KaptKotlinViewModelDep2

// ######################################################################################################

interface KaptKotlinViewModelDep3

@ViewModelScoped
@BindType
internal class KaptKotlinViewModelDep3Impl @Inject constructor() : KaptKotlinViewModelDep3

// ######################################################################################################

interface KaptKotlinViewModelDep4

@ViewModelScoped
@BindType(to = KaptKotlinViewModelDep4::class)
internal class KaptKotlinViewModelDep4Impl @Inject constructor() : KaptKotlinViewModelDep4

// ######################################################################################################

interface KaptKotlinViewModelDep5

@ViewModelScoped
@BindType(
    to = KaptKotlinViewModelDep5::class,
    installIn = BindType.Component.VIEW_MODEL,
)
internal class KaptKotlinViewModelDep5Impl @Inject constructor() : KaptKotlinViewModelDep5

// ######################################################################################################

interface KaptKotlinViewModelDep6

@ViewModelScoped
@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    contributesTo = BindType.Collection.SET,
)
internal class KaptKotlinViewModelDep6Impl1 @Inject constructor() : KaptKotlinViewModelDep6

@ViewModelScoped
@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    contributesTo = BindType.Collection.SET,
)
internal class KaptKotlinViewModelDep6Impl2 @Inject constructor() : KaptKotlinViewModelDep6

@ViewModelScoped
@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    contributesTo = BindType.Collection.SET,
)
internal class KaptKotlinViewModelDep6Impl3 @Inject constructor() : KaptKotlinViewModelDep6

// ######################################################################################################

interface KaptKotlinViewModelDep7

@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    contributesTo = BindType.Collection.MAP,
)
@MapIntKey(1)
internal class KaptKotlinViewModelDep7Impl1 @Inject constructor() : KaptKotlinViewModelDep7

@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    contributesTo = BindType.Collection.MAP,
)
@MapIntKey(2)
internal class KaptKotlinViewModelDep7Impl2 @Inject constructor() : KaptKotlinViewModelDep7

@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    contributesTo = BindType.Collection.MAP,
)
@MapIntKey(3)
internal class KaptKotlinViewModelDep7Impl3 @Inject constructor() : KaptKotlinViewModelDep7

// ######################################################################################################

interface KaptKotlinViewModelDep8

@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    withQualifier = true,
)
@Named("dep8")
internal class KaptKotlinViewModelDep8Impl @Inject constructor() : KaptKotlinViewModelDep8

// ######################################################################################################

abstract class KaptKotlinViewModelDep9<T>

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class KaptKotlinViewModelDep9Impl @Inject constructor() : KaptKotlinViewModelDep9<Float>()

// ######################################################################################################

abstract class KaptKotlinViewModelDep10<T>

@BindType(
    to = KaptKotlinViewModelDep10::class,
    installIn = BindType.Component.VIEW_MODEL,
)
internal class KaptKotlinViewModelDep10Impl @Inject constructor() : KaptKotlinViewModelDep10<Float>()

// ######################################################################################################

abstract class KaptKotlinViewModelDep11<T>

@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    contributesTo = BindType.Collection.SET,
)
internal class KaptKotlinViewModelDep11Impl1 @Inject constructor() : KaptKotlinViewModelDep11<Float>()

@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    contributesTo = BindType.Collection.SET,
)
internal class KaptKotlinViewModelDep11Impl2 @Inject constructor() : KaptKotlinViewModelDep11<Float>()

@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    contributesTo = BindType.Collection.SET,
)
internal class KaptKotlinViewModelDep11Impl3 @Inject constructor() : KaptKotlinViewModelDep11<Float>()
