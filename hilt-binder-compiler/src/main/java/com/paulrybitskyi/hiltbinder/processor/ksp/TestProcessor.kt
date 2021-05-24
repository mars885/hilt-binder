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

package com.paulrybitskyi.hiltbinder.processor.ksp

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSType
import com.paulrybitskyi.hiltbinder.InterestingAnnotation
import com.paulrybitskyi.hiltbinder.processor.ksp.utils.getTypeByName

internal class TestProcessor : SymbolProcessor {


    override fun process(resolver: Resolver): List<KSAnnotated> {
        resolver.getSymbolsWithAnnotation(InterestingAnnotation::class.qualifiedName!!)
            .forEach { symbol ->
                val mapKeyAnnotationType = resolver.getClassDeclarationByName("dagger.MapKey")!!.asType(emptyList())
                val mapKeyAnnotation = symbol.annotations
                    .firstOrNull { outerAnno ->
                        outerAnno.annotationType
                            .resolve()
                            .declaration
                            .annotations.
                            any { innerAnno -> innerAnno.annotationType.resolve() == mapKeyAnnotationType }
                    }

                if(mapKeyAnnotation != null) {
                    val enumEntryType = mapKeyAnnotation.arguments.first().value as KSType

                    try {
                        enumEntryType.declaration   // throws Unexpected psi type...
                    } catch(error: Throwable) {
                        error.printStackTrace()
                    }
                }
            }

        return emptyList()
    }


}