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
import dagger.hilt.DefineComponent
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Scope



@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class KaptKotlinCustomScope

@KaptKotlinCustomScope
@DefineComponent(parent = SingletonComponent::class)
interface KaptKotlinCustomComponent {

    @DefineComponent.Builder
    interface Builder {

        fun build(): KaptKotlinCustomComponent

    }

}



interface KaptKotlinCustomComponentDep1

@BindType(
    installIn = BindType.Component.CUSTOM,
    customComponent = KaptKotlinCustomComponent::class
)
internal class KaptKotlinCustomComponentDep1Impl @Inject constructor(): KaptKotlinCustomComponentDep1



abstract class KaptKotlinCustomComponentDep2

@KaptKotlinCustomScope
@BindType(
    installIn = BindType.Component.CUSTOM,
    customComponent = KaptKotlinCustomComponent::class
)
internal class KaptKotlinCustomComponentDep2Impl @Inject constructor(): KaptKotlinCustomComponentDep2()



class KaptKotlinCustomComponentManager @Inject constructor(
    private val customComponentBuilder: KaptKotlinCustomComponent.Builder
) {


    private var customComponent: KaptKotlinCustomComponent? = null

    private var customComponentDep1: KaptKotlinCustomComponentDep1? = null
    private var customComponentDep2: KaptKotlinCustomComponentDep2? = null


    @EntryPoint
    @InstallIn(KaptKotlinCustomComponent::class)
    internal interface DependenciesProvider {

        fun getCustomComponentDep1(): KaptKotlinCustomComponentDep1
        fun getCustomComponentDep2(): KaptKotlinCustomComponentDep2

    }


    fun onCreateComponent() {
        customComponent = customComponentBuilder.build()

        val dependenciesProvider = EntryPoints.get(
            customComponent!!,
            DependenciesProvider::class.java
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