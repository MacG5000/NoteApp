package com.android.framework.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class ApplicationLifeCycleObserver : LifecycleObserver {
    private var callback: ApplicationLifeCycleObserverCallback? = null

    /**
     *
     * @return true if is app on foreground false otherwise
     */
    var isAppOnForeground = false
        private set

    /**
     * Lifecycle callback
     * @param callback callback
     */
    fun setCallback(callback: ApplicationLifeCycleObserverCallback?) {
        this.callback = callback
    }

    /**
     * when app onForeground
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected fun onForeground() {
        if (callback != null) {
            callback!!.onApplicationForeground()
        }
        isAppOnForeground = true
    }

    /**
     * when app onForeground - after 700 MS delay
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected fun onBackground() {
        if (callback != null) {
            callback!!.onApplicationBackground()
        }
        isAppOnForeground = false
    }

    /**
     * ApplicationObserverCallback
     */
    interface ApplicationLifeCycleObserverCallback {
        /**
         * onApplicationForeground
         */
        fun onApplicationForeground()

        /**
         * onApplicationBackground
         */
        fun onApplicationBackground()
    }
}