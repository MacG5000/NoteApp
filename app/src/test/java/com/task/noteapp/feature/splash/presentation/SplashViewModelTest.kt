package com.task.noteapp.feature.splash.presentation

import androidx.databinding.ObservableField
import com.android.framework.ui.model.ImageResource
import com.android.framework.ui.model.SrcResource
import com.task.noteapp.BaseUnitTest
import com.task.noteapp.R
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.*

class SplashViewModelTest : BaseUnitTest() {

    @Spy
    lateinit var viewModel: SplashViewModel

    @Test
    fun testIcon() {
        val field = viewModel.splashIconObservable.get()
        val image = (field as? SrcResource)?.image
        Assert.assertEquals(image, R.drawable.notepad)
    }
}