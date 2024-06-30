package com.argon.launcher.util.extension

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.argon.launcher.BuildConfig

fun View.log(message: String?) {
    if (BuildConfig.DEBUG) message?.let { Log.e(this::class.java.simpleName, it) }
}

fun ViewGroup.log(message: String?) {
    if (BuildConfig.DEBUG) message?.let { Log.e(this::class.java.simpleName, it) }
}

inline fun <reified T : RecyclerView.ViewHolder> RecyclerView.forEachVisibleHolder(
    action: (T) -> Unit
) {
    for (i in 0 until childCount) {
        action(getChildViewHolder(getChildAt(i)) as T)
    }
}
