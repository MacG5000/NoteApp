package com.task.noteapp.feature.splash.presentation

import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.framework.base.DelayedTaskExecutor
import com.android.framework.base.Task
import com.android.framework.ui.model.ImageResource
import com.android.framework.ui.model.SrcResource
import com.task.noteapp.R
import com.task.noteapp.base.NoteAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(): NoteAppViewModel() {

    companion object {
        private const val SPLASH_DURATION = 10L
    }

    var splashIconObservable = ObservableField<ImageResource>()

    private var nextActivityMutableLiveData = MutableLiveData<Unit>()
    val nextActivityLiveData: LiveData<Unit> get() = nextActivityMutableLiveData

    init {
        splashIconObservable.set(SrcResource(R.drawable.notepad))
        startNextActivity()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun startNextActivity() {
        runDelayedTask(
            DelayedTaskExecutor.Params(
                object : Task {
                    override fun execute() {
                        nextActivityMutableLiveData.value = Unit
                    }
                },
                SPLASH_DURATION
            )
        )
    }
}