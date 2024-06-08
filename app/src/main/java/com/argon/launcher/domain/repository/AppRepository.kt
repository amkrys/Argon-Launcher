package com.argon.launcher.domain.repository

import androidx.lifecycle.LiveData
import com.argon.launcher.data.entitiy.AppEntity


interface AppRepository {

    suspend fun getApps(includeHidden: Boolean = false): LiveData<MutableList<AppEntity>>

    suspend fun getOnlyHiddenApps(): LiveData<MutableList<AppEntity>>

    suspend fun isAppsInDB(): Int

    suspend fun deleteAllApps()

    suspend fun deleteApp(packageName: String)

    suspend fun insert(appEntity: AppEntity)

    suspend fun hideApps(ids: IntArray)

    suspend fun unhiddenApps(ids: IntArray)
}