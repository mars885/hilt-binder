Change Log
==========

## Version 1.1.1 *(2021-10-19)*

Stable KSP support.

* Upgrade: [KSP 1.5.31](https://github.com/google/ksp/releases/tag/1.5.31-1.0.0).
* Upgrade: [Kotlin 1.5.31](https://github.com/JetBrains/kotlin/releases/tag/v1.5.31).
* Upgrade: [Dagger Hilt 2.39.1](https://github.com/google/dagger/releases/tag/dagger-2.39.1).
* Upgrade of other deps: [PR](https://github.com/mars885/hilt-binder/pull/31).
* Cleanup: Added both [detekt](https://github.com/detekt/detekt) & [ktlint](https://github.com/pinterest/ktlint) static analysis tools.

## Version 1.1.0 *(2021-06-01)*

* New: Experimental [KSP](https://github.com/google/ksp) support. KSP is a replacement for KAPT to run annotation processors natively on the Kotlin compiler, significantly reducing build times. To use HiltBinder with KSP, see the [README's KSP section](https://github.com/mars885/hilt-binder#ksp) for installation guide and [KSP documentation](https://github.com/google/ksp/blob/main/docs/quickstart.md) for more details.
* Upgrade: [Kotlin 1.5.10](https://github.com/JetBrains/kotlin/releases/tag/v1.5.10).
* Upgrade: [Dagger Hilt 2.36](https://github.com/google/dagger/releases/tag/dagger-2.36).

## Version 1.0.0 *(2021-05-17)*

First Stable Release.

## Version 1.0.0-beta01 *(2021-05-07)*

First Beta Release.

* Upgrade: [Kotlin 1.5.0](https://github.com/JetBrains/kotlin/releases/tag/v1.5.0).
* Upgrade: [Dagger Hilt 2.35.1](https://github.com/google/dagger/releases/tag/dagger-2.35.1).

## Version 1.0.0-alpha03 *(2021-04-30)*

Third Alpha Release.

* New: It is now possible to specify both scope annotation (e.g., `FragmentScoped`) and predefined component explicitly (the `to` parameter of the `BindType` annotation) at the same time as long as they match (e.g., `ActivityScoped` and `BindType.Component.ACTIVITY`, `ServiceScoped` and `BindType.Component.SERVICE`, and so on). For example:

   ````kotlin
   interface ImageLoader

   // Now possible, previously only either scope or explicit declaration had to be present
   @ActivityScoped
   @BindType(installIn = BindType.Component.ACTIVITY)
   class PicassoImageLoader @Inject constructor(): ImageLoader
   ````

* New: From now on, when installing a binding into a custom component, the `installIn` and the `customComponent` parameters of the `BindType` annotation should be set regardless if the binding is scoped or unscoped. For example:

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

## Version 1.0.0-alpha02 *(2021-04-25)*

Second Alpha Release.

* New: Added support for custom components. Installing a binding into a custom component is similar to installing it in a predefined component: either annotate the type with custom component's scope annotation (if the type needs to be **scoped**) or use the `installIn` and the `customComponent` parameters of the `BindType` annotation. For example:

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

  @CustomScope
  @BindType
  class PicassoImageLoader @Inject constructor(): ImageLoader

  @BindType(
      installIn = BindType.Component.CUSTOM,
      customComponent = CustomComponent::class
  )
  class AndroidLogger @Inject constructor(): Logger
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

* New: Type parameters of generic types are now copied as well when specifying a type to bind to using `to` parameter of the `BindType` annotation. For example:

   ````kotlin
  interface ImageLoader<T>

  @BindType
  class PicassoImageLoader @Inject constructor(): ImageLoader<Picasso>

  @BindType(to = ImageLoader::class)
  class GlideImageLoader @Inject constructor(): ImageLoader<Glide>
  ````

  Which generates the following:

  ````java
  @Module
  @InstallIn(SingletonComponent.class)
  public interface HiltBinder_SingletonComponentModule {
    @Binds
    ImageLoader<Picasso> bind_PicassoImageLoader(PicassoImageLoader binding);

    @Binds
    ImageLoader<Glide> bind_GlideImageLoader(GlideImageLoader binding);
    // ImageLoader bind_GlideImageLoader(GlideImageLoader binding); in alpha01
  }
  ````
* New: Type parameters of generic types are replaced with wildcards if a binding is contributed to either `SET` or `MAP` collection. For example:

   ````kotlin
  interface ImageLoader<T>

  @BindType(contributesTo = BindType.Collection.MAP)
  @MapClassKey(PicassoImageLoader::class)
  class PicassoImageLoader @Inject constructor(): ImageLoader<Picasso>

  @BindType(contributesTo = BindType.Collection.MAP)
  @MapClassKey(GlideImageLoader::class)
  class GlideImageLoader @Inject constructor(): ImageLoader<Glide>

  @BindType(contributesTo = BindType.Collection.MAP)
  @MapClassKey(CoilImageLoader::class)
  class CoilImageLoader @Inject constructor(): ImageLoader<Coil>
  ````

  Which generates the following:

  ````java
  @Module
  @InstallIn(SingletonComponent.class)
  public interface HiltBinder_SingletonComponentModule {
    @Binds
    @IntoMap
    @MapClassKey(PicassoImageLoader.class)
    ImageLoader<?> bind_PicassoImageLoader(PicassoImageLoader binding);

    @Binds
    @IntoMap
    @MapClassKey(GlideImageLoader.class)
    ImageLoader<?> bind_GlideImageLoader(GlideImageLoader binding);

    @Binds
    @IntoMap
    @MapClassKey(GlideImageLoader.class)
    ImageLoader<?> bind_CoilImageLoader(CoilImageLoader binding);
  }
  ````
* New: Added Javadoc documentation for public API.
* Upgrade: [Kotlin 1.4.32](https://github.com/JetBrains/kotlin/releases/tag/v1.4.32).
* Upgrade: [Dagger Hilt 2.35](https://github.com/google/dagger/releases/tag/dagger-2.35).


## Version 1.0.0-alpha01 *(2021-01-24)*

First Alpha Release.
