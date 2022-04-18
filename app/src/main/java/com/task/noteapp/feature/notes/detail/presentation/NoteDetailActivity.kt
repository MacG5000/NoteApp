package com.task.noteapp.feature.notes.detail.presentation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.android.framework.extensions.observeLiveData
import com.task.noteapp.R
import com.task.noteapp.base.NoteAppActivity
import com.task.noteapp.databinding.ActivityNoteDetailBinding
import com.task.noteapp.db.entity.NoteEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class NoteDetailActivity: NoteAppActivity<NoteDetailViewModel, ActivityNoteDetailBinding>() {

    companion object {
        private const val LAYOUT_RES_ID = R.layout.activity_note_detail

        fun newIntent(context: Context?, data: NavigationData?) = newIntent(
            NoteDetailActivity::class.java,
            context,
            data
        )
    }

    @Parcelize
    data class NavigationData(val note: NoteEntity?): com.task.noteapp.base.navigation.NavigationData

    private val vm: NoteDetailViewModel by viewModels()

    override fun provideViewModel() = vm

    override fun provideLayoutResId() = LAYOUT_RES_ID

    override fun bindViewModel(binding: ActivityNoteDetailBinding) {
        binding.viewModel = getViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel().parseIntent(intent)
        if(!vm.hasData()) {
            getBinding().deleteButton.visibility = View.GONE
        }
        observeLiveData(vm.operationCompleteLiveData) {
            setResult(Activity.RESULT_OK)
            finish()
        }
        getBinding().addButton.setOnClickListener {
            vm.save()
        }

        getBinding().deleteButton.setOnClickListener {
            vm.deleteNote()
        }
    }

}