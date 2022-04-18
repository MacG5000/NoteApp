package com.task.noteapp.base

import com.android.framework.base.FrameworkConfig
import com.task.noteapp.db.NoteDatabase

class AppConfig: FrameworkConfig() {

    companion object {
        const val APP_NAME = "Note App"
        const val BASE_API_URL = "www.google.com/" // Dummy for testing
    }

    override fun appName() = APP_NAME

    override fun provideApiUrl() = BASE_API_URL

    override fun provideDBName() = NoteDatabase.DB_NAME

    override fun enableNetworkLogging() = false//BuildConfig.enableNetworkLogging
}