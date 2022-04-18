package com.task.noteapp.feature.notes.list.presentation

import android.view.View
import com.android.framework.ui.FWImageView
import com.android.framework.ui.model.ImageOptions
import com.android.framework.ui.model.SrcResource
import com.android.framework.ui.model.UrlResource
import com.android.framework.ui.recyclerview.BasicRecyclerViewAdapter
import com.bumptech.glide.request.RequestOptions
import com.task.noteapp.R
import com.task.noteapp.databinding.ItemNoteBinding
import com.task.noteapp.db.entity.NoteEntity

class NoteListAdapter(val listener: OnItemClickListener?) :
    BasicRecyclerViewAdapter<NoteEntity.ViewEntity, ItemNoteBinding>(
        R.layout.item_note
    ) {

    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }

    val imageOptions = ImageOptions().apply {
        isCircular = true
    }

    override fun bindItem(binding: ItemNoteBinding, item: NoteEntity.ViewEntity) {
        binding.item = item
        item.imageUrl?.let {
            FWImageView.setImageResource(
                binding.image,
                UrlResource(it, RequestOptions().optionalCenterInside())
            )
        } ?: FWImageView.setImageResource(binding.image, SrcResource(R.drawable.notepad))
        if (item.isEdited) {
            binding.editedImage.visibility = View.VISIBLE
        } else {
            binding.editedImage.visibility = View.GONE
        }
        binding.root.setOnClickListener { listener?.onItemClick(item.uid) }
    }
}
