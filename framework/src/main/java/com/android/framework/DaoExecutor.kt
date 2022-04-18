package com.android.framework

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class DaoExecutor <T> : CompletableObserver {

    companion object {
        private const val TAG = "DaoExecutor"
    }

    inline fun async(crossinline task: (T) -> Unit) {
        Completable
            .fromAction { task.invoke(this as T) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this)
    }

    inline fun async(crossinline task: (T) -> Unit, observer: CompletableObserver) {
        Completable
            .fromAction { task.invoke(this as T) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    fun toSingle(): Single<T> {
        return Single.just(this as T)
    }

    override fun onSubscribe(disposable: Disposable) {
        Log.d(TAG, javaClass.simpleName + " subscribed")
    }

    override fun onComplete() {
        Log.d(TAG, javaClass.simpleName + " executed successfully")
    }

    override fun onError(throwable: Throwable) {
        Log.w(TAG, "Received error when " + javaClass.simpleName + " executed")
    }
}
