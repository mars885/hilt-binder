package com.paulrybitskyi.hiltbinder

/**
 *
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class AsBindType(
    val bindType: BindType,
)
