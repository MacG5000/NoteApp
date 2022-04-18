package com.android.framework.ui.recyclerview

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.android.framework.ui.model.BasicListItem

class BasicRecyclerViewHolder<Item: BasicListItem<*>, DB: ViewDataBinding>(val binding: DB) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.executePendingBindings()
    }
}