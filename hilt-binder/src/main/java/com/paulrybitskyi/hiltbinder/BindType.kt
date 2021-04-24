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

package com.paulrybitskyi.hiltbinder

import kotlin.reflect.KClass

/**
 * Denotes a type that should be exposed by its supertype
 * (either a superclass or an implemented interface) in a
 * specific Dagger Hilt component.
 *
 * For example, the below snippet:
 *
 * ````kotlin
 * abstract class AbstractImageLoader
 * interface ImageLoader
 *
 * @BindType
 * class PicassoImageLoader @Inject constructor(): AbstractImageLoader()
 *
 * @BindType
 * class GlideImageLoader @Inject constructor(): ImageLoader
 * ````
 *
 * Generates the following code:
 *
 * ````java
 * @Module
 * @InstallIn(SingletonComponent.class)
 * public interface HiltBinder_SingletonComponentModule {
 *   @Binds
 *   AbstractImageLoader bind_PicassoImageLoader(PicassoImageLoader binding);
 *
 *   @Binds
 *   ImageLoader bind_GlideImageLoader(GlideImageLoader binding);
 * }
 * ````
 *
 * Visit [Hilt Binder](https://github.com/mars885/hilt-binder) for more info.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class BindType(
    val to: KClass<*> = Nothing::class,
    val installIn: Component = Component.NONE,
    val customComponent: KClass<*> = Nothing::class,
    val contributesTo: Collection = Collection.NONE,
    val withQualifier: Boolean = false
) {


    /**
     * Denotes a Dagger Hilt's component where a binding should be
     * installed in.
     *
     * For example, the below snippet:
     *
     * ````kotlin
     * abstract class AbstractImageLoader
     * interface ImageLoader
     *
     * @BindType(installIn = BindType.Component.FRAGMENT)
     * class PicassoImageLoader @Inject constructor(): AbstractImageLoader()
     *
     * @BindType(installIn = BindType.Component.FRAGMENT)
     * class GlideImageLoader @Inject constructor(): ImageLoader
     * ````
     *
     * Generates the following code:
     *
     * ````java
     * @Module
     * @InstallIn(FragmentComponent.class)
     * public interface HiltBinder_FragmentComponentModule {
     *   @Binds
     *   AbstractImageLoader bind_PicassoImageLoader(PicassoImageLoader binding);
     *
     *   @Binds
     *   ImageLoader bind_GlideImageLoader(GlideImageLoader binding);
     * }
     * ````
     *
     * Visit [Hilt Binder](https://github.com/mars885/hilt-binder) for more info.
     */
    enum class Component {

        NONE,

        SINGLETON,
        ACTIVITY_RETAINED,
        SERVICE,
        ACTIVITY,
        VIEW_MODEL,
        FRAGMENT,
        VIEW,
        VIEW_WITH_FRAGMENT,

        CUSTOM

    }


    /**
     * Denotes a collection where Dagger bindings can be contributed to.
     *
     * For example, to contribute bindings into a SET collection, you'd
     * write something like this:
     *
     * ````kotlin
     * interface UrlOpener
     *
     * @BindType(contributesTo = BindType.Collection.SET)
     * class BrowserUrlOpener @Inject constructor(): UrlOpener
     *
     * @BindType(contributesTo = BindType.Collection.SET)
     * class CustomTabUrlOpener @Inject constructor(): UrlOpener
     *
     * @BindType(contributesTo = BindType.Collection.SET)
     * class NativeAppUrlOpener @Inject constructor(): UrlOpener
     * ````
     *
     * Which generates the following code:
     *
     * ````java
     * @Module
     * @InstallIn(SingletonComponent.class)
     * public interface HiltBinder_SingletonComponentModule {
     *   @Binds
     *   @IntoSet
     *   UrlOpener bind_BrowserUrlOpener(BrowserUrlOpener binding);
     *
     *   @Binds
     *   @IntoSet
     *   UrlOpener bind_CustomTabUrlOpener(CustomTabUrlOpener binding);
     *
     *   @Binds
     *   @IntoSet
     *   UrlOpener bind_NativeAppUrlOpener(NativeAppUrlOpener binding);
     * }
     * ````
     *
     * See [Hilt Binder](https://github.com/mars885/hilt-binder) for more info.
     */
    enum class Collection {

        NONE,

        SET,
        MAP

    }


}