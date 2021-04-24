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


internal sealed class HiltComponent {

    data class Predefined(val component: PredefinedHiltComponent): HiltComponent()

    data class Custom(
        val simpleName: String,
        val qualifiedName: String
    ): HiltComponent()

}


internal val HiltComponent.simpleName: String
    get() = when(this) {
        is HiltComponent.Predefined -> component.simpleName
        is HiltComponent.Custom -> simpleName
    }


internal val HiltComponent.qualifiedName: String
    get() = when(this) {
        is HiltComponent.Predefined -> component.qualifiedName
        is HiltComponent.Custom -> qualifiedName
    }