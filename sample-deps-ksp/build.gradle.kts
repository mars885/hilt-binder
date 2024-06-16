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

plugins {
    androidLibrary()
    kotlinAndroid()
    kotlinKapt()
    ksp()
    daggerHiltAndroid()
}

android {
    compileSdk = appConfig.compileSdkVersion

    defaultConfig {
        namespace = "com.paulrybitskyi.hiltbinder.sample.ksp"
        minSdk = appConfig.minSdkVersion
        targetSdk = appConfig.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            sourceSets {
                getByName("debug") {
                    java.srcDir(File("build/generated/ksp/debug/java"))
                    java.srcDir(File("build/generated/ksp/debug/kotlin"))
                }
            }
        }

        getByName("release") {
            sourceSets {
                getByName("release") {
                    java.srcDir(File("build/generated/ksp/release/java"))
                    java.srcDir(File("build/generated/ksp/release/kotlin"))
                }
            }

            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = appConfig.androidModuleJavaCompatVersion
        targetCompatibility = appConfig.androidModuleJavaCompatVersion
    }

    kotlinOptions {
        jvmTarget = appConfig.androidModuleKotlinCompatVersion.toString()
    }
}

dependencies {
    implementation(deps.daggerHilt)
    kapt(deps.daggerHiltCompiler)

    implementation(project(deps.local.hiltBinder))
    ksp(project(deps.local.hiltBinderCompiler))
}
