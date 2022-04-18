package com.android.framework.ui.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.android.framework.ui.FWImageView
import kotlinx.parcelize.Parcelize

@Parcelize
data class SrcResource @JvmOverloads constructor(@DrawableRes val image: Int? = null, @DrawableRes private val background: Int? = null) : ImageResource(),
    Parcelable {

    override fun loadImage(imageView: FWImageView) {
        image?.let {
            super.loadImage(imageView)
            imageView.setImageResource(it)
            background?.let {
                FWImageView.setImageData(imageView, background)
            }
        }
    }
}
