package com.task.noteapp.feature.notes.list.domain

import com.task.noteapp.db.entity.NoteEntity
import io.reactivex.Single

interface NoteListRepository {
    fun getNotes(): Single<List<NoteEntity>>
}