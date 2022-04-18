package com.task.noteapp.feature.notes.detail.data.repo.datasource

import com.task.noteapp.db.NoteDatabase
import com.task.noteapp.db.entity.NoteEntity
import io.reactivex.Single
import javax.inject.Inject

class NoteDetailLocalDataSource @Inject constructor(private val database: NoteDatabase) {

    fun saveNote(note: NoteEntity): Single<Long> = database.noteDao().insert(note)

    fun updateNote(note: NoteEntity): Single<Unit>  = database.noteDao().updateNote(note)

    fun deleteNote(note: NoteEntity): Single<Unit>  = database.noteDao().deleteNote(note)
}