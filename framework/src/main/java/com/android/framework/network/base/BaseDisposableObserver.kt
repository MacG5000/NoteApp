package com.android.framework.network.base

import com.android.framework.base.BaseViewModel
import io.reactivex.observers.DisposableSingleObserver

/**
 * Base observer for abstraction.
 */
abstract class BaseDisposableObserver<Response: BaseResponse>(val viewModel: BaseViewModel) :
    DisposableSingleObserver<Response>() {

    override fun onSuccess(response: Response) {
        handleSuccess(response)
    }

    override fun onError(e: Throwable) {
        onErr(e)
    }

    /**
     * Handle success event, check the response for additional controls for app's needs.
     */
    private fun handleSuccess(response: Response) {
        onResponseSuccess(response)
    }

    abstract fun onResponseSuccess(response: Response)

    abstract fun onErr(e: Throwable)
}