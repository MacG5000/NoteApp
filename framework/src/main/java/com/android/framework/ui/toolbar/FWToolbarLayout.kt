package com.android.framework.ui.toolbar

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.Toolbar
import com.android.framework.R
import com.android.framework.ui.BaseLayoutComponent

class FWToolbarLayout (
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0): Toolbar(context, attrs, defStyleAttrs), BaseLayoutComponent {

    companion object {
        val LAYOUT_RES_ID: Int = R.layout.toolbar_view
    }

    private val leftIconCount = 0
    private val rightIconCount = 0



}