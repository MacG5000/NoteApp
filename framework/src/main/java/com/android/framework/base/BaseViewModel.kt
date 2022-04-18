package com.android.framework.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.framework.network.base.BaseResponse
import com.android.framework.network.base.ProgressInformation
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    private val progressLiveData = MutableLiveData<ProgressInformation>()
    private val errorLiveData = MutableLiveData<String>()
    private val compositeDisposable = CompositeDisposable()

    private val delayedTaskExecutor by lazy {
        DelayedTaskExecutor()
    }

    fun getProgressLiveData() = progressLiveData

    fun getErrorLiveData() = errorLiveData

    open fun runDelayedTask(params: DelayedTaskExecutor.Params) {
        val disposable: Disposable? = delayedTaskExecutor.execute(params)
        disposable?.let { compositeDisposable.add(it) }
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}