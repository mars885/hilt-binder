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
import dagger.hilt.android.WithFragmentBindings
import dagger.hilt.android.scopes.ViewScoped
import javax.inject.Inject


internal interface ViewDep1
internal interface ViewDep2
internal interface ViewDep3
internal interface ViewDep4


@BindType(installIn = BindType.Component.VIEW)
internal class ViewDep1Impl @Inject constructor(): ViewDep1

@ViewScoped
@BindType
internal class ViewDep2Impl @Inject constructor(): ViewDep2

@BindType(installIn = BindType.Component.VIEW_WITH_FRAGMENT)
internal class ViewDep3Impl @Inject constructor(): ViewDep3

@ViewScoped
@WithFragmentBindings
@BindType
internal class ViewDep4Impl @Inject constructor(): ViewDep4