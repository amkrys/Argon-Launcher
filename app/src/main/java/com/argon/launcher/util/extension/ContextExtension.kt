package com.argon.launcher.util.extension

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.preferencesDataStore
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

