package com.android.framework.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseUseCase {
    private var compositeDisposable = CompositeDisposable()

    fun add(disposable: Disposable) = compositeDisposable.add(disposable)

    fun clear() = compositeDisposable.clear()
}