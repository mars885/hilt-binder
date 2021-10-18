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
 * Denotes a type that should be exposed by its supertype (either a superclass or an
 * implemented interface) in a specific Dagger Hilt component.
 *
 * Let's say we want to bind a class to its superclass or interface:
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
 * Which generates this:
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
 * What happens if we have a class that has a superclass and also implements some
 * interfaces? In that case, we'll have to manually specify the type we would like to
 * bind to using `to` parameter of the annotation. For example, to bind to an interface:
 *
 * ````kotlin
 * abstract class AbstractImageLoader
 * interface ImageLoader
 *
 * @BindType(to = ImageLoader::class)
 * class PicassoImageLoader @Inject constructor(): AbstractImageLoader(), ImageLoader
 * ````
 *
 * Which generates this:
 *
 * ````java
 * @Module
 * @InstallIn(SingletonComponent.class)
 * public interface HiltBinder_SingletonComponentModule {
 *   @Binds
 *   ImageLoader bind_PicassoImageLoader(PicassoImageLoader binding);
 * }
 * ````
 *
 * The default behavior simply tries to bind to a direct superclass or interface
 * of the annotated type. If the processor cannot deduce the type on its own (e.g., class
 * implements multiple interfaces, has a superclass and implements an interface), then
 * it is going to throw an error to notify you to specify the type to bind to explicitly.
 *
 * It's worth mentioning that if you need to bind to a specific type in your class
 * hierarchy (e.g., superclass of a superclass, extended interface, etc), then you have
 * no other option than specifying a value for the `to` parameter.
 *
 * Dagger Hilt comes with predefined components for Android, but also supports creating
 * custom ones. First, let's see how we can install a binding into predefined components.
 *
 * You've probably noticed that in the previous examples all the generated files have
 * the `@InstallIn(SingletonComponent.class)` annotation. This means that by default
 * all bindings are installed into the Hilt's predefined `SingletonComponent`. There
 * are two ways to change a predefined component: either specify a scope annotation
 * of a predefined component or use the `installIn` parameter of the annotation.
 * For example, have a look at the following code:
 *
 * ````kotlin
 * interface ImageLoader
 * interface Logger
 *
 * @BindType(installIn = BindType.Component.FRAGMENT)
 * class PicassoImageLoader @Inject constructor(): ImageLoader
 *
 * @FragmentScoped
 * @BindType
 * class AndroidLogger @Inject constructor(): Logger
 * ````
 *
 * Which generates this:
 *
 * ````java
 * @Module
 * @InstallIn(FragmentComponent.class)
 * public interface HiltBinder_FragmentComponentModule {
 *   @Binds
 *   ImageLoader bind_PicassoImageLoader(PicassoImageLoader binding);
 *
 *   @Binds
 *   Logger bind_AndroidLogger(AndroidLogger binding);
 * }
 * ````
 *
 * Obviously, the `AndroidLogger` instance will also be **scoped** to the
 * `FragmentComponent`, unlike the `PicassoImageLoader` instance. With the
 * `AndroidLogger` example, we simply leverage the fact that every scope
 * is associated with its corresponding component, therefore, there is no need
 * to specify it again using the `installIn` parameter, though you can.
 *
 * To install a binding into a custom component, assign `BindType.Component.CUSTOM`
 * as the value of the `installIn` parameter and specify a class of the custom
 * component itself through the `customComponent` parameter. For example, take a
 * look at the following code:
 *
 * ````kotlin
 * // A custom component's scope annotation
 * @Scope
 * @Retention(value = AnnotationRetention.RUNTIME)
 * annotation class CustomScope
 *
 * // Declaration of a custom component itself
 * @CustomScope
 * @DefineComponent(parent = SingletonComponent::class)
 * interface CustomComponent
 *
 * interface ImageLoader
 * interface Logger
 *
 * // Binding unscoped type
 * @BindType(
 *   installIn = BindType.Component.CUSTOM,
 *   customComponent = CustomComponent::class
 * )
 * class PicassoImageLoader @Inject constructor(): ImageLoader
 *
 * // Binding scoped type
 * @CustomScope
 * @BindType(
 *   installIn = BindType.Component.CUSTOM,
 *   customComponent = CustomComponent::class
 * )
 * class AndroidLogger @Inject constructor(): Logger
 * ````
 *
 * Which generates the following:
 *
 * ````java
 * @Module
 * @InstallIn(CustomComponent.class)
 * public interface HiltBinder_CustomComponentModule {
 *   @Binds
 *   ImageLoader bind_PicassoImageLoader(PicassoImageLoader binding);
 *
 *   @Binds
 *   Logger bind_AndroidLogger(AndroidLogger binding);
 * }
 * ````
 *
 * [Dagger Multibindings](https://dagger.dev/dev-guide/multibindings.html) are
 * supported too. For example, to contribute elements to a multibound set:
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
 * Which generates this:
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
 * To contribute to a multibound map, we also need to provide the
 * [@Mapkey](https://dagger.dev/api/latest/dagger/MapKey.html) annotation.
 * Dagger has some default ones, like
 * [@ClassKey](https://dagger.dev/api/latest/dagger/multibindings/ClassKey.html) and
 * [@StringKey](https://dagger.dev/api/latest/dagger/multibindings/StringKey.html), but,
 * unfortunately, they cannot be used in this case, because they are only applicable to
 * methods (meaning their `@Target` annotation is equal to `ElementType.METHOD`). Therefore,
 * the library provides its own default ones (`@MapIntKey`, `@MapLongKey`, `@MapStringKey`,
 * and `@MapClassKey`). For example:
 *
 * ````kotlin
 * interface SettingHandler
 *
 * @BindType(contributesTo = BindType.Collection.MAP)
 * @MapStringKey("change_username")
 * class ChangeUsernameSettingHandler @Inject constructor(): SettingHandler
 *
 * @BindType(contributesTo = BindType.Collection.MAP)
 * @MapStringKey("buy_subscription")
 * class BuySubscriptionSettingHandler @Inject constructor(): SettingHandler
 *
 * @BindType(contributesTo = BindType.Collection.MAP)
 * @MapStringKey("log_out")
 * class LogOutSettingHandler @Inject constructor(): SettingHandler
 * ````
 *
 * Which generates this:
 *
 * ````java
 * @Module
 * @InstallIn(SingletonComponent.class)
 * public interface HiltBinder_SingletonComponentModule {
 *   @Binds
 *   @IntoMap
 *   @MapStringKey("change_username")
 *   SettingHandler bind_ChangeUsernameSettingHandler(ChangeUsernameSettingHandler binding);
 *
 *   @Binds
 *   @IntoMap
 *   @MapStringKey("buy_subscription")
 *   SettingHandler bind_BuySubscriptionSettingHandler(BuySubscriptionSettingHandler binding);
 *
 *   @Binds
 *   @IntoMap
 *   @MapStringKey("log_out")
 *   SettingHandler bind_LogOutSettingHandler(LogOutSettingHandler binding);
 * }
 * ````
 *
 * Custom keys are also supported. For example:
 *
 * ````kotlin
 * interface SettingHandler
 *
 * enum class SettingType {
 *
 *   CHANGE_USERNAME,
 *   BUY_SUBSCRIPTION,
 *   LOG_OUT
 *
 * }
 *
 * @MapKey
 * @Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
 * @Retention(AnnotationRetention.BINARY)
 * annotation class SettingMapKey(val type: SettingType)
 *
 * @BindType(contributesTo = BindType.Collection.MAP)
 * @SettingMapKey(SettingType.CHANGE_USERNAME)
 * class ChangeUsernameSettingHandler @Inject constructor(): SettingHandler
 *
 * @BindType(contributesTo = BindType.Collection.MAP)
 * @SettingMapKey(SettingType.BUY_SUBSCRIPTION)
 * class BuySubscriptionSettingHandler @Inject constructor(): SettingHandler
 *
 * @BindType(contributesTo = BindType.Collection.MAP)
 * @SettingMapKey(SettingType.LOG_OUT)
 * class LogOutSettingHandler @Inject constructor(): SettingHandler
 * ````
 *
 * Which generates this:
 *
 * ````java
 * @Module
 * @InstallIn(SingletonComponent.class)
 * public interface HiltBinder_SingletonComponentModule {
 *   @Binds
 *   @IntoMap
 *   @SettingMapKey(SettingType.CHANGE_USERNAME)
 *   SettingHandler bind_ChangeUsernameSettingHandler(ChangeUsernameSettingHandler binding);
 *
 *   @Binds
 *   @IntoMap
 *   @SettingMapKey(SettingType.BUY_SUBSCRIPTION)
 *   SettingHandler bind_BuySubscriptionSettingHandler(BuySubscriptionSettingHandler binding);
 *
 *   @Binds
 *   @IntoMap
 *   @SettingMapKey(SettingType.LOG_OUT)
 *   SettingHandler bind_LogOutSettingHandler(LogOutSettingHandler binding);
 * }
 * ````
 *
 * Qualifiers are supported as well. For the qualifier to be associated with the
 * return type, you need to provide `true` to the `withQualifier` parameter of the
 * annotation. The default value is `false`. Let's have a look at the following example:
 *
 * ````kotlin
 * interface BooksDataStore
 *
 * @BindType(withQualifier = true)
 * @Named("books_local")
 * class BooksLocalDataStore @Inject constructor(): BooksDataStore
 *
 * @BindType(withQualifier = true)
 * @Named("books_remote")
 * class BooksRemoteDataStore @Inject constructor(): BooksDataStore
 * ````
 *
 * Which generates this:
 *
 * ````java
 * @Module
 * @InstallIn(SingletonComponent.class)
 * public interface HiltBinder_SingletonComponentModule {
 *   @Binds
 *   @Named("books_local")
 *   BooksDataStore bind_BooksLocalDataStore(BooksLocalDataStore binding);
 *
 *   @Binds
 *   @Named("books_remote")
 *   BooksDataStore bind_BooksRemoteDataStore(BooksRemoteDataStore binding);
 * }
 * ````
 *
 * Binding generic types is supported as well. It is identical to binding regular
 * types. For example:
 *
 * ````kotlin
 * interface StreamingService<T>
 *
 * class Netflix
 * class Hulu
 *
 * @BindType
 * class NetflixService @Inject constructor(): StreamingService<Netflix>
 *
 * @BindType(to = StreamingService::class)
 * class HuluService @Inject constructor(): StreamingService<Hulu>
 * ````
 *
 * Generates the following:
 *
 * ````java
 * @Module
 * @InstallIn(SingletonComponent.class)
 * public interface HiltBinder_SingletonComponentModule {
 *   @Binds
 *   StreamingService<Netflix> bind_NetflixService(NetflixService binding);
 *
 *   @Binds
 *   StreamingService<Hulu> bind_HuluService(HuluService binding);
 * }
 * ````
 *
 * However, if a binding is contributed to either collection (`SET` or `MAP`), then any
 * type parameters of a generic return type will be replaced with wildcards. For example:
 *
 * ````kotlin
 * interface StreamingService<T>
 *
 * class Netflix
 * class Hulu
 *
 * @BindType(contributesTo = BindType.Collection.SET)
 * class NetflixService @Inject constructor(): StreamingService<Netflix>
 *
 * @BindType(
 *   to = StreamingService::class,
 *   contributesTo = BindType.Collection.SET
 * )
 * class HuluService @Inject constructor(): StreamingService<Hulu>
 * ````
 *
 * Generates the following:
 *
 * ````java
 * @Module
 * @InstallIn(SingletonComponent.class)
 * public interface HiltBinder_SingletonComponentModule {
 *   @Binds
 *   @IntoSet
 *   StreamingService<?> bind_NetflixService(NetflixService binding);
 *
 *   @Binds
 *   @IntoSet
 *   StreamingService<?> bind_HuluService(HuluService binding);
 * }
 * ````
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
     * Denotes a component where bindings can be installed in.
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
     * Denotes a collection where bindings can be contributed to.
     */
    enum class Collection {

        NONE,

        SET,
        MAP
    }
}
