package com.task.noteapp.feature.notes.detail.domain.interactor

import com.android.framework.base.TaskUseCase
import com.task.noteapp.db.entity.NoteEntity
import com.task.noteapp.feature.notes.detail.data.repo.datasource.NoteDetailLocalDataSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SaveNote @Inject constructor(private val localDataSource: NoteDetailLocalDataSource):
    TaskUseCase<Single<Long>, SaveNote.Params>()
{

    data class Params(val note: NoteEntity)

    override fun execute(params: Params): Single<Long> = localDataSource.saveNote(params.note)
        .subscribeOn(Schedulers.single())
        .observeOn(AndroidSchedulers.mainThread())
}