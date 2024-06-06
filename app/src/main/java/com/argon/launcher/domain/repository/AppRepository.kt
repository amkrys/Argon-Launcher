package com.argon.launcher.domain.repository

import androidx.lifecycle.LiveData
import com.argon.launcher.data.entitiy.App


interface AppRepository {

    suspend fun getApps(includeHidden: Boolean = false): LiveData<MutableList<App>>

    suspend fun getOnlyHiddenApps(): LiveData<MutableList<App>>

    suspend fun isAppsInDB(): Int

    suspend fun deleteAllApps()

    suspend fun deleteApp(packageName: String)

    suspend fun insert(app: App)

    suspend fun hideApps(ids: IntArray)

    suspend fun unhiddenApps(ids: IntArray)
}