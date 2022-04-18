package com.android.framework.network

import com.android.framework.network.base.BaseRequest
import com.android.framework.network.base.BaseResponse
import com.android.framework.network.di.BaseRetrofitService
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class NetworkProxyTaskProcessor @Inject constructor(
    private val retrofitService: BaseRetrofitService,
    private val gson: Gson
) : NetworkProxy {

    override fun <Response : BaseResponse?, Request : BaseRequest> sendRequest(
        request: Request,
        task: ApiTask<Response>?
    ): Single<Response>? {
        return task?.execute()
    }

    override fun <Response : BaseResponse?> sendRequest(task: ApiTask<Response>?): Single<Response>? {
        return task?.execute()
    }

    override fun <Request : BaseRequest?> fireAndForget(
        request: Request,
        task: CompletableApiTask<Request>?
    ): Completable? {
        return task?.execute(request)
    }
}