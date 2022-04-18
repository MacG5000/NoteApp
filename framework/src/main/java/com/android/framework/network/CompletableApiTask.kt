package com.android.framework.network

import com.android.framework.network.base.BaseRequest
import io.reactivex.Completable

interface CompletableApiTask<Request : BaseRequest?> {
    /**
     * Executes an api task
     *
     * @param request Request to be send
     * @return Response from api
     */
    fun execute(request: Request): Completable?
}