package com.paulrybitskyi.hiltbinder

/**
 * Denotes a annotation that can be used as [BindType] with specified params.
 *
 * Example for view models used in a RecyclerView in a fragment. We can create annotation:
 * ````kotlin
 *  @AsBindType(
 *      BindType(
 *          to = ViewModel::class,
 *          contributesTo = BindType.Collection.MAP,
 *          installIn = BindType.Component.FRAGMENT,
 *      )
 *  )
 *  @MapClassKey
 *  annotation class FragmentViewModel
 * ````
 * Then mark some view model with it:
 * ````kotlin
 *  @FragmentViewModel
 *  open class InstrumentItemViewModel @Inject constructor(
 *      private val store: IStore,
 *      private val coordinator: ICoordinator,
 *  ) : ViewModel() { ... }
 * ````
 * And after that use it in some ViewModelFactory:
 * ````kotlin
 *  class ViewModelFactory @Inject constructor(
 *      private val creators: @JvmSuppressWildcards Map<Class<*>, Provider<ViewModel>>
 *  ) : ViewModelProvider.Factory { ... }
 * ````
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class AsBindType(
    val bindType: BindType,
)
