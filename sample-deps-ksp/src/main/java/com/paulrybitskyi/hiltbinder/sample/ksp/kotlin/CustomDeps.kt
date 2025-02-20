/*
 * Copyright 2021 Paul Rybitskyi, oss@paulrybitskyi.com
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
import dagger.hilt.DefineComponent
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Scope

// ######################################################################################################

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class KspKotlinCustomScope

@KspKotlinCustomScope
@DefineComponent(parent = SingletonComponent::class)
interface KspKotlinCustomComponent {

    @DefineComponent.Builder
    interface Builder {
        fun build(): KspKotlinCustomComponent
    }
}

// ######################################################################################################

interface KspKotlinCustomComponentDep1

@BindType(
    installIn = BindType.Component.CUSTOM,
    customComponent = KspKotlinCustomComponent::class,
)
internal class KspKotlinCustomComponentDep1Impl @Inject constructor() : KspKotlinCustomComponentDep1

// ######################################################################################################

abstract class KspKotlinCustomComponentDep2

@KspKotlinCustomScope
@BindType(
    installIn = BindType.Component.CUSTOM,
    customComponent = KspKotlinCustomComponent::class,
)
internal class KspKotlinCustomComponentDep2Impl @Inject constructor() : KspKotlinCustomComponentDep2()

// ######################################################################################################

class KspKotlinCustomComponentManager @Inject constructor(
    private val customComponentBuilder: KspKotlinCustomComponent.Builder,
) {

    private var customComponent: KspKotlinCustomComponent? = null

    private var customComponentDep1: KspKotlinCustomComponentDep1? = null
    private var customComponentDep2: KspKotlinCustomComponentDep2? = null

    @EntryPoint
    @InstallIn(KspKotlinCustomComponent::class)
    internal interface DependenciesProvider {
        fun getCustomComponentDep1(): KspKotlinCustomComponentDep1
        fun getCustomComponentDep2(): KspKotlinCustomComponentDep2
    }

    fun onCreateComponent() {
        customComponent = customComponentBuilder.build()

        val dependenciesProvider = EntryPoints.get(
            customComponent!!,
            DependenciesProvider::class.java,
        )

        customComponentDep1 = dependenciesProvider.getCustomComponentDep1()
        customComponentDep2 = dependenciesProvider.getCustomComponentDep2()

        checkNotNull(customComponentDep1)
        checkNotNull(customComponentDep2)
    }

    fun onDestroyComponent() {
        customComponent = null
    }
}
