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

package com.paulrybitskyi.hiltbinder.processor.javac.parser.providers

import com.paulrybitskyi.hiltbinder.BindType

// For some unknown reason, multiline string literals are misaligned
// in the build console. Standard strings work fine.
internal class MessageProvider {


    fun undefinedReturnTypeError(): String {
        return "Cannot determine a return type of the @${BindType::class.simpleName} binding. " +
            "Forgot to specify it explicitly?"
    }


    fun noSubtypeRelationError(bindingTypeName: String, returnTypeName: String): String {
        return "@${BindType::class.simpleName}-using type, $bindingTypeName, is not " +
            "a subtype of the $returnTypeName class."
    }


    fun componentMismatchError(): String {
        return "@${BindType::class.simpleName}-using type is annotated with a scope of " +
            "one component and specifies a different component in the `installIn` parameter."
    }


    fun undefinedCustomComponentError(): String {
        return "@${BindType::class.simpleName}-using type requests to be installed in a custom " +
            "component, but does not specify its type in the 'customComponent' parameter of the annotation."
    }


    fun noMapKeyError(): String {
        return "@${BindType::class.simpleName}-using type is contributed to a multibound map, " +
            "but does not have a @MapKey annotation."
    }


    fun qualifierAbsentError(): String {
        return "@${BindType::class.simpleName}-using type is specified to have a qualifier, " +
            "but does not have one."
    }


}