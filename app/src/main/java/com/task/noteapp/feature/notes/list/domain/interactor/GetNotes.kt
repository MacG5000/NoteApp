package com.task.noteapp.feature.notes.list.domain.interactor

import com.android.framework.base.TaskUseCase
import com.task.noteapp.db.entity.NoteEntity
import com.task.noteapp.feature.notes.list.domain.NoteListRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetNotes @Inject constructor(
    private val noteListRepository: NoteListRepository
    ): TaskUseCase<Single<List<NoteEntity>>, Unit>() {

    override fun execute(params: Unit): Single<List<NoteEntity>> = noteListRepository
        .getNotes()
        .subscribeOn(Schedulers.single())
        .observeOn(AndroidSchedulers.mainThread())
}