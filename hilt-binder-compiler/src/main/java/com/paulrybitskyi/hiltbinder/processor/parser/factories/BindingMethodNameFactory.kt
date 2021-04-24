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

package com.paulrybitskyi.hiltbinder.processor.parser.factories

import com.paulrybitskyi.hiltbinder.processor.parser.PACKAGE_SEPARATOR
import com.paulrybitskyi.hiltbinder.processor.utils.getQualifiedNameStr
import javax.lang.model.element.TypeElement

internal class BindingMethodNameFactory {


    private companion object {

        private const val METHOD_NAME_WORD_SEPARATOR = '_'
        private const val METHOD_NAME_PREFIX = "bind$METHOD_NAME_WORD_SEPARATOR"

    }


    fun createMethodName(annotatedElement: TypeElement): String {
        val qualifiedName = annotatedElement.getQualifiedNameStr()
        val formattedQualifiedName = qualifiedName.replace(
            PACKAGE_SEPARATOR,
            METHOD_NAME_WORD_SEPARATOR
        )

        return (METHOD_NAME_PREFIX + formattedQualifiedName)
    }


}