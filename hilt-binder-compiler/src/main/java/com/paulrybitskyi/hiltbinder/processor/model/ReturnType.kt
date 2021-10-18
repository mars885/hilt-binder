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

import com.paulrybitskyi.hiltbinder.compiler.processing.XType

internal sealed class ReturnType(val type: XType) {

    class Standard(type: XType) : ReturnType(type)

    internal sealed class Generic(type: XType) : ReturnType(type) {
        class Parameterized(type: XType) : Generic(type)
        class UnboundedWildcard(type: XType, val typeParamCount: Int) : Generic(type)
    }
}
