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

package com.paulrybitskyi.hiltbinder.processor.model

internal enum class PredefinedHiltComponent(
    val simpleName: String,
    val qualifiedName: String,
    val scopeQualifiedName: String,
) {
    SINGLETON(
        simpleName = "SingletonComponent",
        qualifiedName = "dagger.hilt.components.SingletonComponent",
        scopeQualifiedName = "javax.inject.Singleton",
    ),
    ACTIVITY_RETAINED(
        simpleName = "ActivityRetainedComponent",
        qualifiedName = "dagger.hilt.android.components.ActivityRetainedComponent",
        scopeQualifiedName = "dagger.hilt.android.scopes.ActivityRetainedScoped",
    ),
    SERVICE(
        simpleName = "ServiceComponent",
        qualifiedName = "dagger.hilt.android.components.ServiceComponent",
        scopeQualifiedName = "dagger.hilt.android.scopes.ServiceScoped",
    ),
    ACTIVITY(
        simpleName = "ActivityComponent",
        qualifiedName = "dagger.hilt.android.components.ActivityComponent",
        scopeQualifiedName = "dagger.hilt.android.scopes.ActivityScoped",
    ),
    VIEW_MODEL(
        simpleName = "ViewModelComponent",
        qualifiedName = "dagger.hilt.android.components.ViewModelComponent",
        scopeQualifiedName = "dagger.hilt.android.scopes.ViewModelScoped",
    ),
    FRAGMENT(
        simpleName = "FragmentComponent",
        qualifiedName = "dagger.hilt.android.components.FragmentComponent",
        scopeQualifiedName = "dagger.hilt.android.scopes.FragmentScoped",
    ),
    VIEW(
        simpleName = "ViewComponent",
        qualifiedName = "dagger.hilt.android.components.ViewComponent",
        scopeQualifiedName = "dagger.hilt.android.scopes.ViewScoped",
    ),
    VIEW_WITH_FRAGMENT(
        simpleName = "ViewWithFragmentComponent",
        qualifiedName = "dagger.hilt.android.components.ViewWithFragmentComponent",
        scopeQualifiedName = "dagger.hilt.android.scopes.ViewScoped",
    ),
}
