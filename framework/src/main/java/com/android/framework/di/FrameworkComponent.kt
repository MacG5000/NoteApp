package com.android.framework.di

import android.app.Application
import android.content.Context
import com.android.framework.base.FrameworkConfig
import com.android.framework.network.NetworkProxy
import com.android.framework.network.di.BaseRetrofitService
import com.android.framework.network.di.NetworkModule
import com.android.framework.network.di.RetrofitModule
import com.google.gson.Gson
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
/*
@Component(modules = arrayOf(FrameworkModule::class, NetworkModule::class, RetrofitModule::class))
@Singleton
interface FrameworkComponent {

    fun retrofitService(): BaseRetrofitService

    fun gson(): Gson

    fun networkProxy(): NetworkProxy

    fun provideApplication(): Application

    @ApplicationContext
    fun appContext(): Context

    fun frameworkConfig(): FrameworkConfig?

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): FrameworkComponent

    }
}*/
