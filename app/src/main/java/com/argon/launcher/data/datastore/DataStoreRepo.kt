package com.argon.launcher.data.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepo {
    suspend fun <T> putValue(key: Preferences.Key<T>, value: T)
    suspend fun <T> getValue(key: Preferences.Key<T>): Flow<T?>
    suspend fun <T> removeValue(key: Preferences.Key<T>)
    suspend fun removeAll()
}