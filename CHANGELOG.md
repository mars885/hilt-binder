Change Log
==========

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
* Upgrade: [Dagger Hilt 2.35.](https://github.com/google/dagger/releases/tag/dagger-2.35).


## Version 1.0.0-alpha01 *(2021-01-24)*

First Alpha Release.