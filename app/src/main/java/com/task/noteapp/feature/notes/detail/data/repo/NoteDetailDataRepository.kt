package com.task.noteapp.feature.notes.detail.data.repo

import com.task.noteapp.db.entity.NoteEntity
import com.task.noteapp.feature.notes.detail.data.repo.datasource.NoteDetailLocalDataSource
import com.task.noteapp.feature.notes.detail.domain.NoteDetailRepository
import io.reactivex.Single
import javax.inject.Inject

class NoteDetailDataRepository @Inject constructor(private val localDataSource: NoteDetailLocalDataSource) : NoteDetailRepository {

    override fun saveNote(note: NoteEntity): Single<Long> = localDataSource.saveNote(note)

    override fun updateNote(note: NoteEntity) : Single<Unit> = localDataSource.updateNote(note)

    override fun deleteNote(note: NoteEntity): Single<Unit> = localDataSource.deleteNote(note)
}