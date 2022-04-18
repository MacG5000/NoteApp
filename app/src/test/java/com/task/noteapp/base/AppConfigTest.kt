package com.task.noteapp.base

import com.task.noteapp.BaseUnitTest
import com.task.noteapp.db.NoteDatabase
import org.junit.Assert.*

import org.junit.Test

class AppConfigTest: BaseUnitTest() {

    private val config = AppConfig()

    @Test
    fun appName() {
        assertEquals(AppConfig.APP_NAME, config.appName())
    }

    @Test
    fun provideApiUrl() {
        assertEquals(AppConfig.BASE_API_URL, config.provideApiUrl())
    }

    @Test
    fun provideDBName() {
        assertEquals(NoteDatabase.DB_NAME, config.provideDBName())
    }

    @Test
    fun enableNetworkLogging() {
        assertEquals(false, config.enableNetworkLogging())
    }
}