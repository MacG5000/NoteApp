package com.task.noteapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.task.noteapp.db.dao.NoteDao
import com.task.noteapp.db.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = NoteDatabase.DB_VERSION)
abstract class NoteDatabase : RoomDatabase(){
    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "note_database"
    }

    abstract fun noteDao(): NoteDao
}