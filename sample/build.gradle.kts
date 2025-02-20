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

plugins {
    androidApplication()
    kotlinAndroid()
    kotlinKapt()
    daggerHiltAndroid()
}

android {
    compileSdk = appConfig.compileSdkVersion

    defaultConfig {
        applicationId = appConfig.applicationId
        namespace = appConfig.applicationId
        minSdk = appConfig.minSdkVersion
        targetSdk = appConfig.targetSdkVersion
        versionCode = appConfig.versionCode
        versionName = appConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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
    implementation(project(deps.local.sampleDepsJavac))
    implementation(project(deps.local.sampleDepsKapt))
    implementation(project(deps.local.sampleDepsKsp))

    implementation(deps.daggerHilt)
    kapt(deps.daggerHiltCompiler)

    implementation(project(deps.local.hiltBinder))
    kapt(project(deps.local.hiltBinderCompiler))

    implementation(deps.appCompat)
    implementation(deps.navFragmentKtx)
    implementation(deps.fragmentKtx)
    implementation(deps.constraintLayout)

    implementation(deps.materialComponents)
}

val installGitHook by tasks.registering(Copy::class) {
    from(File(rootProject.rootDir, "hooks/pre-push"))
    into(File(rootProject.rootDir, ".git/hooks/"))

    // https://github.com/gradle/kotlin-dsl-samples/issues/1412
    filePermissions {
        unix("rwxr-xr-x")
    }
}

tasks.getByPath(":sample:preBuild").dependsOn(installGitHook)
