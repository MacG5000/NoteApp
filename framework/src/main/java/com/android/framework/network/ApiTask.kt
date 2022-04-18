package com.android.framework.network

import com.android.framework.network.base.BaseRequest
import com.android.framework.network.base.BaseResponse
import io.reactivex.Single

interface ApiTask<Response : BaseResponse?> { //, Request : BaseRequest?
    /**
     * Executes an api task
     *
     * @param request Request to be send
     * @return Response from api
     */
    //fun execute(request: Request): Single<Response>?

    fun execute(): Single<Response>?
}