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

package com.paulrybitskyi.hiltbinder.processor.generator.content.utils

private class ClassInfo(val packageName: String, val className: String)

private fun ClassInfo.toJavaClassName(): com.squareup.javapoet.ClassName {
    return com.squareup.javapoet.ClassName.get(packageName, className)
}

private fun ClassInfo.toKotlinClassName(): com.squareup.kotlinpoet.ClassName {
    return com.squareup.kotlinpoet.ClassName(packageName, className)
}

private val DAGGER_TYPE_MODULE_CLASS_INFO = ClassInfo("dagger", "Module")
private val DAGGER_TYPE_INSTALL_IN_CLASS_INFO = ClassInfo("dagger.hilt", "InstallIn")
private val DAGGER_TYPE_BINDS_CLASS_INFO = ClassInfo("dagger", "Binds")
private val DAGGER_TYPE_INTO_SET_CLASS_INFO = ClassInfo("dagger.multibindings", "IntoSet")
private val DAGGER_TYPE_INTO_MAP_CLASS_INFO = ClassInfo("dagger.multibindings", "IntoMap")

internal val DAGGER_TYPE_MODULE_JAVA_CLASS_NAME = DAGGER_TYPE_MODULE_CLASS_INFO.toJavaClassName()
internal val DAGGER_TYPE_INSTALL_IN_JAVA_CLASS_NAME = DAGGER_TYPE_INSTALL_IN_CLASS_INFO.toJavaClassName()
internal val DAGGER_TYPE_BINDS_JAVA_CLASS_NAME = DAGGER_TYPE_BINDS_CLASS_INFO.toJavaClassName()
internal val DAGGER_TYPE_INTO_SET_JAVA_CLASS_NAME = DAGGER_TYPE_INTO_SET_CLASS_INFO.toJavaClassName()
internal val DAGGER_TYPE_INTO_MAP_JAVA_CLASS_NAME = DAGGER_TYPE_INTO_MAP_CLASS_INFO.toJavaClassName()

internal val DAGGER_TYPE_MODULE_KOTLIN_CLASS_NAME = DAGGER_TYPE_MODULE_CLASS_INFO.toKotlinClassName()
internal val DAGGER_TYPE_INSTALL_IN_KOTLIN_CLASS_NAME = DAGGER_TYPE_INSTALL_IN_CLASS_INFO.toKotlinClassName()
internal val DAGGER_TYPE_BINDS_KOTLIN_CLASS_NAME = DAGGER_TYPE_BINDS_CLASS_INFO.toKotlinClassName()
internal val DAGGER_TYPE_INTO_SET_KOTLIN_CLASS_NAME = DAGGER_TYPE_INTO_SET_CLASS_INFO.toKotlinClassName()
internal val DAGGER_TYPE_INTO_MAP_KOTLIN_CLASS_NAME = DAGGER_TYPE_INTO_MAP_CLASS_INFO.toKotlinClassName()
