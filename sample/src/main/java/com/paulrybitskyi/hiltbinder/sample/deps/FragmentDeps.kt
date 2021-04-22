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

package com.paulrybitskyi.hiltbinder.sample.deps

import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.hiltbinder.keys.MapStringKey
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import javax.inject.Qualifier


internal interface FragmentDep1

@BindType(installIn = BindType.Component.FRAGMENT)
internal class FragmentDep1Impl @Inject constructor(): FragmentDep1



internal interface FragmentDep2

@FragmentScoped
@BindType
internal class FragmentDep2Impl @Inject constructor(): FragmentDep2



internal interface FragmentDep3
internal interface FragmentDep4

internal enum class DepType {

    DEP3,
    DEP4

}

@Qualifier
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.FIELD
)
@Retention(AnnotationRetention.BINARY)
internal annotation class DepMapKey(val value: DepType)

@BindType(installIn = BindType.Component.FRAGMENT, withQualifier = true)
@DepMapKey(DepType.DEP3)
internal class FragmentDep3Impl @Inject constructor(): FragmentDep3

@FragmentScoped
@BindType(withQualifier = true)
@DepMapKey(DepType.DEP4)
internal class FragmentDep4Impl @Inject constructor(): FragmentDep4



internal interface FragmentSuperDep
internal abstract class AbstractFragmentSuperDep : FragmentSuperDep

@FragmentScoped
@BindType(
    to = FragmentSuperDep::class,
    contributesTo = BindType.Collection.MAP
)
@MapStringKey("super_dep_1")
internal class FragmentSuperDep1 @Inject constructor(): AbstractFragmentSuperDep()

@BindType(
    to = FragmentSuperDep::class,
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.MAP
)
@MapStringKey("super_dep_2")
internal class FragmentSuperDep2 @Inject constructor(): AbstractFragmentSuperDep()

@FragmentScoped
@BindType(
    to = FragmentSuperDep::class,
    contributesTo = BindType.Collection.MAP
)
@MapStringKey("super_dep_3")
internal class FragmentSuperDep3 @Inject constructor(): AbstractFragmentSuperDep()

@BindType(
    to = FragmentSuperDep::class,
    installIn = BindType.Component.FRAGMENT,
    contributesTo = BindType.Collection.MAP
)
@MapStringKey("super_dep_4")
internal class FragmentSuperDep4 @Inject constructor(): AbstractFragmentSuperDep()