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
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject


internal interface ViewModelDep1
internal interface ViewModelDep2
internal interface ViewModelDep3

@ViewModelScoped
@BindType
class ViewModelDep1Impl @Inject constructor(): ViewModelDep1

@BindType(installIn = BindType.Component.VIEW_MODEL)
class ViewModelDep2Impl @Inject constructor(): ViewModelDep2

@ViewModelScoped
@BindType
class ViewModelDep3Impl @Inject constructor(): ViewModelDep3