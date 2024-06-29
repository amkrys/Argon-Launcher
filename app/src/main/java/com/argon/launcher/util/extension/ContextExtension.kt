package com.argon.launcher.util.extension

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.datastore.preferences.preferencesDataStore
import com.argon.launcher.BuildConfig
import com.argon.launcher.util.constant.Constants
import java.io.File


val Context.dataStorePreferences by preferencesDataStore(
    name = "datastore_prefs"
)

fun Context.getIconFolderPath(label: String): String {
    return "${applicationContext.getExternalFilesDir(Constants.ICON_FOLDER_NAME)}${File.separator}${label.removeSpecialChars()}"
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.log(message: String?) {
    if (BuildConfig.DEBUG) message?.let { Log.e(this::class.java.simpleName, it) }
}

fun Context.openStatusBar() {
    val service = getSystemService("statusbar")
    val statusBarManager = Class.forName("android.app.StatusBarManager")
    statusBarManager.getMethod("expandNotificationsPanel").invoke(service)
}