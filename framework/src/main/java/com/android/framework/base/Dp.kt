package com.android.framework.base

import androidx.annotation.Dimension

/**
 * Denotes that an integer parameter, field or method return value is expected
 * to represent a density-independent pixel dimension.
 *
 * This annotation is created with reference to @see [androidx.annotation.Px].
 */
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FIELD,
    AnnotationTarget.LOCAL_VARIABLE
)
@Dimension(unit = Dimension.DP)
annotation class Dp
