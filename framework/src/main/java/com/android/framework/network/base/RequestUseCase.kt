package com.android.framework.network.base

import com.android.framework.base.BaseUseCase
import com.android.framework.base.BaseViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

abstract class RequestUseCase<Response : BaseResponse, Params> : BaseUseCase() {

    companion object {
        private val DEAFULT_REQUEST_TAG = "DEAFULT_REQUEST_TAG"
    }

    abstract fun buildUseCaseObservable(var1: Params?): Single<Response>

    fun execute(observer: BaseDisposableObserver<Response>) {
        execute(observer, null, null)
    }

    fun execute(observer: BaseDisposableObserver<Response>, params: Params?, tag: String?) {
        val viewModel: BaseViewModel? = observer.viewModel
        val progressLiveData = viewModel?.getProgressLiveData()
        buildUseCaseObservable(params)
            ?.subscribeOn(Schedulers.io())
            ?.doOnSubscribe {
                progressLiveData?.postValue(
                    ProgressInformation(
                        ResponseSubscriptionStatus.SUBSCRIBED,
                        tag
                    )
                )
            }
            ?.doAfterTerminate {
                progressLiveData?.postValue(
                    ProgressInformation(
                        ResponseSubscriptionStatus.FINISHED,
                        tag
                    )
                )
            }
            ?.observeOn(AndroidSchedulers.mainThread())?.let {
                add(it.subscribeWith(observer))
            }
    }

    fun executeSilently(observer: BaseDisposableObserver<Response>, params: Params, tag: String) {
        val viewModel: BaseViewModel? = observer.viewModel
        val progressLiveData = viewModel?.getProgressLiveData()
        buildUseCaseObservable(params)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())?.let {
                add(it.subscribeWith(observer))
            }
    }
}