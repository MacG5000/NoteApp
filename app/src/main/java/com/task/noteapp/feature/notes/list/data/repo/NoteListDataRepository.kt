package com.task.noteapp.feature.notes.list.data.repo

import com.task.noteapp.feature.notes.list.data.repo.datasource.NoteListLocalDataSource
import com.task.noteapp.feature.notes.list.domain.NoteListRepository
import javax.inject.Inject

class NoteListDataRepository @Inject constructor(private val dataSource: NoteListLocalDataSource)
    :NoteListRepository{

    override fun getNotes() = dataSource.getNotes()

}