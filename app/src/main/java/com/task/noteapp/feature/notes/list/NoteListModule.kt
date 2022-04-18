package com.task.noteapp.feature.notes.list

import com.task.noteapp.db.NoteDatabase
import com.task.noteapp.feature.notes.list.data.repo.NoteListDataRepository
import com.task.noteapp.feature.notes.list.data.repo.datasource.NoteListLocalDataSource
import com.task.noteapp.feature.notes.list.domain.NoteListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class NoteListModule {

    @Provides
    @ViewModelScoped
    fun provideNoteListLocalDataSource(database: NoteDatabase) =
        NoteListLocalDataSource(database)

    @Provides
    @ViewModelScoped
    fun provideNoteListRepository(localDataSource: NoteListLocalDataSource): NoteListRepository =
        NoteListDataRepository(localDataSource)
}