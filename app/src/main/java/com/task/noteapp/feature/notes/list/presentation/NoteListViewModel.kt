package com.task.noteapp.feature.notes.list.presentation

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.noteapp.base.NoteAppViewModel
import com.task.noteapp.db.entity.NoteEntity
import com.task.noteapp.feature.notes.list.domain.interactor.GetNotes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getNotes: GetNotes
): NoteAppViewModel() {

    private var listMutableLiveData = MutableLiveData<List<NoteEntity>>()
    val listLiveData: LiveData<List<NoteEntity>> get() = listMutableLiveData

    fun getNotes() {
        addDisposable(
            getNotes.execute(Unit).subscribe(
                {
                    listMutableLiveData.value = it
                },
                {
                    getErrorLiveData().postValue("Error!")
                }
            )
        )
    }
}