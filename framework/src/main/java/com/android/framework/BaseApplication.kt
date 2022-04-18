package com.android.framework

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.android.framework.base.ApplicationLifeCycleObserver

abstract class BaseApplication : Application(), ConfigProvider {

    //private var frameworkComponent: FrameworkComponent? = null

    var lifeCycleObserver: ApplicationLifeCycleObserver? = null
        private set

    override fun onCreate() {
        super.onCreate()

        lifeCycleObserver = ApplicationLifeCycleObserver()
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifeCycleObserver!!)
        //frameworkComponent = DaggerFrameworkComponent.builder().application(this).build()
    }
}