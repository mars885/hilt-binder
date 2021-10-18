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
import com.paulrybitskyi.hiltbinder.keys.MapStringKey
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Named



interface KspKotlinActivityDep1

@BindType(installIn = BindType.Component.ACTIVITY)
internal class KspKotlinActivityDep1Impl @Inject constructor(): KspKotlinActivityDep1



interface KspKotlinActivityDep2

@BindType(
    to = KspKotlinActivityDep2::class,
    installIn = BindType.Component.ACTIVITY
)
internal class KspKotlinActivityDep2Impl @Inject constructor(): KspKotlinActivityDep2



interface KspKotlinActivityDep3

@ActivityScoped
@BindType
internal class KspKotlinActivityDep3Impl @Inject constructor(): KspKotlinActivityDep3



interface KspKotlinActivityDep4

@ActivityScoped
@BindType(to = KspKotlinActivityDep4::class)
internal class KspKotlinActivityDep4Impl @Inject constructor(): KspKotlinActivityDep4



interface KspKotlinActivityDep5

@ActivityScoped
@BindType(
    to = KspKotlinActivityDep5::class,
    installIn = BindType.Component.ACTIVITY
)
internal class KspKotlinActivityDep5Impl @Inject constructor(): KspKotlinActivityDep5



interface KspKotlinActivityDep6

@ActivityScoped
@BindType(
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinActivityDep6Impl1 @Inject constructor(): KspKotlinActivityDep6

@ActivityScoped
@BindType(
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinActivityDep6Impl2 @Inject constructor(): KspKotlinActivityDep6

@ActivityScoped
@BindType(
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinActivityDep6Impl3 @Inject constructor(): KspKotlinActivityDep6



interface KspKotlinActivityDep7

@BindType(
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.MAP
)
@MapStringKey("dep1_1")
internal class KspKotlinActivityDep7Impl1 @Inject constructor(): KspKotlinActivityDep7

@BindType(
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.MAP
)
@MapStringKey("dep1_2")
internal class KspKotlinActivityDep7Impl2 @Inject constructor(): KspKotlinActivityDep7

@BindType(
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.MAP
)
@MapStringKey("dep1_3")
internal class KspKotlinActivityDep7Impl3 @Inject constructor(): KspKotlinActivityDep7



interface KspKotlinActivityDep8

@BindType(
    installIn = BindType.Component.ACTIVITY,
    withQualifier = true
)
@Named("dep8")
internal class KspKotlinActivityDep8Impl @Inject constructor(): KspKotlinActivityDep8



abstract class KspKotlinActivityDep9<T>

@BindType(installIn = BindType.Component.ACTIVITY)
internal class KspKotlinActivityDep9Impl @Inject constructor(): KspKotlinActivityDep9<Float>()



abstract class KspKotlinActivityDep10<T>

@BindType(
    to = KspKotlinActivityDep10::class,
    installIn = BindType.Component.ACTIVITY
)
internal class KspKotlinActivityDep10Impl @Inject constructor(): KspKotlinActivityDep10<Float>()



abstract class KspKotlinActivityDep11<T>

@BindType(
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinActivityDep11Impl1 @Inject constructor(): KspKotlinActivityDep11<Float>()

@BindType(
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinActivityDep11Impl2 @Inject constructor(): KspKotlinActivityDep11<Float>()

@BindType(
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.SET
)
internal class KspKotlinActivityDep11Impl3 @Inject constructor(): KspKotlinActivityDep11<Float>()
