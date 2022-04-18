package com.android.framework.base

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class DelayedTaskExecutor {

    companion object {
        private const val DEFAULT_DELAY_TIME = 500L
    }

    fun execute(params: Params): Disposable? {
        return Observable.timer(params.delayTime, TimeUnit.MILLISECONDS)
            .observeOn(params.observerThread ?: AndroidSchedulers.mainThread())
            .subscribeOn(params.subscriberThread ?: AndroidSchedulers.mainThread())
            .subscribe(
                { long: Long? ->
                    params.task.execute()
                }
            ) { throwable: Throwable? -> }
    }

    class Params(
        val task: Task,
        val delayTime: Long = DEFAULT_DELAY_TIME,
        val observerThread: Scheduler? = null,
        val subscriberThread: Scheduler? = null
    ) {

    }
}