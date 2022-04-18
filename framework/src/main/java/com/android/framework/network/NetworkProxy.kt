package com.android.framework.network

import com.android.framework.network.base.BaseRequest
import com.android.framework.network.base.BaseResponse
import io.reactivex.Completable
import io.reactivex.Single

interface NetworkProxy {

    fun <Response : BaseResponse?, Request : BaseRequest> sendRequest(
        request: Request,
        task: ApiTask<Response>?
    ): Single<Response>?

    fun <Response : BaseResponse?> sendRequest(
        task: ApiTask<Response>?
    ): Single<Response>?


    fun <Request : BaseRequest?> fireAndForget(
        request: Request,
        task: CompletableApiTask<Request>?
    ): Completable?
}