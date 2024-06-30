package com.argon.launcher.util.interfaces

import android.view.View

interface OnItemClickListener<T> {
    fun onItemClick(view: View, position: Int, dataModel: T)
}