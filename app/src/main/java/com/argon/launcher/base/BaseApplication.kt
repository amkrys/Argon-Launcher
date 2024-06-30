package com.argon.launcher.base

import android.app.Application
import com.argon.launcher.util.helper.AppListUtil
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {

    @Inject
    lateinit var appListUtil: AppListUtil

    override fun onCreate() {
        super.onCreate()
        initApps()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initApps() {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                appListUtil.saveAppsInDB()
            }
        }
    }

}