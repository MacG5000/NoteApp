package com.task.noteapp

import android.content.Context
import androidx.room.Room
import com.android.framework.base.FrameworkConfig
import com.task.noteapp.db.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(context: Context, config: FrameworkConfig?): NoteDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            config?.provideDBName() ?: ""
        )
            .fallbackToDestructiveMigration()
            .build()
}