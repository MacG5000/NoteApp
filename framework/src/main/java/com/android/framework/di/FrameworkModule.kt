package com.android.framework.di

import android.app.Application
import android.content.Context
import com.android.framework.ConfigProvider
import com.android.framework.base.FrameworkConfig
import com.android.framework.base.util.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@InstallIn(SingletonComponent::class)
@Module
class FrameworkModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideFrameworkConfig(application: Application): FrameworkConfig? {
        return if (application is ConfigProvider) {
            (application as ConfigProvider).initFrameworkConfigs()
        } else null
    }

    @Provides
    @Singleton
    fun provideResourceProvider(application: Application): ResourceProvider {
        return ResourceProvider(application)
    }


}

