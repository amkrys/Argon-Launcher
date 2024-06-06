package com.argon.launcher.data.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val USER_DATA = stringPreferencesKey("user_data")
    val TOKEN_KEY = stringPreferencesKey("jwt_token")
}