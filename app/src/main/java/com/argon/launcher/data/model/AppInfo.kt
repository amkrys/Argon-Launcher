package com.argon.launcher.data.model

import android.graphics.drawable.Drawable

data class AppInfo(
    val icon: Drawable,
    val packageName: String,
    val label: String
)