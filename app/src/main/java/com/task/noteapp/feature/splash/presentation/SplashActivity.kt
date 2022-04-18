package com.task.noteapp.feature.splash.presentation

import android.os.Bundle
import androidx.activity.viewModels
import com.android.framework.extensions.observeLiveData
import com.task.noteapp.R
import com.task.noteapp.base.NoteAppActivity
import com.task.noteapp.databinding.ActivitySplashBinding
import com.task.noteapp.feature.notes.list.presentation.NoteListActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity: NoteAppActivity<SplashViewModel, ActivitySplashBinding>(){
    companion object {
        private const val LAYOUT_RES_ID = R.layout.activity_splash
    }

    private val vm: SplashViewModel by viewModels()

    override fun provideViewModel() = vm

    override fun provideLayoutResId() = LAYOUT_RES_ID

    override fun bindViewModel(binding: ActivitySplashBinding) {
        binding.viewModel = getViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLiveData(getViewModel().nextActivityLiveData) {
            startNextActivity()
        }
    }

    private fun startNextActivity() {
        navigate(NoteListActivity.newIntent(this))
        finish()
    }
}