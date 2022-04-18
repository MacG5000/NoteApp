package com.android.framework.ui.model

interface BasicListItem<T> {

    fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    fun areContentsTheSame(oldItem: T, newItem: T): Boolean
}