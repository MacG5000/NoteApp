package com.android.framework.network.di

import com.android.framework.base.FrameworkConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun provideOkHttp(
        configurations: FrameworkConfig?,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        configurations?.let {
            if (it.enableNetworkLogging()) {
                val interceptor = HttpLoggingInterceptor()
                builder.addNetworkInterceptor(interceptor)
            }
            builder.connectTimeout(it.connectTimeOutInSeconds().toLong(), TimeUnit.SECONDS)
            builder.readTimeout(it.readTimeOutInSeconds().toLong(), TimeUnit.SECONDS)
            builder.writeTimeout(it.writeTimeOutInSeconds().toLong(), TimeUnit.SECONDS)
        }
        return builder.build()
    }
}