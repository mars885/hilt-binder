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

package com.paulrybitskyi.hiltbinder.sample.javac.custom.deps;

import com.paulrybitskyi.hiltbinder.BindType;
import com.paulrybitskyi.hiltbinder.sample.javac.custom.JavacJavaCustomComponent;

import javax.inject.Inject;

@BindType(
    installIn = BindType.Component.CUSTOM,
    customComponent = JavacJavaCustomComponent.class
)
public class JavacJavaCustomComponentDep1Impl implements JavacJavaCustomComponentDep1 {

    @Inject
    public JavacJavaCustomComponentDep1Impl() {}
}
