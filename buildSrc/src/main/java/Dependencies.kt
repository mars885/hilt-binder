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

@file:Suppress("ClassName")

import org.gradle.api.JavaVersion


object appConfig {

    const val compileSdkVersion = 30
    const val targetSdkVersion = 30
    const val minSdkVersion = 21
    const val buildToolsVersion = "30.0.2"
    const val applicationId = "com.paulrybitskyi.hiltbinder.sample"
    const val versionCode = 1
    const val versionName = "1.0.0"

    val javaCompatibilityVersion = JavaVersion.VERSION_1_8
    val kotlinCompatibilityVersion = JavaVersion.VERSION_1_8

}


object publishingConfig {

    const val artifactGroupId = "com.paulrybitskyi"
    const val artifactWebsite = "https://github.com/mars885/hilt-binder"

    const val mavenPublicationName = "release"

    const val licenseName = "The Apache Software License, Version 2.0"
    const val licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.txt"
    const val developerId = "mars885"
    const val developerName = "Paul Rybitskyi"
    const val developerEmail = "paul.rybitskyi.work@gmail.com"
    const val siteUrl = "https://github.com/mars885/hilt-binder"
    const val gitUrl = "https://github.com/mars885/hilt-binder.git"

    const val hostRepoName = "sonatype"
    const val hostRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"

    const val hiltBinderArtifactName = "hilt-binder"
    const val hiltBinderArtifactVersion = "1.0.0"
    const val hiltBinderArtifactDesc = "A public API of the library that automatically generates Dagger Hilt's @Binds methods."

    const val hiltBinderCompilerArtifactName = "hilt-binder-compiler"
    const val hiltBinderCompilerArtifactVersion = "1.0.0"
    const val hiltBinderCompilerArtifactDesc = "An annotation processor of the library that automatically generates Dagger Hilt's @Binds methods."

    var artifactName = ""
    var artifactVersion = ""
    var artifactDescription = ""

}


object versions {

    const val kotlin = "1.5.0" // also in buildSrc build.gradle.kts file
    const val gradlePlugin = "4.2.0" // also in buildSrc build.gradle.kts file
    const val gradleVersionsPlugin = "0.38.0"
    const val dokkaPlugin = "1.4.32"
    const val appCompat = "1.2.0"
    const val navigation = "2.3.5"
    const val fragmentKtx = "1.3.3"
    const val constraintLayout = "2.0.4"
    const val dagger = "2.35.1"
    const val materialComponents = "1.3.0"
    const val apacheCommons = "3.12.0"
    const val javaPoet = "1.13.0"
    const val kotlinPoet = "1.8.0"
    const val incap = "0.3"
    const val autoService = "1.0"
    const val ksp = "1.5.0-1.0.0-alpha10"
    const val jUnit = "4.13.2"
    const val jUnitExt = "1.1.2"
    const val truth = "1.1.2"
    const val ktCompileTesting = "1.4.1"
    const val burst = "1.2.0"

}


object deps {

    object plugins {

        const val androidGradle = "com.android.tools.build:gradle:${versions.gradlePlugin}"
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${versions.kotlin}"
        const val daggerHiltGradle = "com.google.dagger:hilt-android-gradle-plugin:${versions.dagger}"
        const val gradleVersions = "com.github.ben-manes:gradle-versions-plugin:${versions.gradleVersionsPlugin}"
        const val dokka = "org.jetbrains.dokka:dokka-gradle-plugin:${versions.dokkaPlugin}"

    }

    object local {

        const val hiltBinder = ":hilt-binder"
        const val hiltBinderCompiler = ":hilt-binder-compiler"

        const val daggerHiltCore = "libs/hilt-core-${versions.dagger}.jar"
        const val daggerHiltAndroid = "libs/hilt-android-${versions.dagger}.jar"

    }

    const val appCompat = "androidx.appcompat:appcompat:${versions.appCompat}"
    const val navFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${versions.navigation}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${versions.fragmentKtx}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${versions.constraintLayout}"
    const val vanillaDagger = "com.google.dagger:dagger:${versions.dagger}"
    const val daggerHilt = "com.google.dagger:hilt-android:${versions.dagger}"
    const val daggerHiltCompiler = "com.google.dagger:hilt-android-compiler:${versions.dagger}"
    const val materialComponents = "com.google.android.material:material:${versions.materialComponents}"
    const val apacheCommons = "org.apache.commons:commons-lang3:${versions.apacheCommons}"
    const val javaPoet = "com.squareup:javapoet:${versions.javaPoet}"
    const val kotlinPoet = "com.squareup:kotlinpoet:${versions.kotlinPoet}"
    const val incap = "net.ltgt.gradle.incap:incap:${versions.incap}"
    const val incapCompiler = "net.ltgt.gradle.incap:incap-processor:${versions.incap}"
    const val autoService = "com.google.auto.service:auto-service:${versions.autoService}"
    const val kspCore = "com.google.devtools.ksp:symbol-processing:${versions.ksp}"
    const val kspApi = "com.google.devtools.ksp:symbol-processing-api:${versions.ksp}"
    const val jUnit = "junit:junit:${versions.jUnit}"
    const val jUnitExt = "androidx.test.ext:junit:${versions.jUnitExt}"
    const val truth = "com.google.truth:truth:${versions.truth}"
    const val kspCompileTesting = "com.github.tschuchortdev:kotlin-compile-testing-ksp:${versions.ktCompileTesting}"
    const val burst = "com.squareup.burst:burst-junit4:${versions.burst}"

}