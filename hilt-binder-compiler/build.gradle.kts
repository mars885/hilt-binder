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

    implementation(deps.apacheCommons)
    implementation(deps.javaPoet)

    compileOnly(deps.incap)
    kapt(deps.incapCompiler)

    compileOnly(deps.autoService)
    kapt(deps.autoService)

    testImplementation(deps.jUnit)
    testImplementation(deps.truth)
    testImplementation(deps.compileTesting)

    // The following dep excludes Android related classes for some reason
    // (Android components and their according scopes), so they are
    // provided in a separate local jar file.
    testImplementation(deps.daggerHilt)
    testImplementation(files(deps.local.daggerHilt))

    // https://github.com/google/compile-testing/issues/28
    if(Jvm.current().javaVersion?.isJava9Compatible == false) {
        testImplementation(files(Jvm.current().toolsJar))
    }
}

publishingConfig.artifactName = publishingConfig.hiltBinderCompilerArtifactName
publishingConfig.artifactVersion = publishingConfig.hiltBinderCompilerArtifactVersion
publishingConfig.artifactDescription = publishingConfig.hiltBinderCompilerArtifactDesc

apply(from = "../publishing.gradle.kts")