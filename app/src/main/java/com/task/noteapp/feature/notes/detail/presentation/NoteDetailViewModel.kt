package com.task.noteapp.feature.notes.detail.presentation

import android.content.Intent
import android.text.format.DateFormat
import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.noteapp.base.NoteAppViewModel
import com.task.noteapp.db.entity.NoteEntity
import com.task.noteapp.feature.notes.detail.domain.interactor.DeleteNote
import com.task.noteapp.feature.notes.detail.domain.interactor.SaveNote
import com.task.noteapp.feature.notes.detail.domain.interactor.UpdateNote
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val saveNote: SaveNote,
    private val updateNote: UpdateNote,
    private val deleteNote: DeleteNote
) : NoteAppViewModel() {

    companion object {
        private const val ERR_EMPTY_FIELDS = "You must enter a title and description."
    }

    var titleObservable = ObservableField<String>()
    var descriptionObservable = ObservableField<String>()
    var imageUrlObservable = ObservableField<String>()
    var createdDateObservable = ObservableField<String>()

    private var operationCompleteMutableLiveData = MutableLiveData<Unit>()
    val operationCompleteLiveData: LiveData<Unit> get() = operationCompleteMutableLiveData

    var noteEntity: NoteEntity? = null

    fun parseIntent(intent: Intent) {
        getDataFromIntent(intent)?.let { data ->
            data.note?.let { entity ->
                setUI(entity)
                noteEntity = entity
            }
        }
    }

    fun getDataFromIntent(intent: Intent) = getNavigationData(intent) as? NoteDetailActivity.NavigationData

    private fun setUI(note: NoteEntity) {
        titleObservable.set(note.title)
        descriptionObservable.set(note.description)
        imageUrlObservable.set(note.imageUrl)
        createdDateObservable.set(note.createdDate)
    }

    fun deleteNote() {
        noteEntity?.let {
            addDisposable(
                deleteNote.execute(
                    DeleteNote.Params(it)
                ).subscribe(
                    {
                        operationCompleteMutableLiveData.value = Unit
                    },

                    { t ->
                        onError(t.message)
                    }
                )
            )
        }
    }

    private fun insert(
        title: String? = "",
        description: String? = "",
        image: String? = "",
    ) {
        val created = getFormattedDate()
        addDisposable(
            saveNote.execute(
                SaveNote.Params(
                    NoteEntity(
                        title = title,
                        description = description,
                        imageUrl = image,
                        createdDate = created
                    )
                )
            ).subscribe(
                {
                    operationCompleteMutableLiveData.value = Unit
                },

                {
                    onError(it.message)
                }
            )
        )
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun update(note: NoteEntity) {
        addDisposable(
            updateNote.execute(
                UpdateNote.Params(
                    note
                )
            ).subscribe(
                {
                    operationCompleteMutableLiveData.value = Unit
                },

                {
                    onError(it.message)
                }
            )
        )
    }

    private fun checkFields(): Boolean {
        if (titleObservable.get().isNullOrEmpty() ||
                descriptionObservable.get().isNullOrEmpty()) {
            onError(ERR_EMPTY_FIELDS)
            return false
        }
        return true
    }

    fun save() {
        if (!checkFields()) return
        val title = titleObservable.get()
        val description = descriptionObservable.get()
        val image = imageUrlObservable.get()
        noteEntity?.let {
            update(it.apply {
                this.title = title
                this.description = description
                this.imageUrl = image
                this.isEdited = true
            })
        } ?: insert(title, description, image)
    }

    fun hasData() = noteEntity != null

    private fun getFormattedDate() = DateFormat.format("dd/MM/yyyy", Date()).toString()
}