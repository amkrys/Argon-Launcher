package com.argon.launcher.util.helper

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.WorkerThread
import com.argon.launcher.domain.model.AppInfo
import com.argon.launcher.util.extension.toLowerCased

object PackageResolverUtil {

    @WorkerThread
    fun getSortedAppList(
        packageManager: PackageManager
    ): MutableList<AppInfo> {

        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        val list = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.queryIntentActivities(
                mainIntent,
                PackageManager.ResolveInfoFlags.of(0L)
            )
        } else {
            packageManager.queryIntentActivities(mainIntent, 0)
        }
        
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