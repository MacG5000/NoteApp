package com.android.framework.base

public abstract class FrameworkConfig {
    private val SESSION_TIMEOUT_SECONDS = 20 * 1000
    private val DEFAULT_HTTP_CLIENT_TIMEOUT = 120

    open fun sessionTimeOutInSeconds() = SESSION_TIMEOUT_SECONDS

    open fun readTimeOutInSeconds() = DEFAULT_HTTP_CLIENT_TIMEOUT

    open fun writeTimeOutInSeconds() =  DEFAULT_HTTP_CLIENT_TIMEOUT

    open fun connectTimeOutInSeconds() = DEFAULT_HTTP_CLIENT_TIMEOUT

    abstract fun appName(): String?

    abstract fun provideApiUrl(): String

    abstract fun provideDBName(): String

    open fun enableNetworkLogging(): Boolean {
        return false
    }

}