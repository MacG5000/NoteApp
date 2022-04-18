package com.android.framework.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class FWLabel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0): AppCompatTextView(context, attrs, defStyleAttrs), BaseLayoutComponent