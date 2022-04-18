package com.android.framework.ui.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import com.android.framework.base.Task
import com.android.framework.extensions.pxToDp

class ToolbarItemView (
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0): View(context, attrs, defStyleAttrs) {

    private var itemTask: Task? = null

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val margins =
            MarginLayoutParams::class.java.cast(this.layoutParams) as MarginLayoutParams
        val margin: Int = this.getMargin()
        margins.topMargin = 0
        margins.bottomMargin = 0
        margins.rightMargin = margin
        this.layoutParams = margins
    }

    fun getItemTask(): Task? {
        return itemTask
    }

    fun setItemTask(itemTask: Task?) {
        this.itemTask = itemTask
    }

    fun getMargin(): Int {
        return 12.pxToDp(this.context)
    }

    fun setSafeOnClickListener(onSafeClick: Function1<View?, Unit>) {
        this.setSafeOnClickListener(onSafeClick)
    }

}