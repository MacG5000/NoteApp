package com.android.framework.ui.model

import android.os.Parcelable
import com.android.framework.ui.FWImageView
import com.bumptech.glide.request.RequestOptions
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class UrlResource @JvmOverloads constructor(
    private val image: String?,
    var requestOptions: @RawValue RequestOptions? = null
) : ImageResource(), Parcelable {

    override fun loadImage(imageView: FWImageView) {
        super.loadImage(imageView)
        FWImageView.setGlideSource(imageView, image, requestOptions)
    }
}
