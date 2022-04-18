package com.task.noteapp.feature.notes.detail

import com.task.noteapp.db.NoteDatabase
import com.task.noteapp.feature.notes.detail.data.repo.NoteDetailDataRepository
import com.task.noteapp.feature.notes.detail.data.repo.datasource.NoteDetailLocalDataSource
import com.task.noteapp.feature.notes.detail.domain.NoteDetailRepository
import com.task.noteapp.feature.notes.list.data.repo.datasource.NoteListLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class NoteDetailModule {

    @Provides
    @ViewModelScoped
    fun provideNoteListLocalDataSource(database: NoteDatabase) =
        NoteDetailLocalDataSource(database)

    @Provides
    @ViewModelScoped
    fun provideNoteDetailRepository(localDataSource: NoteDetailLocalDataSource) : NoteDetailRepository =
        NoteDetailDataRepository(localDataSource)
}