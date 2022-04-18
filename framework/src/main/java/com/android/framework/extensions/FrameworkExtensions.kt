package com.android.framework.extensions

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

const val CLICKABLE_DELAY = 300L

fun View?.temporarilyDisableClickable(disableFor: Long = CLICKABLE_DELAY) {
    this?.apply {
        isClickable = false
        io.reactivex.rxjava3.core.Observable.timer(disableFor, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isClickable = true }
    }
}

fun View?.setSafeOnClickListener(
    nextClickDelay: Long = CLICKABLE_DELAY,
    onSafeClick: (View) -> Unit
) {
    this?.apply {
        setOnClickListener(
            SafeClickHandler(nextClickDelay) {
                onSafeClick(it)
            }
        )
    }
}

private class SafeClickHandler(
    private var nextClickDelay: Long,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    override fun onClick(v: View) {
        v.temporarilyDisableClickable(nextClickDelay)
        onSafeCLick(v)
    }
}

fun Int.pxToDp(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    )
        .toInt()
}

inline fun <T> LifecycleOwner?.observeLiveData(liveData: LiveData<T>?, compositeDisposable: CompositeDisposable? = null, crossinline callback: (T) -> Unit) =
    this?.run {
        Observer<T> {callback(it)}.also { liveData?.observe(this, it) }?.let { observer ->
            Disposables.fromAction { liveData?.removeObserver(observer) }.also {disposable ->
                compositeDisposable?.add(disposable)
            }
        }
    }