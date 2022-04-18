package com.android.framework.ui.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.android.framework.ui.model.BasicListItem

class RecyclerItemCallback<Item: BasicListItem<Item>>: DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.areContentsTheSame(oldItem, newItem)
    }

}