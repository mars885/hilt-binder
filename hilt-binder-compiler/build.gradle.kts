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

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin()
    kotlinKapt()
    shadow()
}

// Custom configuration used for dependencies that are copied
// into the library jar instead of adding it as a POM dependency.
val shadowed: Configuration = configurations.create("shadowed") {
    // Make sure shadowed dependencies show up as "compileOnly"
    // so they won't be included in the POM file.
    configurations.getByName("compileOnly").extendsFrom(this)
    // Compiler tests run without shadowed classes, so we should
    // add those dependencies into test configuration.
    configurations.getByName("testImplementation").extendsFrom(this)
}

val shadowJar by tasks.getting(ShadowJar::class) {
    // Clearing the classifier because by default it appends "all"
    // ending to the jar name.
    archiveClassifier.set("")
    configurations = listOf(shadowed)
    dependencies {
        // Excluding redundant dependencies like
        // JetBrains annotations, kotlin libraries,
        // and both Java and Kotlin Poets, since they
        // are directly requested at runtime.
        exclude(dependency("org.jetbrains:.*"))
        exclude(dependency("org.jetbrains.lang:.*"))
        exclude(dependency("org.jetbrains.kotlin:.*"))
        exclude(dependency("com.squareup:javapoet:.*"))
        exclude(dependency("com.squareup:kotlinpoet:.*"))
    }
}

configurations {
    // Replace the standard jar with the one built by "shadowJar"
    // in both api and runtime variants.
    apiElements.get().outgoing.artifacts.clear()
    apiElements.get().outgoing.artifact(shadowJar) {
        builtBy(shadowJar)
    }

    runtimeElements.get().outgoing.artifacts.clear()
    runtimeElements.get().outgoing.artifact(shadowJar) {
        builtBy(shadowJar)
    }
}

dependencies {
    implementation(project(deps.local.hiltBinder))
    shadowed(project(deps.local.compilerProcessing))
    shadowed(project(deps.local.commonUtils))

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
}

publishingConfig.artifactName = publishingConfig.hiltBinderCompilerArtifactName
publishingConfig.artifactVersion = publishingConfig.hiltBinderCompilerArtifactVersion
publishingConfig.artifactDescription = publishingConfig.hiltBinderCompilerArtifactDesc

apply(from = "../publishing.gradle.kts")
