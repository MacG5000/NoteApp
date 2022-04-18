package com.android.framework.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import com.android.framework.GlideApp
import com.android.framework.GlideRequest
import com.android.framework.R
import com.android.framework.extensions.px
import com.android.framework.extensions.pxToDp
import com.android.framework.ui.model.ImageOptions
import com.android.framework.ui.model.ImageResource
import com.android.framework.ui.model.Size
import com.android.framework.ui.model.Size.Companion.realSize
import com.bumptech.glide.request.RequestOptions

class FWImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0): AppCompatImageView(context, attrs, defStyleAttrs), BaseLayoutComponent {

        companion object {
            private const val DEFAULT_BORDER_WIDTH = 0
            private const val DEFAULT_BORDER_COLOR = Color.WHITE
            private const val DEFAULT_BACKGROUND_COLOR = Color.WHITE
            private const val DEFAULT_TEXT_SIZE = -1
            private const val DEFAULT_TEXT_COLOR = Color.WHITE
            private const val DEFAULT_IS_CIRCULAR = false

            /**
             * Binding adapter to manage [ImageResource] types
             *
             * @param imageView     to set an [ImageResource]
             * @param imageResource one of [ImageResource]
             */
            @BindingAdapter(value = ["app:imageResource"])
            @JvmStatic
            fun setImageResource(
                imageView: FWImageView,
                imageResource: ImageResource?
            ) {
                imageResource?.loadImage(imageView)
            }

            @BindingAdapter(value = ["android:backgroundResource"], requireAll = false)
            @JvmStatic
            fun setImageData(
                imageView: FWImageView,
                @DrawableRes resourceId: Int
            ) {
                if (resourceId != 0) {
                    imageView.background = ContextCompat.getDrawable(
                        imageView.context,
                        resourceId
                    )
                }
            }

            /**
             * Binding adapters
             */

            @BindingAdapter(value = ["app:circularPadding"])
            @JvmStatic
            fun setCircularPadding(
                imageView: FWImageView,
                padding: Int
            ) {
                imageView.circularPadding = padding.px
                imageView.setIsCircular(true)
            }

            @BindingAdapter(value = ["app:imageOptions"])
            @JvmStatic
            fun setImageOptions(
                imageView: FWImageView,
                imageOptions: ImageOptions?
            ) {
                if (imageOptions != null) {
                    imageView.setSize(imageOptions.size)
                    imageOptions.text?.let { imageView.setText(it) }
                    imageOptions.circularPadding?.let {
                        setCircularPadding(imageView, it)
                    }
                    imageOptions.borderWidth?.let {
                        imageView.setBorderWidth(it)
                    }
                    imageOptions.borderColor?.let { imageView.setBorderColor(it) }
                    imageOptions.tintColor?.let {
                        ImageViewCompat.setImageTintList(
                            imageView,
                            AppCompatResources.getColorStateList(
                                imageView.context,
                                it
                            )
                        )
                    }
                    imageOptions.scaleType?.let { imageView.scaleType = it }
                    imageView.setIsCircular(imageOptions.isCircular)
                }
            }

            @BindingAdapter(value = ["srcGlide", "requestOptions"], requireAll = false)
            @JvmStatic
            fun setGlideSource(
                imageView: FWImageView,
                source: Any?,
                requestOptions: RequestOptions?
            ) {
                // Tag set for testing
                imageView.setTag(imageView.id, source)
                val glideRequests: GlideRequest<Drawable> =
                    GlideApp.with(imageView.context).load(source)
                if (requestOptions != null) {
                    glideRequests.apply(requestOptions)
                }
                glideRequests.into(imageView)
            }
        }

    // Object used to draw
    private var image: Bitmap? = null
    //private var drawable: Drawable? = null
    private var paint: Paint? = null
    private var paintBorder: Paint? = null
    private var paintBackground: Paint? = null
    private var textPaint: TextPaint? = null
    //private var colorFilter: ColorFilter? = null
    private var text: String? = null
    private var borderWidth = -0.4f
    private var textSize = 0
    private var textColor = 0
    private var canvasSize = 0
    private var isCircular = false
    private var circularPadding = 0

    init {
        paint = Paint()
        paint?.isAntiAlias = true

        paintBorder = Paint()
        paintBorder?.isAntiAlias = true

        paintBackground = Paint()
        paintBackground?.isAntiAlias = true

        textPaint = TextPaint()
        textPaint?.isAntiAlias = true

        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.FWImageView, defStyleAttrs, 0)
        val borderW = attributes.getInteger(
            R.styleable.FWImageView_fwImageViewCircularBorderWidth,
            DEFAULT_BORDER_WIDTH
        )

        if (borderW != DEFAULT_BORDER_WIDTH) {
            val borderSize: Int =  borderW.pxToDp(context)
            val borderColor = attributes.getColor(
                R.styleable.FWImageView_fwImageViewCircularBorderColor,
                DEFAULT_BORDER_COLOR
            )
            setBorderWidth(borderSize.toFloat())
            setBorderColor(borderColor)
        }

        text = attributes.getString(R.styleable.FWImageView_fwImageViewCircularText)

        textColor = attributes.getColor(
            R.styleable.FWImageView_fwImageViewCircularTextColor,
            DEFAULT_TEXT_COLOR
        )

        textSize = attributes.getInt(
            R.styleable.FWImageView_fwImageViewCircularTextSize,
            DEFAULT_TEXT_SIZE
        )

        setBackgroundColor(
            attributes.getColor(
                R.styleable.FWImageView_fwImageViewCircularBackgroundColor,
                DEFAULT_BACKGROUND_COLOR
            )
        )
        isCircular = attributes.getBoolean(
            R.styleable.FWImageView_fwImageViewIsCircular,
            DEFAULT_IS_CIRCULAR
        )

        attributes.recycle()
    }

    fun setIsCircular(isCircular: Boolean) {
        this.isCircular = isCircular
        invalidate()
    }

    private fun setSize(size: Size?) {
        size?.width?.let {
            layoutParams.width = it.realSize
        }
        size?.height?.let {
            layoutParams.height = it.realSize
        }
    }

    fun setBorderWidth(borderWidth: Float) {
        this.borderWidth = borderWidth
        requestLayout()
        invalidate()
    }

    fun setText(text: String?) {
        this.text = text
        invalidate()
    }

    fun setBorderColor(@ColorInt borderColor: Int) {
        paintBorder?.let {
            it.color = borderColor
            invalidate()
        }
    }

}