package com.task.noteapp.feature.notes.list.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.framework.extensions.observeLiveData
import com.task.noteapp.R
import com.task.noteapp.base.NoteAppActivity
import com.task.noteapp.constants.RequestCodes
import com.task.noteapp.databinding.ActivityNoteListBinding
import com.task.noteapp.db.entity.NoteEntity
import com.task.noteapp.feature.notes.detail.presentation.NoteDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class NoteListActivity : NoteAppActivity<NoteListViewModel, ActivityNoteListBinding>() {

    companion object {
        private const val LAYOUT_RES_ID = R.layout.activity_note_list

        fun newIntent(context: Context?) = newIntent(
            NoteListActivity::class.java,
            context
        )
    }

    private val vm: NoteListViewModel by viewModels()

    var adapter: NoteListAdapter? = null

    override fun provideViewModel() = vm

    override fun provideLayoutResId() = LAYOUT_RES_ID

    override fun bindViewModel(binding: ActivityNoteListBinding) {
        binding.viewModel = getViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        getViewModel().getNotes()
        getBinding().addButton.setOnClickListener {
            startDetailActivity(null)
        }
        adapter = NoteListAdapter(object : NoteListAdapter.OnItemClickListener {
            override fun onItemClick(id: Int) {
                startDetailActivity(getViewModel().listLiveData.value?.find {
                    it.uid == id
                })
            }
        })
        getBinding().recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        observeLiveData(getViewModel().listLiveData) {
            if (it.isEmpty()) {
                getBinding().emptyImage.visibility = View.VISIBLE
                getBinding().recyclerView.visibility = View.GONE
            } else {
                getBinding().emptyImage.visibility = View.GONE
                getBinding().recyclerView.visibility = View.VISIBLE
                if(it.isNotEmpty()) {
                    adapter?.submitList(it.map {entity ->
                        entity.toViewEntity()
                    })
                    getBinding().recyclerView.adapter = adapter
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RequestCodes.NoteDetail && resultCode == Activity.RESULT_OK) {
            getViewModel().getNotes()
        }
    }

    private fun startDetailActivity(note: NoteEntity?) {
        navigateForResult(
            NoteDetailActivity.newIntent(
                this,
                NoteDetailActivity.NavigationData(note)
            ),
            RequestCodes.NoteDetail
        )
    }
}