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
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Named


internal interface ActivityDep1

@ActivityScoped
@BindType
internal class ActivityDep1Impl @Inject constructor(): ActivityDep1



internal interface ActivityDep2

@BindType(installIn = BindType.Component.ACTIVITY)
internal class ActivityDep2Impl @Inject constructor(): ActivityDep2



internal interface ActivityDep3

@ActivityScoped
@BindType(withQualifier = true)
@Named("dep3")
internal class ActivityDep3Impl @Inject constructor(): ActivityDep3



internal interface ActivityDep4

@BindType(installIn = BindType.Component.ACTIVITY, withQualifier = true)
@Named("dep4")
internal class ActivityDep4Impl @Inject constructor(): ActivityDep4



internal abstract class ActivitySuperDep

@BindType(
    to = ActivitySuperDep::class,
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.SET
)
internal class ActivitySuperDep1 @Inject constructor(): ActivitySuperDep(), ActivityDep1

@BindType(
    to = ActivitySuperDep::class,
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.SET
)
internal class ActivitySuperDep2 @Inject constructor(): ActivitySuperDep(), ActivityDep2

@BindType(
    to = ActivitySuperDep::class,
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.SET
)
internal class ActivitySuperDep3 @Inject constructor(): ActivitySuperDep(), ActivityDep3

@BindType(
    to = ActivitySuperDep::class,
    installIn = BindType.Component.ACTIVITY,
    contributesTo = BindType.Collection.SET
)
internal class ActivitySuperDep4 @Inject constructor(): ActivitySuperDep(), ActivityDep4