package com.task.noteapp.feature.notes.list.data.repo.datasource

import com.task.noteapp.db.NoteDatabase
import com.task.noteapp.db.entity.NoteEntity
import io.reactivex.Single
import javax.inject.Inject

class NoteListLocalDataSource @Inject constructor(private val database: NoteDatabase) {

    fun getNotes(): Single<List<NoteEntity>> = database.noteDao().getNotes()
}