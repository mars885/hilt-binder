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

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    detekt()
    ktlint()
    gradleVersions()
    dokka()
}

buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    dependencies {
        classpath(deps.plugins.kotlin)
        classpath(deps.plugins.android)
        classpath(deps.plugins.ksp)
        classpath(deps.plugins.daggerHilt)
        classpath(deps.plugins.gradleVersions)
        classpath(deps.plugins.dokka)
        classpath(deps.plugins.shadow)
    }
}

detekt {
    parallel = true
    buildUponDefaultConfig = true
    config.setFrom("config/detekt/detekt.yml")
}

tasks.withType<Detekt>().configureEach {
    reports.html.required.set(true)
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        listOf("alpha", "beta", "rc").any { keyword ->
            candidate.version.lowercase().contains(keyword)
        }
    }
}

allprojects {
    apply(plugin = PLUGIN_KTLINT)

    project.subprojects
        .filter { subproject ->
            subproject.name !in listOf(
                "sample-deps-javac",
                "sample-deps-kapt",
                "sample-deps-ksp",
                "sample",
            )
        }
        .forEach { subproject ->
            subproject.apply(plugin = PLUGIN_DETEKT)
        }

    repositories {
        mavenCentral()
        google()
    }

    configure<KtlintExtension> {
        version.set(versions.ktlint)
        android.set(true)
        outputToConsole.set(true)
        verbose.set(true)

        filter {
            // https://github.com/JLLeitschuh/ktlint-gradle/issues/266#issuecomment-529527697
            exclude { fileTreeElement -> fileTreeElement.file.path.contains("generated/") }
        }

        reporters {
            reporter(ReporterType.HTML)
        }
    }
}
