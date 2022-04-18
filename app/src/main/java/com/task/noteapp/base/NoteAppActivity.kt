package com.task.noteapp.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.android.framework.base.BaseActivity
import com.android.framework.extensions.observeLiveData
import com.task.noteapp.R
import com.task.noteapp.base.navigation.AppNavigator
import com.task.noteapp.base.navigation.NavigationData
import com.task.noteapp.feature.notes.list.presentation.NoteListActivity
import javax.inject.Inject

abstract class NoteAppActivity<VM: NoteAppViewModel, DB: ViewDataBinding>: BaseActivity<VM, DB>() {

    companion object {
        private const val ANIM_IN_START = R.anim.slide_in_right
        private const val ANIM_OUT_START = R.anim.slide_out_left
        private const val ANIM_IN_FINISH = R.anim.slide_in_left
        private const val ANIM_OUT_FINISH = R.anim.slide_out_right

        const val EXTRAS = "intent_extras"
        fun newIntent(
            clazz: Class<*>,
            context: Context?,
            data: NavigationData? = null
        ) = Intent(context, clazz).apply {
            data?.let { putExtra(EXTRAS, data) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLiveData(getViewModel().getErrorLiveData()) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    @Inject
    lateinit var appNavigator: AppNavigator

    private val progressDialog by lazy {
        ProgressDialog(this, lifecycle)
    }

    fun navigate(intent: Intent) {
        appNavigator.navigate(this, intent)
    }

    fun navigateForResult(intent: Intent, requestCode: Int) {
        appNavigator.navigateForResult(this, intent, requestCode)
    }

    override fun showLoading(requestTag: String?) {
        progressDialog.show()
    }

    override fun hideLoading(requestTag: String?) {
        progressDialog.hide()
    }

    override fun provideStartAnimations() = Pair(ANIM_IN_START, ANIM_OUT_START)

    override fun provideFinishAnimations() = Pair(ANIM_IN_FINISH, ANIM_OUT_FINISH)
}