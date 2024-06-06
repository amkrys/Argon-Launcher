package com.argon.launcher.data.database.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.argon.launcher.data.database.dao.AppsDao
import com.argon.launcher.data.entitiy.App
import com.argon.launcher.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val appsDao: AppsDao
): AppRepository {

    override suspend fun getApps(includeHidden: Boolean): LiveData<MutableList<App>> {
        return if (includeHidden) {
            appsDao.getAllApps()
        } else {
            appsDao.getVisibleApps(false)
        }
    }

    override suspend fun getOnlyHiddenApps(): LiveData<MutableList<App>> {
        return appsDao.getOnlyHiddenApps(true)
    }

    override suspend fun isAppsInDB(): Int {
        return appsDao.isAppsInDB()
    }

    @WorkerThread
    override suspend fun deleteAllApps() {
        return appsDao.deleteAllApps()
    }

    @WorkerThread
    override suspend fun deleteApp(packageName: String) {
        return appsDao.deleteApp(packageName)
    }

    @WorkerThread
    override suspend fun insert(app: App) {
        appsDao.insert(app)
    }

    @WorkerThread
    override suspend fun hideApps(ids: IntArray) {
        appsDao.hideApps(ids, true)
    }

    @WorkerThread
    override suspend fun unhiddenApps(ids: IntArray) {
        appsDao.unhiddenApps(ids, false)
    }

}