package com.argon.launcher.base

import android.app.Application
import android.content.Context
import com.argon.launcher.util.AppListUtil
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication @Inject constructor(
    private val appListUtil: AppListUtil
) : Application() {

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