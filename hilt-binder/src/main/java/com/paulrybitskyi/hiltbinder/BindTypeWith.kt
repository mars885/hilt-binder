package com.paulrybitskyi.hiltbinder

import kotlin.reflect.KClass

/**
 * Like [BindType] but with predefined params thought [AsBindType].
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
 *  @BindTypeWith(FragmentViewModel::class)
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
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class BindTypeWith(
    vararg val value: KClass<out Annotation>,
)
