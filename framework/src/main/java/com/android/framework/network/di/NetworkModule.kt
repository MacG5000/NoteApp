package com.android.framework.network.di

import com.android.framework.base.FrameworkConfig
import com.android.framework.network.NetworkProxy
import com.android.framework.network.NetworkProxyTaskProcessor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideSimpleGson() = GsonBuilder().setLenient().serializeNulls().create()


    @Provides
    @Singleton
    fun provideNetworkProxy(
        gson: Gson,
        retrofitService: BaseRetrofitService
    ): NetworkProxy = NetworkProxyTaskProcessor(retrofitService, gson)


    @Provides
    @Singleton
    fun provideRetrofit(
        config: FrameworkConfig?,
        okHttpClient: OkHttpClient,
        gson: Gson
    ): BaseRetrofitService = BaseRetrofitService(config, okHttpClient, gson)

    @Provides
    @Singleton
    fun provideOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }
}