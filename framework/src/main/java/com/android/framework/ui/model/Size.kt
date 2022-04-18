package com.android.framework.ui.model

import android.os.Parcelable
import android.view.ViewGroup
import com.android.framework.base.Dp
import com.android.framework.extensions.px
import kotlinx.parcelize.Parcelize

@Parcelize
data class Size(
    @Dp var width: Int? = null,
    @Dp var height: Int? = null
) : Parcelable {

    companion object {
        /**
         * @return real size for view's width and height.
         */
        internal val Int.realSize: Int
            @JvmName("getRealSize")
            get() = if (this == ViewGroup.LayoutParams.WRAP_CONTENT || this == ViewGroup.LayoutParams.MATCH_PARENT) {
                this
            } else {
                this.px
            }
    }
}
