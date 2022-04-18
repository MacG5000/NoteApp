package com.android.framework.ui.model

import android.os.Parcelable
import com.android.framework.ui.FWImageView

abstract class ImageResource : Parcelable {

    var imageOptions: ImageOptions? = null

    /**
     * Loads image
     */
    open fun loadImage(imageView: FWImageView) {
        imageOptions?.let {
            FWImageView.setImageOptions(imageView, it)
        }
    }
}