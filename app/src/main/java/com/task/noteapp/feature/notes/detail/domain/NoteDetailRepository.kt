package com.task.noteapp.feature.notes.detail.domain

import com.task.noteapp.db.entity.NoteEntity
import io.reactivex.Single

interface NoteDetailRepository {

    fun saveNote(note: NoteEntity): Single<Long>

    fun updateNote(note: NoteEntity) : Single<Unit>

    fun deleteNote(note: NoteEntity) : Single<Unit>
}