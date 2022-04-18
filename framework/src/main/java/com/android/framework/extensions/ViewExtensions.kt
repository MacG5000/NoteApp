package com.android.framework.extensions

import android.content.res.Resources

/**
 * Px to Dp
 */
val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

/**
 * Px to Dp
 */
val Float.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

/**
 * Dp to Px
 */
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Dp to Px
 */
val Float.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
