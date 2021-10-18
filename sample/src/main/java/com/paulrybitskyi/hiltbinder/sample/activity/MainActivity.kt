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

package com.paulrybitskyi.hiltbinder.sample.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paulrybitskyi.hiltbinder.sample.R
import com.paulrybitskyi.hiltbinder.sample.javac.custom.JavacJavaCustomComponentManager
import com.paulrybitskyi.hiltbinder.sample.kapt.java.custom.KaptJavaCustomComponentManager
import com.paulrybitskyi.hiltbinder.sample.kapt.kotlin.KaptKotlinCustomComponentManager
import com.paulrybitskyi.hiltbinder.sample.ksp.java.custom.KspJavaCustomComponentManager
import com.paulrybitskyi.hiltbinder.sample.ksp.kotlin.KspKotlinCustomComponentManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject lateinit var javacJavaActivityDeps: JavacJavaActivityDeps
    @Inject lateinit var javacJavaCustomComponentManager: JavacJavaCustomComponentManager

    @Inject lateinit var kaptJavaActivityDeps: KaptJavaActivityDeps
    @Inject lateinit var kaptJavaCustomComponentManager: KaptJavaCustomComponentManager

    @Inject lateinit var kaptKotlinActivityDeps: KaptKotlinActivityDeps
    @Inject lateinit var kaptKotlinCustomComponentManager: KaptKotlinCustomComponentManager

    @Inject lateinit var kspJavaActivityDeps: KspJavaActivityDeps
    @Inject lateinit var kspJavaCustomComponentManager: KspJavaCustomComponentManager

    @Inject lateinit var kspKotlinActivityDeps: KspKotlinActivityDeps
    @Inject lateinit var kspKotlinCustomComponentManager: KspKotlinCustomComponentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        javacJavaActivityDeps.check()
        javacJavaCustomComponentManager.onCreateComponent()

        kaptJavaActivityDeps.check()
        kaptJavaCustomComponentManager.onCreateComponent()

        kaptKotlinActivityDeps.check()
        kaptKotlinCustomComponentManager.onCreateComponent()

        kspJavaActivityDeps.check()
        kspJavaCustomComponentManager.onCreateComponent()

        kspKotlinActivityDeps.check()
        kspKotlinCustomComponentManager.onCreateComponent()
    }

    override fun onDestroy() {
        super.onDestroy()

        javacJavaCustomComponentManager.onDestroyComponent()
        kaptJavaCustomComponentManager.onDestroyComponent()
        kaptKotlinCustomComponentManager.onDestroyComponent()
        kspJavaCustomComponentManager.onDestroyComponent()
        kspKotlinCustomComponentManager.onDestroyComponent()
    }
}
