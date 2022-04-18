package com.task.noteapp

import com.android.framework.BaseApplication
import com.android.framework.base.ApplicationLifeCycleObserver
import com.task.noteapp.base.AppConfig
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteApplication: BaseApplication() ,
    ApplicationLifeCycleObserver.ApplicationLifeCycleObserverCallback {

    private var isAppOnForegroud = false
        get() = field

    override fun onCreate() {
        super.onCreate()
        lifeCycleObserver?.setCallback(this)
    }

    override fun initFrameworkConfigs() = AppConfig()

    override fun onApplicationForeground() {
        isAppOnForegroud = true
    }

    override fun onApplicationBackground() {
        isAppOnForegroud = false
    }
}