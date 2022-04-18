package com.task.noteapp.db.dao

import androidx.room.*
import com.android.framework.DaoExecutor
import com.task.noteapp.db.entity.NoteEntity
import io.reactivex.Single

@Dao
abstract class NoteDao: DaoExecutor<NoteDao>() {

    @Query("SELECT * FROM " + NoteEntity.TABLE_NAME)
    abstract fun getNotes(): Single<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(noteEntity: NoteEntity) : Single<Long>

    @Update
    abstract fun updateNote(noteEntity: NoteEntity): Single<Unit>

    @Delete
    abstract fun deleteNote(noteEntity: NoteEntity): Single<Unit>

    @Query("DELETE FROM " + NoteEntity.TABLE_NAME)
    abstract fun nukeNotes(): Single<Unit>
}