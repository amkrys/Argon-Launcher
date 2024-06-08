package com.argon.launcher.util

import android.content.Intent
import android.content.pm.PackageManager
import androidx.annotation.WorkerThread
import com.argon.launcher.data.model.AppInfo

object PackageResolverUtil {

    @WorkerThread
    fun getSortedAppList(
        packageManager: PackageManager
    ): MutableList<AppInfo> {

        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        val list = packageManager.queryIntentActivities(mainIntent, 0)
        val appInoList: MutableList<AppInfo> = ArrayList(list.size)

        list.forEach { resolveInfo ->
            val packageInfo = AppInfo(
                icon = resolveInfo.loadIcon(packageManager),
                packageName = resolveInfo.activityInfo.packageName,
                label = resolveInfo.loadLabel(packageManager).toString()
            )
            appInoList.add(packageInfo)
        }

        appInoList.sortBy {
            it.label.toLowerCased()
        }

        return appInoList
    }

    @WorkerThread
    fun getAppInfoFromPackageName(packageManager: PackageManager, packageName: String): AppInfo? {
        val intent = Intent()
        intent.`package` = packageName
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        var appInfo: AppInfo? = null
        packageManager.resolveActivity(intent, 0)?.let {
            appInfo = AppInfo(
                icon = it.loadIcon(packageManager),
                packageName = it.activityInfo.packageName,
                label = it.loadLabel(packageManager).toString()
            )
        }
        return appInfo
    }
}