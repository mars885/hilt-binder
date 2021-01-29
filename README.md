# HiltBinder
An annotating processing library that automatically generates Dagger Hilt's `@Binds` methods.

[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](http://developer.android.com/index.html)
[![Download](https://api.bintray.com/packages/mars885/maven/hilt-binder/images/download.svg)](https://bintray.com/mars885/maven/hilt-binder/_latestVersion)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Contents
* [Motivation](#motivation)
* [Installation](#installation)
* [Usage](#usage)
  * [Basics](#basics)
  * [Hilt Components](#hilt-components)
  * [Multibindings](#multibindings)
  * [Qualifiers](#qualifiers)
* [Sample](#sample)
* [License](#license)

## Motivation

The main motivation behind this library is to eliminate boilerplate code when binding types in the Dagger Hilt library. Every now and then we have a type that should be exposed only by its supertype (a superclass or an implemented interface). In order to bind the type to its supertype in the Dagger Hilt, we have to write something like this:

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

First of all, make sure that you've added the `jcenter()` repository to your top-level `build.gradle` file.

````groovy
buildscript {
    //...
    repositories {
        //...
        jcenter()
    }
    //...
}
````

Then, if you are using Java, add the following to your module-level `build.gradle` file.

````groovy
dependencies {
    implementation "com.paulrybitskyi:hilt-binder:1.0.0-alpha01"
    annotationProcessor "com.paulrybitskyi:hilt-binder-compiler:1.0.0-alpha01"
}
````

If you are using Kotlin, then apply the [kapt plugin](https://kotlinlang.org/docs/reference/kapt.html) and declare the compiler dependency using `kapt` instead of `annotationProcessor`.

````groovy
dependencies {
    implementation "com.paulrybitskyi:hilt-binder:1.0.0-alpha01"
    kapt "com.paulrybitskyi:hilt-binder-compiler:1.0.0-alpha01"
}
````

## Usage

### Basics

The main annotation of the library is [@BindType](https://github.com/mars885/hilt-binder/blob/master/hilt-binder/src/main/java/com/paulrybitskyi/hiltbinder/BindType.kt). The annotation has 4 optional parameters, which are gonna be explained in the following examples.

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

What happens if we have a class that has a superclass and also implements some interfaces? In that case, we'll have to manually specify the type we would like to bind to using `to` parameter of the annoation. For example, to bind to an interface:

````kotlin
abstract class AbstractImageLoader
interface ImageLoader

@BindType(ImageLoader::class)
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

The default behavior simply tries to bind to a direct superclass or interface of the annotated type. If the library cannot deduce the type on its own (e.g., class implements multiple interfaces, has a superclass and implements an interface), then the processor is going to throw an error to notify you to specify the type to bind to explicitly.

It's worth mentioning that if you need to bind to a specific type in your class hierarchy (e.g., superclass of a superclass, extented interface, etc), then you have no other option than specifying a value for the `to` parameter.

### Hilt Components

You've probably noticed that in the previous examples all the generated files had the `@InstallIn(SingletonComponent.class)` annotation. This means that by default all bindings are installed in the Hilt's `SingletonComponent`. There are two ways to change where to install the binding: either specify one of the Hilt's scope annotations or use `installIn` parameter of the annotation. For example, have a look at the following code:

````kotlin
interface ImageLoader
interface Logger

@FragmentScoped
@BindType
class PicassoImageLoader @Inject constructor(): ImageLoader

@BindType(installIn = BindType.Component.FRAGMENT)
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

Obviously, the `PicassoImageLoader` instance will also be **scoped** to the `FragmentComponent`, unlike the `AndroidLogger` instance. With the `PicassoImageLoader` example, the library simply leverages the fact that every scope is associated with its corresponding component, therefore, there is no need to specify it again using the `installIn` parameter.

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

## Sample

The project contains a [sample](https://github.com/mars885/hilt-binder/tree/master/sample/src/main/java/com/paulrybitskyi/hiltbinder/sample) application that illustrates the aforementioned examples as well as some advanced ones.

## License

HiltBinder is licensed under the [Apache 2.0 License](LICENSE).