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
import com.paulrybitskyi.hiltbinder.keys.MapIntKey
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Named



interface KspKotlinViewModelDep1

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class KspKotlinViewModelDep1Impl @Inject constructor(): KspKotlinViewModelDep1



interface KspKotlinViewModelDep2

@BindType(
    to = KspKotlinViewModelDep2::class,
    installIn = BindType.Component.VIEW_MODEL
)
internal class KspKotlinViewModelDep2Impl @Inject constructor(): KspKotlinViewModelDep2



interface KspKotlinViewModelDep3

@ViewModelScoped
@BindType
internal class KspKotlinViewModelDep3Impl @Inject constructor(): KspKotlinViewModelDep3



interface KspKotlinViewModelDep4

@ViewModelScoped
@BindType(to = KspKotlinViewModelDep4::class)
internal class KspKotlinViewModelDep4Impl @Inject constructor(): KspKotlinViewModelDep4



interface KspKotlinViewModelDep5

@ViewModelScoped
@BindType(
    to = KspKotlinViewModelDep5::class,
    installIn = BindType.Component.VIEW_MODEL
)
internal class KspKotlinViewModelDep5Impl @Inject constructor(): KspKotlinViewModelDep5



interface KspKotlinViewModelDep6

@ViewModelScoped
@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinViewModelDep6Impl1 @Inject constructor(): KspKotlinViewModelDep6

@ViewModelScoped
@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinViewModelDep6Impl2 @Inject constructor(): KspKotlinViewModelDep6

@ViewModelScoped
@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinViewModelDep6Impl3 @Inject constructor(): KspKotlinViewModelDep6



interface KspKotlinViewModelDep7

@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    contributesTo = BindType.Collection.MAP
)
@MapIntKey(1)
internal class KspKotlinViewModelDep7Impl1 @Inject constructor(): KspKotlinViewModelDep7

@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    contributesTo = BindType.Collection.MAP
)
@MapIntKey(2)
internal class KspKotlinViewModelDep7Impl2 @Inject constructor(): KspKotlinViewModelDep7

@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    contributesTo = BindType.Collection.MAP
)
@MapIntKey(3)
internal class KspKotlinViewModelDep7Impl3 @Inject constructor(): KspKotlinViewModelDep7



interface KspKotlinViewModelDep8

@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    withQualifier = true
)
@Named("dep8")
internal class KspKotlinViewModelDep8Impl @Inject constructor(): KspKotlinViewModelDep8



abstract class KspKotlinViewModelDep9<T>

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class KspKotlinViewModelDep9Impl @Inject constructor(): KspKotlinViewModelDep9<Float>()



abstract class KspKotlinViewModelDep10<T>

@BindType(
    to = KspKotlinViewModelDep10::class,
    installIn = BindType.Component.VIEW_MODEL
)
internal class KspKotlinViewModelDep10Impl @Inject constructor(): KspKotlinViewModelDep10<Float>()



abstract class KspKotlinViewModelDep11<T>

@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinViewModelDep11Impl1 @Inject constructor(): KspKotlinViewModelDep11<Float>()

@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinViewModelDep11Impl2 @Inject constructor(): KspKotlinViewModelDep11<Float>()

@BindType(
    installIn = BindType.Component.VIEW_MODEL,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinViewModelDep11Impl3 @Inject constructor(): KspKotlinViewModelDep11<Float>()