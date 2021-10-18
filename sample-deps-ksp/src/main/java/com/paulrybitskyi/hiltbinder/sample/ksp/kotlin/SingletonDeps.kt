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
import com.paulrybitskyi.hiltbinder.keys.MapClassKey
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

// ######################################################################################################

interface KspKotlinSingletonDep1

@BindType
internal class KspKotlinSingletonDep1Impl @Inject constructor() : KspKotlinSingletonDep1

// ######################################################################################################

interface KspKotlinSingletonDep2

@BindType(to = KspKotlinSingletonDep2::class)
internal class KspKotlinSingletonDep2Impl @Inject constructor() : KspKotlinSingletonDep2

// ######################################################################################################

interface KspKotlinSingletonDep3

@BindType(installIn = BindType.Component.SINGLETON)
internal class KspKotlinSingletonDep3Impl @Inject constructor() : KspKotlinSingletonDep3

// ######################################################################################################

interface KspKotlinSingletonDep4

@BindType(
    to = KspKotlinSingletonDep4::class,
    installIn = BindType.Component.SINGLETON
)
internal class KspKotlinSingletonDep4Impl @Inject constructor() : KspKotlinSingletonDep4

// ######################################################################################################

interface KspKotlinSingletonDep5

@Singleton
@BindType
internal class KspKotlinSingletonDep5Impl @Inject constructor() : KspKotlinSingletonDep5

// ######################################################################################################

interface KspKotlinSingletonDep6

@Singleton
@BindType(to = KspKotlinSingletonDep6::class)
internal class KspKotlinSingletonDep6Impl @Inject constructor() : KspKotlinSingletonDep6

// ######################################################################################################

interface KspKotlinSingletonDep7

@Singleton
@BindType(
    to = KspKotlinSingletonDep7::class,
    installIn = BindType.Component.SINGLETON
)
internal class KspKotlinSingletonDep7Impl @Inject constructor() : KspKotlinSingletonDep7

// ######################################################################################################

interface KspKotlinSingletonDep8

@Singleton
@BindType(contributesTo = BindType.Collection.SET)
internal class KspKotlinSingletonDep8Impl1 @Inject constructor() : KspKotlinSingletonDep8

@Singleton
@BindType(contributesTo = BindType.Collection.SET)
internal class KspKotlinSingletonDep8Impl2 @Inject constructor() : KspKotlinSingletonDep8

@Singleton
@BindType(contributesTo = BindType.Collection.SET)
internal class KspKotlinSingletonDep8Impl3 @Inject constructor() : KspKotlinSingletonDep8

// ######################################################################################################

interface KspKotlinSingletonDep9

@BindType(contributesTo = BindType.Collection.MAP)
@MapClassKey(KspKotlinSingletonDep9Impl1::class)
internal class KspKotlinSingletonDep9Impl1 @Inject constructor() : KspKotlinSingletonDep9

@BindType(contributesTo = BindType.Collection.MAP)
@MapClassKey(KspKotlinSingletonDep9Impl2::class)
internal class KspKotlinSingletonDep9Impl2 @Inject constructor() : KspKotlinSingletonDep9

@BindType(contributesTo = BindType.Collection.MAP)
@MapClassKey(KspKotlinSingletonDep9Impl3::class)
internal class KspKotlinSingletonDep9Impl3 @Inject constructor() : KspKotlinSingletonDep9

// ######################################################################################################

interface KspKotlinSingletonDep10

@BindType(withQualifier = true)
@Named("dep10")
internal class KspKotlinSingletonDep10Impl @Inject constructor() : KspKotlinSingletonDep10

// ######################################################################################################

abstract class KspKotlinSingletonDep11<T>

@BindType
internal class KspKotlinSingletonDep11Impl @Inject constructor() : KspKotlinSingletonDep11<Int>()

// ######################################################################################################

abstract class KspKotlinSingletonDep12<T>

@BindType(to = KspKotlinSingletonDep12::class)
internal class KspKotlinSingletonDep12Impl @Inject constructor() : KspKotlinSingletonDep12<Int>()

// ######################################################################################################

abstract class KspKotlinSingletonDep13<T>

@BindType(contributesTo = BindType.Collection.SET)
internal class KspKotlinSingletonDep13Impl1 @Inject constructor() : KspKotlinSingletonDep13<Int>()

@BindType(contributesTo = BindType.Collection.SET)
internal class KspKotlinSingletonDep13Impl2 @Inject constructor() : KspKotlinSingletonDep13<Int>()

@BindType(contributesTo = BindType.Collection.SET)
internal class KspKotlinSingletonDep13Impl3 @Inject constructor() : KspKotlinSingletonDep13<Int>()
