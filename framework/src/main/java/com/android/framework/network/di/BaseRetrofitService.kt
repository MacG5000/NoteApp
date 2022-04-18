package com.android.framework.network.di

import com.android.framework.base.FrameworkConfig
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class BaseRetrofitService @Inject constructor(
    private val config: FrameworkConfig?,
    private val okHttpClient: OkHttpClient,
    private val gson: Gson
) {
    var retrofit: Retrofit? = null

    init {
        retrofit = buildRetrofit()
    }

    fun buildRetrofit() = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .baseUrl(config?.provideApiUrl() ?: "")
        .build()

    fun <Service> create(serviceClass: Class<Service>?) = retrofit!!.create(serviceClass)

    fun getHttpClient() = okHttpClient

    fun addInterceptor() {
        //if(config.enableNetworkLogging()) {
            // add custom interceptors here okHttpClient.newBuilder().addInterceptor()
        //}
    }
}