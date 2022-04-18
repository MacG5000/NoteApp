package com.task.noteapp.base.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import javax.inject.Inject

class AppNavigator @Inject constructor(): Navigator() {
    companion object {
        const val EXTRA_ROUTER_DATA = "router_data"
    }

    override fun navigate(context: Context?, intent: Intent) {
        context?.startActivity(intent)
    }

    override fun navigateForResult(context: Context?, intent: Intent, requestCode: Int) {
        (context as? Activity)?.startActivityForResult(intent, requestCode)
    }
}