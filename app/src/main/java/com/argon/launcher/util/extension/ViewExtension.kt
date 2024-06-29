package com.argon.launcher.util.extension

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.argon.launcher.BuildConfig

fun View.log(message: String?) {
    if (BuildConfig.DEBUG) message?.let { Log.e(this::class.java.simpleName, it) }
}

fun ViewGroup.log(message: String?) {
    if (BuildConfig.DEBUG) message?.let { Log.e(this::class.java.simpleName, it) }
}

