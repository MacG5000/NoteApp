package com.task.noteapp.base

import android.content.Intent
import com.android.framework.base.BaseViewModel
import com.android.framework.network.base.BaseResponse
import com.task.noteapp.base.NoteAppActivity.Companion.EXTRAS
import com.task.noteapp.base.navigation.NavigationData
import com.task.noteapp.feature.notes.list.presentation.NoteListActivity

open class NoteAppViewModel: BaseViewModel() {

    fun getNavigationData(intent: Intent): NavigationData? {
        return if(intent.hasExtra(EXTRAS)) {
             intent.getParcelableExtra<NavigationData>(EXTRAS)
        } else {
            null
        }
    }

    fun onError(message: String?) {
        getErrorLiveData().value = message
    }

}