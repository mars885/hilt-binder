# HiltBinder
An annotating processing library that automatically generates Dagger Hilt's `@Binds` methods.

[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](http://developer.android.com/index.html)
[![Download](https://img.shields.io/maven-central/v/com.paulrybitskyi/hilt-binder.svg?label=Download)](https://search.maven.org/search?q=com.paulrybitskyi.hilt-binder)
[![Build](https://github.com/mars885/hilt-binder/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/mars885/hilt-binder/actions)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Contents
* [Motivation](#motivation)
* [Installation](#installation)
  * [Java](#java)
  * [KAPT](#kapt)
  * [KSP](#ksp)
* [Usage](#usage)
  * [Basics](#basics)
  * [Hilt Components](#hilt-components)
    * [Predefined](#predefined)
    * [Custom](#custom)
  * [Multibindings](#multibindings)
  * [Qualifiers](#qualifiers)
  * [Generic Types](#generic-types)
* [Sample](#sample)
* [License](#license)

## Motivation

The main motivation behind this library is to eliminate boilerplate code when binding types in the Dagger Hilt library. Occasionally we have a type that should be exposed only by its supertype (a superclass or an implemented interface). In order to bind the type to its supertype in the Dagger Hilt, we have to write something like this:

````kotlin
@Module
@InstallIn(SingletonComponent::class)
interface BindingsModule {

  @Binds
  fun bindType(type: Type): Supertype

}
````

Can't we just automate this process and instruct the machine to generate a binding for us? This library is the answer to the given question.


## Installation

First of all, make sure that you've added the `mavenCentral()` repository to your top-level `build.gradle` file.

````kotlin
buildscript {
    //...
    repositories {
        //...
        mavenCentral()
    }
    //...
}
````

### Java

If you are using pure Java (no Kotlin code), add the following to your module-level `build.gradle` file.

````kotlin
dependencies {
    implementation("com.paulrybitskyi:hilt-binder:1.1.4")
    annotationProcessor("com.paulrybitskyi:hilt-binder-compiler:1.1.4")
}
````

### KAPT

If you are using Kotlin with Java, then apply the [kapt plugin](https://kotlinlang.org/docs/reference/kapt.html) and declare the compiler dependency using `kapt` instead of `annotationProcessor`.

````kotlin
plugins {
    kotlin("kapt")
}

dependencies {
    implementation("com.paulrybitskyi:hilt-binder:1.1.4")
    kapt("com.paulrybitskyi:hilt-binder-compiler:1.1.4")
}
````

### KSP

A [KSP](https://github.com/google/ksp) implementation of the library. KSP is a replacement for KAPT to run annotation processors natively on the Kotlin compiler, significantly reducing build times.

To use the KSP implementation, go to your project's `settings.gradle.kts` file and add `google()` to `repositories` for the KSP plugin.

````kotlin
pluginManagement {
    repositories {
       gradlePluginPortal()
       google()
    }
}
````

Then, in the module's `build.gradle.kts` file, apply the KSP Gradle plguin and replace the `kapt` configuration in your build file with `ksp`.

````kotlin
plugins {
    id("com.google.devtools.ksp") version "<latestKspVersion>"
}

dependencies {
    implementation("com.paulrybitskyi:hilt-binder:1.1.4")
    ksp("com.paulrybitskyi:hilt-binder-compiler:1.1.4")
}
````

See the [KSP documentation](https://github.com/google/ksp/blob/main/docs/quickstart.md) for more details.

## Usage

### Basics

The main annotation of the library is [@BindType](https://github.com/mars885/hilt-binder/blob/master/hilt-binder/src/main/java/com/paulrybitskyi/hiltbinder/BindType.kt). The annotation has 4 optional parameters, which are going to be explained in the following examples.

Let's say we want to bind a class to its superclass/interface:

````kotlin
abstract class AbstractImageLoader
interface ImageLoader

@BindType
class PicassoImageLoader @Inject constructor(): AbstractImageLoader()

@BindType
class GlideImageLoader @Inject constructor(): ImageLoader
````

Which generates this:

````java
@Module
@InstallIn(SingletonComponent.class)
public interface HiltBinder_SingletonComponentModule {
  @Binds
  AbstractImageLoader bind_PicassoImageLoader(PicassoImageLoader binding);

  @Binds
  ImageLoader bind_GlideImageLoader(GlideImageLoader binding);
}
````

What happens if we have a class that has a superclass and also implements some interfaces? In that case, we'll have to manually specify the type we would like to bind to using `to` parameter of the annotation. For example, to bind to an interface:

````kotlin
abstract class AbstractImageLoader
interface ImageLoader

@BindType(to = ImageLoader::class)
class PicassoImageLoader @Inject constructor(): AbstractImageLoader(), ImageLoader
````

Which generates this:

````java
@Module
@InstallIn(SingletonComponent.class)
public interface HiltBinder_SingletonComponentModule {
  @Binds
  ImageLoader bind_PicassoImageLoader(PicassoImageLoader binding);
}
````

The default behavior simply tries to bind to a direct superclass or interface of the annotated type. If the processor cannot deduce the type on its own (e.g., class implements multiple interfaces, has a superclass and implements an interface), then it is going to throw an error to notify you to specify the type to bind to explicitly.

It's worth mentioning that if you need to bind to a specific type in your class hierarchy (e.g., superclass of a superclass, extended interface, etc.), then you have no other option than specifying a value for the `to` parameter.

### Hilt Components

Dagger Hilt comes with predefined components for Android, but also supports creating custom ones. First, let's see how we can install a binding into predefined components.

#### Predefined

You've probably noticed that in the previous examples all the generated files have the `@InstallIn(SingletonComponent.class)` annotation. This means that by default all bindings are installed into the Hilt's predefined `SingletonComponent`. There are two ways to change a predefined component: either use the `installIn` parameter of the annotation or specify a scope annotation of a predefined component. For example, have a look at the following code:

````kotlin
interface ImageLoader
interface Logger

@BindType(installIn = BindType.Component.FRAGMENT)
class PicassoImageLoader @Inject constructor(): ImageLoader

@FragmentScoped
@BindType
class AndroidLogger @Inject constructor(): Logger
````

Which generates this:

````java
@Module
@InstallIn(FragmentComponent.class)
public interface HiltBinder_FragmentComponentModule {
  @Binds
  ImageLoader bind_PicassoImageLoader(PicassoImageLoader binding);

  @Binds
  Logger bind_AndroidLogger(AndroidLogger binding);
}
````

Obviously, the `AndroidLogger` instance will also be **scoped** to the `FragmentComponent`, unlike the `PicassoImageLoader` instance. With the `AndroidLogger` example, the library simply leverages the fact that every scope is associated with its corresponding component, therefore, there is no need to specify it again using the `installIn` parameter, though you can.

#### Custom

To install a binding into a custom component, assign `BindType.Component.CUSTOM` as the value of the `installIn` parameter and specify a class of the custom component itself through the `customComponent` parameter. It should be mentioned that, unlike with predefined components, simply specifying a scope of the custom component won't work, since it's impossible to infer a class of the custom component from its scope annotation. For example, take a look at the following code:

 ````kotlin
// A custom component's scope annotation
@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class CustomScope

// Declaration of a custom component itself
@CustomScope
@DefineComponent(parent = SingletonComponent::class)
interface CustomComponent

interface ImageLoader
interface Logger

// Binding unscoped type
@BindType(
  installIn = BindType.Component.CUSTOM,
  customComponent = CustomComponent::class
)
class PicassoImageLoader @Inject constructor(): ImageLoader

// Binding scoped type
@CustomScope
@BindType(
  installIn = BindType.Component.CUSTOM,
  customComponent = CustomComponent::class
)
class AndroidLogger @Inject constructor(): Logger

// Won't work, can't infer CustomComponent from CustomScope
// @CustomScope
// @BindType
// class AndroidLogger @Inject constructor(): Logger
````

Which generates the following:

````java
@Module
@InstallIn(CustomComponent.class)
public interface HiltBinder_CustomComponentModule {
  @Binds
  ImageLoader bind_PicassoImageLoader(PicassoImageLoader binding);

  @Binds
  Logger bind_AndroidLogger(AndroidLogger binding);
}
````

### Multibindings

The library also supports [Dagger Multibindings](https://dagger.dev/dev-guide/multibindings.html). For example, to contribute elements to a multibound set:

````kotlin
interface UrlOpener

@BindType(contributesTo = BindType.Collection.SET)
class BrowserUrlOpener @Inject constructor(): UrlOpener

@BindType(contributesTo = BindType.Collection.SET)
class CustomTabUrlOpener @Inject constructor(): UrlOpener

@BindType(contributesTo = BindType.Collection.SET)
class NativeAppUrlOpener @Inject constructor(): UrlOpener
````

Which generates this:

````java
@Module
@InstallIn(SingletonComponent.class)
public interface HiltBinder_SingletonComponentModule {
  @Binds
  @IntoSet
  UrlOpener bind_BrowserUrlOpener(BrowserUrlOpener binding);

  @Binds
  @IntoSet
  UrlOpener bind_CustomTabUrlOpener(CustomTabUrlOpener binding);

  @Binds
  @IntoSet
  UrlOpener bind_NativeAppUrlOpener(NativeAppUrlOpener binding);
}
````

To contribute to a multibound map, we also need to provide the [@Mapkey](https://dagger.dev/api/latest/dagger/MapKey.html) annotation. Dagger has some default ones, like [@ClassKey](https://dagger.dev/api/latest/dagger/multibindings/ClassKey.html) and [@StringKey](https://dagger.dev/api/latest/dagger/multibindings/StringKey.html), but, unfortunately, they cannot be used in this case, because they are only applicable to methods (meaning their `@Target` annotation is equal to `ElementType.METHOD`). Therefore, the library provides its own default ones (`@MapIntKey`, `@MapLongKey`, `@MapStringKey`, and `@MapClassKey`). For example:

````kotlin
interface SettingHandler

@BindType(contributesTo = BindType.Collection.MAP)
@MapStringKey("change_username")
class ChangeUsernameSettingHandler @Inject constructor(): SettingHandler

@BindType(contributesTo = BindType.Collection.MAP)
@MapStringKey("buy_subscription")
class BuySubscriptionSettingHandler @Inject constructor(): SettingHandler

@BindType(contributesTo = BindType.Collection.MAP)
@MapStringKey("log_out")
class LogOutSettingHandler @Inject constructor(): SettingHandler
````

Which generates this:

````java
@Module
@InstallIn(SingletonComponent.class)
public interface HiltBinder_SingletonComponentModule {
  @Binds
  @IntoMap
  @MapStringKey("change_username")
  SettingHandler bind_ChangeUsernameSettingHandler(ChangeUsernameSettingHandler binding);

  @Binds
  @IntoMap
  @MapStringKey("buy_subscription")
  SettingHandler bind_BuySubscriptionSettingHandler(BuySubscriptionSettingHandler binding);

  @Binds
  @IntoMap
  @MapStringKey("log_out")
  SettingHandler bind_LogOutSettingHandler(LogOutSettingHandler binding);
}
````

Custom keys are also supported. For example:

````kotlin
interface SettingHandler

enum class SettingType {

  CHANGE_USERNAME,
  BUY_SUBSCRIPTION,
  LOG_OUT

}

@MapKey
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class SettingMapKey(val type: SettingType)

@BindType(contributesTo = BindType.Collection.MAP)
@SettingMapKey(SettingType.CHANGE_USERNAME)
class ChangeUsernameSettingHandler @Inject constructor(): SettingHandler

@BindType(contributesTo = BindType.Collection.MAP)
@SettingMapKey(SettingType.BUY_SUBSCRIPTION)
class BuySubscriptionSettingHandler @Inject constructor(): SettingHandler

@BindType(contributesTo = BindType.Collection.MAP)
@SettingMapKey(SettingType.LOG_OUT)
class LogOutSettingHandler @Inject constructor(): SettingHandler
````

Which generates this:

````java
@Module
@InstallIn(SingletonComponent.class)
public interface HiltBinder_SingletonComponentModule {
  @Binds
  @IntoMap
  @SettingMapKey(SettingType.CHANGE_USERNAME)
  SettingHandler bind_ChangeUsernameSettingHandler(ChangeUsernameSettingHandler binding);

  @Binds
  @IntoMap
  @SettingMapKey(SettingType.BUY_SUBSCRIPTION)
  SettingHandler bind_BuySubscriptionSettingHandler(BuySubscriptionSettingHandler binding);

  @Binds
  @IntoMap
  @SettingMapKey(SettingType.LOG_OUT)
  SettingHandler bind_LogOutSettingHandler(LogOutSettingHandler binding);
}
````

### Qualifiers

Qualifiers are supported by the library as well. For the qualifier to be associated with the return type, you need to provide `true` to the `withQualifier` parameter of the annotation. The default value is `false`. Let's have a look at the following example:

````kotlin
interface BooksDataStore

@BindType(withQualifier = true)
@Named("books_local")
class BooksLocalDataStore @Inject constructor(): BooksDataStore

@BindType(withQualifier = true)
@Named("books_remote")
class BooksRemoteDataStore @Inject constructor(): BooksDataStore
````

Which generates this:

````java
@Module
@InstallIn(SingletonComponent.class)
public interface HiltBinder_SingletonComponentModule {
  @Binds
  @Named("books_local")
  BooksDataStore bind_BooksLocalDataStore(BooksLocalDataStore binding);

  @Binds
  @Named("books_remote")
  BooksDataStore bind_BooksRemoteDataStore(BooksRemoteDataStore binding);
}
````

### Generic Types

Binding generic types is identical to binding regular types. For example:

````kotlin
interface StreamingService<T>

class Netflix
class Hulu

@BindType
class NetflixService @Inject constructor(): StreamingService<Netflix>

@BindType(to = StreamingService::class)
class HuluService @Inject constructor(): StreamingService<Hulu>
````

Generates the following:

````java
@Module
@InstallIn(SingletonComponent.class)
public interface HiltBinder_SingletonComponentModule {
  @Binds
  StreamingService<Netflix> bind_NetflixService(NetflixService binding);

  @Binds
  StreamingService<Hulu> bind_HuluService(HuluService binding);
}
````

However, if a binding is contributed to either collection (`SET` or `MAP`), then any type parameters of a generic return type will be replaced with wildcards. For example:

````kotlin
interface StreamingService<T>

class Netflix
class Hulu

@BindType(contributesTo = BindType.Collection.SET)
class NetflixService @Inject constructor(): StreamingService<Netflix>

@BindType(
  to = StreamingService::class,
  contributesTo = BindType.Collection.SET
)
class HuluService @Inject constructor(): StreamingService<Hulu>
````

Generates the following:

````java
@Module
@InstallIn(SingletonComponent.class)
public interface HiltBinder_SingletonComponentModule {
  @Binds
  @IntoSet
  StreamingService<?> bind_NetflixService(NetflixService binding);

  @Binds
  @IntoSet
  StreamingService<?> bind_HuluService(HuluService binding);
}
````

## Sample

The project contains a [sample](https://github.com/mars885/hilt-binder/tree/master/sample/src/main/java/com/paulrybitskyi/hiltbinder/sample) application that illustrates the aforementioned examples as well as some advanced ones.

Apart from the sample, you can also take a look at the following repositories that heavily utilize this library:
- [Gamedge](https://github.com/mars885/gamedge) - An Android application for browsing video games and checking the latest gaming news from around the world.
- [DocSkanner](https://github.com/mars885/doc-skanner) - An Android application that makes it possible to automatically scan and digitize documents from photos.

## License

HiltBinder is licensed under the [Apache 2.0 License](LICENSE).
