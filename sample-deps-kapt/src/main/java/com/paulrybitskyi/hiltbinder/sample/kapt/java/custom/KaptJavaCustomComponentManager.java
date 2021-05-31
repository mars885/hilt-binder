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

package com.paulrybitskyi.hiltbinder.sample.kapt.java.custom;

import com.paulrybitskyi.hiltbinder.sample.kapt.java.custom.deps.KaptJavaCustomComponentDep1;
import com.paulrybitskyi.hiltbinder.sample.kapt.java.custom.deps.KaptJavaCustomComponentDep2;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.EntryPoint;
import dagger.hilt.EntryPoints;
import dagger.hilt.InstallIn;

public class KaptJavaCustomComponentManager {


    private final KaptJavaCustomComponent.Builder customComponentBuilder;
    private KaptJavaCustomComponent customComponent;

    private KaptJavaCustomComponentDep1 customComponentDep1;
    private KaptJavaCustomComponentDep2 customComponentDep2;


    @EntryPoint
    @InstallIn(KaptJavaCustomComponent.class)
    interface DependenciesProvider {

        KaptJavaCustomComponentDep1 getCustomComponentDep1();
        KaptJavaCustomComponentDep2 getCustomComponentDep2();

    }


    @Inject
    public KaptJavaCustomComponentManager(
        KaptJavaCustomComponent.Builder customComponentBuilder
    ) {
        this.customComponentBuilder = customComponentBuilder;
    }


    public void onCreateComponent() {
        customComponent = customComponentBuilder.build();

        DependenciesProvider dependenciesProvider = EntryPoints.get(
            customComponent,
            DependenciesProvider.class
        );

        customComponentDep1 = dependenciesProvider.getCustomComponentDep1();
        customComponentDep2 = dependenciesProvider.getCustomComponentDep2();

        Objects.requireNonNull(customComponentDep1);
        Objects.requireNonNull(customComponentDep2);
    }


    public void onDestroyComponent() {
        customComponent = null;
    }


}