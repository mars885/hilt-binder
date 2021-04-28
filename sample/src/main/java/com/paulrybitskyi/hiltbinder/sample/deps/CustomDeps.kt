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
import dagger.hilt.DefineComponent
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Scope


internal interface CustomComponentDep1
internal interface CustomComponentDep2

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
internal annotation class CustomScope

@CustomScope
@DefineComponent(parent = SingletonComponent::class)
internal interface CustomComponent

@DefineComponent.Builder
internal interface CustomComponentBuilder {

    fun build(): CustomComponent

}

@BindType(
    installIn = BindType.Component.CUSTOM,
    customComponent = CustomComponent::class
)
class CustomComponentDep1Impl @Inject constructor(): CustomComponentDep1

@CustomScope
@BindType(
    installIn = BindType.Component.CUSTOM,
    customComponent = CustomComponent::class
)
class CustomComponentDep2Impl @Inject constructor(): CustomComponentDep2

internal class CustomComponentManager @Inject constructor(
    private val customComponentBuilder: CustomComponentBuilder
) {

    private var customComponent: CustomComponent? = null

    private var customComponentDep1: CustomComponentDep1? = null
    private var customComponentDep2: CustomComponentDep2? = null

    @EntryPoint
    @InstallIn(CustomComponent::class)
    interface DependenciesProvider {

        fun getCustomComponentDep1(): CustomComponentDep1

        fun getCustomComponentDep2(): CustomComponentDep2

    }

    fun onCreateComponent() {
        customComponent = customComponentBuilder.build()

        val dependenciesProvider = EntryPoints.get(customComponent!!, DependenciesProvider::class.java)

        customComponentDep1 = dependenciesProvider.getCustomComponentDep1()
        customComponentDep2 = dependenciesProvider.getCustomComponentDep2()

        checkNotNull(customComponent)
        checkNotNull(customComponentDep2)
    }

    fun onDestroyComponent() {
        customComponent = null
    }

}