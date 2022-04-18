package com.task.noteapp.base.navigation

import android.content.Context
import android.content.Intent

abstract class Navigator {
    abstract fun navigate(context: Context?, intent: Intent)

    abstract fun navigateForResult(context: Context?, intent: Intent, requestCode: Int)
}