package com.task.noteapp.feature.notes.detail.domain.interactor

import com.android.framework.base.TaskUseCase
import com.task.noteapp.db.entity.NoteEntity
import com.task.noteapp.feature.notes.detail.data.repo.datasource.NoteDetailLocalDataSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UpdateNote @Inject constructor(private val localDataSource: NoteDetailLocalDataSource):
    TaskUseCase<Single<Unit>, UpdateNote.Params>()
{

    data class Params(val note: NoteEntity)

    override fun execute(params: Params): Single<Unit> = localDataSource.updateNote(params.note)
        .subscribeOn(Schedulers.single())
        .observeOn(AndroidSchedulers.mainThread())
}