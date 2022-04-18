package com.android.framework.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.android.framework.ui.model.BasicListItem

abstract class BasicRecyclerViewAdapter<Item: BasicListItem<*>, DB: ViewDataBinding> (@LayoutRes val layoutResId: Int):
    ListAdapter<Item, BasicRecyclerViewHolder<Item, DB>>(RecyclerItemCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BasicRecyclerViewHolder<Item, DB> {
        val binding: DB = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            this.layoutResId,
            parent,
            false
        )
        return BasicRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BasicRecyclerViewHolder<Item, DB>, position: Int) {
        this.bindItem(holder.binding, getItem(position))
        holder.bind()
    }

    abstract fun bindItem(binding: DB, item: Item)


}