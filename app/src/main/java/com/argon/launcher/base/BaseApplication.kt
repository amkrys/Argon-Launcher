package com.argon.launcher.base

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

}