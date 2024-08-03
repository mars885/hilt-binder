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
import com.paulrybitskyi.hiltbinder.keys.MapClassKey
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

// ######################################################################################################

interface KaptKotlinSingletonDep1

@BindType
internal class KaptKotlinSingletonDep1Impl @Inject constructor() : KaptKotlinSingletonDep1

// ######################################################################################################

interface KaptKotlinSingletonDep2

@BindType(to = KaptKotlinSingletonDep2::class)
internal class KaptKotlinSingletonDep2Impl @Inject constructor() : KaptKotlinSingletonDep2

// ######################################################################################################

interface KaptKotlinSingletonDep3

@BindType(installIn = BindType.Component.SINGLETON)
internal class KaptKotlinSingletonDep3Impl @Inject constructor() : KaptKotlinSingletonDep3

// ######################################################################################################

interface KaptKotlinSingletonDep4

@BindType(
    to = KaptKotlinSingletonDep4::class,
    installIn = BindType.Component.SINGLETON,
)
internal class KaptKotlinSingletonDep4Impl @Inject constructor() : KaptKotlinSingletonDep4

// ######################################################################################################

interface KaptKotlinSingletonDep5

@Singleton
@BindType
internal class KaptKotlinSingletonDep5Impl @Inject constructor() : KaptKotlinSingletonDep5

// ######################################################################################################

interface KaptKotlinSingletonDep6

@Singleton
@BindType(to = KaptKotlinSingletonDep6::class)
internal class KaptKotlinSingletonDep6Impl @Inject constructor() : KaptKotlinSingletonDep6

// ######################################################################################################

interface KaptKotlinSingletonDep7

@Singleton
@BindType(
    to = KaptKotlinSingletonDep7::class,
    installIn = BindType.Component.SINGLETON,
)
internal class KaptKotlinSingletonDep7Impl @Inject constructor() : KaptKotlinSingletonDep7

// ######################################################################################################

interface KaptKotlinSingletonDep8

@Singleton
@BindType(contributesTo = BindType.Collection.SET)
internal class KaptKotlinSingletonDep8Impl1 @Inject constructor() : KaptKotlinSingletonDep8

@Singleton
@BindType(contributesTo = BindType.Collection.SET)
internal class KaptKotlinSingletonDep8Impl2 @Inject constructor() : KaptKotlinSingletonDep8

@Singleton
@BindType(contributesTo = BindType.Collection.SET)
internal class KaptKotlinSingletonDep8Impl3 @Inject constructor() : KaptKotlinSingletonDep8

// ######################################################################################################

interface KaptKotlinSingletonDep9

@BindType(contributesTo = BindType.Collection.MAP)
@MapClassKey(KaptKotlinSingletonDep9Impl1::class)
internal class KaptKotlinSingletonDep9Impl1 @Inject constructor() : KaptKotlinSingletonDep9

@BindType(contributesTo = BindType.Collection.MAP)
@MapClassKey(KaptKotlinSingletonDep9Impl2::class)
internal class KaptKotlinSingletonDep9Impl2 @Inject constructor() : KaptKotlinSingletonDep9

@BindType(contributesTo = BindType.Collection.MAP)
@MapClassKey(KaptKotlinSingletonDep9Impl3::class)
internal class KaptKotlinSingletonDep9Impl3 @Inject constructor() : KaptKotlinSingletonDep9

// ######################################################################################################

interface KaptKotlinSingletonDep10

@BindType(withQualifier = true)
@Named("dep10")
internal class KaptKotlinSingletonDep10Impl @Inject constructor() : KaptKotlinSingletonDep10

// ######################################################################################################

abstract class KaptKotlinSingletonDep11<T>

@BindType
internal class KaptKotlinSingletonDep11Impl @Inject constructor() : KaptKotlinSingletonDep11<Int>()

// ######################################################################################################

abstract class KaptKotlinSingletonDep12<T>

@BindType(to = KaptKotlinSingletonDep12::class)
internal class KaptKotlinSingletonDep12Impl @Inject constructor() : KaptKotlinSingletonDep12<Int>()

// ######################################################################################################

abstract class KaptKotlinSingletonDep13<T>

@BindType(contributesTo = BindType.Collection.SET)
internal class KaptKotlinSingletonDep13Impl1 @Inject constructor() : KaptKotlinSingletonDep13<Int>()

@BindType(contributesTo = BindType.Collection.SET)
internal class KaptKotlinSingletonDep13Impl2 @Inject constructor() : KaptKotlinSingletonDep13<Int>()

@BindType(contributesTo = BindType.Collection.SET)
internal class KaptKotlinSingletonDep13Impl3 @Inject constructor() : KaptKotlinSingletonDep13<Int>()
