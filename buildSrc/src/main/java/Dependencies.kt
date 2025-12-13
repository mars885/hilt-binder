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

@file:Suppress("ClassName")

import org.gradle.api.JavaVersion

object appConfig {

    const val compileSdkVersion = 34
    const val targetSdkVersion = 34
    const val minSdkVersion = 21
    const val applicationId = "com.paulrybitskyi.hiltbinder.sample"
    const val versionCode = 1
    const val versionName = "1.0.0"

    const val exportableLibJavaCompatVersion = 8

    val androidModuleJavaCompatVersion = JavaVersion.VERSION_1_8
    val androidModuleKotlinCompatVersion = JavaVersion.VERSION_1_8
}

object publishingConfig {

    const val artifactGroupId = "com.paulrybitskyi"
    const val artifactWebsite = "https://github.com/mars885/hilt-binder"

    const val mavenPublicationName = "release"

    const val licenseName = "The Apache Software License, Version 2.0"
    const val licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.txt"
    const val developerId = "mars885"
    const val developerName = "Paul Rybitskyi"
    const val developerEmail = "oss@paulrybitskyi.com"
    const val siteUrl = "https://github.com/mars885/hilt-binder"
    const val gitUrl = "https://github.com/mars885/hilt-binder.git"

    const val hostRepoName = "sonatype"
    const val hostRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"

    const val hiltBinderArtifactName = "hilt-binder"
    const val hiltBinderArtifactVersion = "1.1.3"
    const val hiltBinderArtifactDesc = "A public API of the library that automatically generates Dagger Hilt's @Binds methods."

    const val hiltBinderCompilerArtifactName = "hilt-binder-compiler"
    const val hiltBinderCompilerArtifactVersion = "1.1.3"
    const val hiltBinderCompilerArtifactDesc = "An annotation processor of the library that automatically generates Dagger Hilt's @Binds methods."

    var artifactName = ""
    var artifactVersion = ""
    var artifactDescription = ""
}

object versions {

    const val kotlin = "2.2.21" // also in buildSrc build.gradle.kts file
    const val androidPlugin = "8.13.2" // also in buildSrc build.gradle.kts file
    const val detektPlugin = "1.23.7"
    const val ktlintPlugin = "12.1.1"
    const val gradleVersionsPlugin = "0.53.0"
    const val dokkaPlugin = "1.9.20"
    const val shadowPlugin = "9.3.0"
    const val ksp = "2.3.3"
    const val dagger = "2.57.2"
    const val appCompat = "1.7.0"
    const val navigation = "2.8.1"
    const val fragmentKtx = "1.8.3"
    const val constraintLayout = "2.1.4"
    const val materialComponents = "1.12.0"
    const val apacheCommons = "3.17.0"
    const val javaPoet = "1.13.0" // also in buildSrc build.gradle.kts file
    const val kotlinPoet = "2.2.0"
    const val incap = "1.0.0"
    const val autoService = "1.1.1"
    const val ktlint = "1.3.1"
    const val jUnit = "4.13.2"
    const val truth = "1.4.5"
    const val ktCompileTesting = "0.11.0"
    const val testParamInjector = "1.20"
}

object deps {

    object plugins {

        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${versions.kotlin}"
        const val android = "com.android.tools.build:gradle:${versions.androidPlugin}"
        const val ksp = "com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:${versions.ksp}"
        const val daggerHilt = "com.google.dagger:hilt-android-gradle-plugin:${versions.dagger}"
        const val gradleVersions = "com.github.ben-manes:gradle-versions-plugin:${versions.gradleVersionsPlugin}"
        const val dokka = "org.jetbrains.dokka:dokka-gradle-plugin:${versions.dokkaPlugin}"
        const val shadow = "com.gradleup.shadow:shadow-gradle-plugin:${versions.shadowPlugin}"

    }

    object local {

        const val hiltBinder = ":hilt-binder"
        const val hiltBinderCompiler = ":hilt-binder-compiler"
        const val commonUtils = ":common-utils"
        const val compilerProcessing = ":compiler-processing"
        const val sampleDepsJavac = ":sample-deps-javac"
        const val sampleDepsKapt = ":sample-deps-kapt"
        const val sampleDepsKsp = ":sample-deps-ksp"

        const val daggerHiltCore = "libs/hilt-core-classes-${versions.dagger}.jar"
        const val daggerHiltAndroid = "libs/hilt-android-classes-${versions.dagger}.jar"

    }

    const val appCompat = "androidx.appcompat:appcompat:${versions.appCompat}"
    const val navFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${versions.navigation}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${versions.fragmentKtx}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${versions.constraintLayout}"
    const val vanillaDagger = "com.google.dagger:dagger:${versions.dagger}"
    const val daggerHilt = "com.google.dagger:hilt-android:${versions.dagger}"
    const val daggerHiltCompiler = "com.google.dagger:hilt-android-compiler:${versions.dagger}"
    const val materialComponents = "com.google.android.material:material:${versions.materialComponents}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${versions.kotlin}"
    const val apacheCommons = "org.apache.commons:commons-lang3:${versions.apacheCommons}"
    const val javaPoet = "com.squareup:javapoet:${versions.javaPoet}"
    const val kotlinPoet = "com.squareup:kotlinpoet:${versions.kotlinPoet}"
    const val incap = "net.ltgt.gradle.incap:incap:${versions.incap}"
    const val incapCompiler = "net.ltgt.gradle.incap:incap-processor:${versions.incap}"
    const val autoService = "com.google.auto.service:auto-service:${versions.autoService}"
    const val kspCore = "com.google.devtools.ksp:symbol-processing:${versions.ksp}"
    const val kspApi = "com.google.devtools.ksp:symbol-processing-api:${versions.ksp}"
    const val jUnit = "junit:junit:${versions.jUnit}"
    const val truth = "com.google.truth:truth:${versions.truth}"
    const val kspCompileTesting = "dev.zacsweers.kctfork:ksp:${versions.ktCompileTesting}"
    const val testParamInjector = "com.google.testparameterinjector:test-parameter-injector:${versions.testParamInjector}"
}
