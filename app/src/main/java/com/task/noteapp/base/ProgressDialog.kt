package com.task.noteapp.base

import android.content.Context
import androidx.appcompat.app.AppCompatDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.task.noteapp.R

class ProgressDialog(
    context: Context,
    private val lifeCycle: Lifecycle
) : LifecycleObserver {

    companion object {
        const val LAYOUT_RES_ID = R.layout.dialog_progress
    }

    private var progressDialog: AppCompatDialog? = null

    private var shouldShowAfterResume = false

    init {
        progressDialog = AppCompatDialog(context, R.style.ProgressDialogStyle).apply {
            setContentView(LAYOUT_RES_ID)
            setCancelable(false)
        }
    }

    fun show() {
        if(lifeCycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            progressDialog?.takeUnless { it.isShowing }?.show()
        } else {
            shouldShowAfterResume = true
        }
    }

    fun hide() = progressDialog?.hide()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        shouldShowAfterResume = progressDialog?.isShowing == true
        if(shouldShowAfterResume) {
            hide()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        shouldShowAfterResume = false
        progressDialog?.dismiss()
    }
}