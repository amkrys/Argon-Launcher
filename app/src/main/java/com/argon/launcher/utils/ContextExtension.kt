package com.argon.launcher.utils

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore


val Context.dataStorePreferences by preferencesDataStore(
    name = "datastore_prefs"
)

