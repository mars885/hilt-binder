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

import org.gradle.internal.jvm.Jvm

plugins {
    kotlin()
    kotlinKapt()
}

dependencies {
    implementation(project(deps.local.hiltBinder))

    implementation(deps.kotlinReflect)
    implementation(deps.apacheCommons)
    implementation(deps.javaPoet)
    implementation(deps.kotlinPoet)

    compileOnly(deps.kspApi)

    compileOnly(deps.incap)
    kapt(deps.incapCompiler)

    compileOnly(deps.autoService)
    kapt(deps.autoService)

    testImplementation(deps.jUnit)
    testImplementation(deps.truth)
    testImplementation(deps.kspCore)
    testImplementation(deps.kspApi)
    testImplementation(deps.kspCompileTesting)
    testImplementation(deps.burst)

    // Some Hilt Android classes have to be present on the classpath
    // when testing. Since hilt-binder-compiler is a jar artifact and
    // dagger-hilt consists of aar artifacts, we have to somehow
    // pull the Android classes. If the dependency in question
    // (dagger-hilt in this case) does not specify some extra
    // metadata (e.g., https://stackoverflow.com/questions/62681012/version-update-of-androidx-ktx-navigation-fragment-and-navigation-ui-fails-from)
    // then Gradle will simply ignore any aar dependencies.
    // If the dependency specifies that extra metadata, then Gradle
    // will throw an error. Somewhere between release 2.31.2-alpha
    // and 2.34.1-beta, dagger-hilt started adding that extra metadata.
    // The workaround then is to manually add missing classes from the classpath
    // by adding local jars in which they are contained.
    testImplementation(files(deps.local.daggerHiltCore))
    testImplementation(files(deps.local.daggerHiltAndroid))
    testImplementation(files("libs/hilt-android-classes-2.35.1.jar"))
    testImplementation(files("libs/hilt-core-classes-2.35.1.jar"))
}

publishingConfig.artifactName = publishingConfig.hiltBinderCompilerArtifactName
publishingConfig.artifactVersion = publishingConfig.hiltBinderCompilerArtifactVersion
publishingConfig.artifactDescription = publishingConfig.hiltBinderCompilerArtifactDesc

apply(from = "../publishing.gradle.kts")