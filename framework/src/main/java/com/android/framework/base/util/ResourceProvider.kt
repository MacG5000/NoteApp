package com.android.framework.base.util

import android.content.Context
import android.content.res.Resources.Theme
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import javax.inject.Inject

class ResourceProvider @Inject constructor(val context: Context){

    fun getString(@StringRes resId: Int): String? {
        return context.getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String? {
        return context.getString(resId, *formatArgs)
    }

    fun getDrawable(@DrawableRes resId: Int): Drawable? {
        return AppCompatResources.getDrawable(context, resId)
    }

    fun getColor(@ColorRes resId: Int): Int {
        return this.getColor(resId, null as Theme?)
    }

    fun getColor(@ColorRes resId: Int, theme: Theme?): Int {
        return ResourcesCompat.getColor(context.resources, resId, theme)
    }

    fun getDimension(@DimenRes resId: Int): Float {
        return context.resources.getDimension(resId)
    }

    fun getDimensionPixelSize(@DimenRes resId: Int): Int {
        return context.resources.getDimensionPixelSize(resId)
    }

    fun dpToPx(dpSize: Int): Int {
        return (dpSize.toFloat() * context.resources.displayMetrics.density).toInt()
    }

    fun pxToDp(@Px pixelSize: Int): Int {
        return (pixelSize.toFloat() / context.resources.displayMetrics.density).toInt()
    }
}