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

package com.paulrybitskyi.hiltbinder.sample.application

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltAndroidApp
internal class SampleApplication : Application() {

    // TODO Delete when https://github.com/google/dagger/issues/3601 is resolved.
    @Inject
    @ApplicationContext
    lateinit var context: Context

    @Inject lateinit var javacJavaSingletonDeps: JavacJavaSingletonDeps
    @Inject lateinit var kaptJavaSingletonDeps: KaptJavaSingletonDeps
    @Inject lateinit var kaptKotlinSingletonDeps: KaptKotlinSingletonDeps
    @Inject lateinit var kspJavaSingletonDeps: KspJavaSingletonDeps
    @Inject lateinit var kspKotlinSingletonDeps: KspKotlinSingletonDeps

    override fun onCreate() {
        super.onCreate()

        javacJavaSingletonDeps.check()
        kaptJavaSingletonDeps.check()
        kaptKotlinSingletonDeps.check()
        kspJavaSingletonDeps.check()
        kspKotlinSingletonDeps.check()
    }
}
