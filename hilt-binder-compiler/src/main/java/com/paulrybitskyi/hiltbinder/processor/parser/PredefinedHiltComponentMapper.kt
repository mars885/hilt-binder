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

package com.paulrybitskyi.hiltbinder.processor.parser

import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.hiltbinder.processor.model.PredefinedHiltComponent

internal class PredefinedHiltComponentMapper {

    fun mapToPredefinedComponent(component: BindType.Component): PredefinedHiltComponent {
        return when (component) {
            BindType.Component.SINGLETON -> PredefinedHiltComponent.SINGLETON
            BindType.Component.ACTIVITY_RETAINED -> PredefinedHiltComponent.ACTIVITY_RETAINED
            BindType.Component.SERVICE -> PredefinedHiltComponent.SERVICE
            BindType.Component.ACTIVITY -> PredefinedHiltComponent.ACTIVITY
            BindType.Component.VIEW_MODEL -> PredefinedHiltComponent.VIEW_MODEL
            BindType.Component.FRAGMENT -> PredefinedHiltComponent.FRAGMENT
            BindType.Component.VIEW -> PredefinedHiltComponent.VIEW
            BindType.Component.VIEW_WITH_FRAGMENT -> PredefinedHiltComponent.VIEW_WITH_FRAGMENT

            else -> throw IllegalArgumentException(
                "Cannot map the component ${component::class.qualifiedName} to a Hilt's predefined one.",
            )
        }
    }
}
