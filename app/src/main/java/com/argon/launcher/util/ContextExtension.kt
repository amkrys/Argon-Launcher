package com.argon.launcher.util

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import java.io.File


val Context.dataStorePreferences by preferencesDataStore(
    name = "datastore_prefs"
)

fun Context.getIconFolderPath(label: String): String {
    return "${applicationContext.getExternalFilesDir(Constants.ICON_FOLDER_NAME)}${File.separator}${label.removeSpecialChars()}"
}

