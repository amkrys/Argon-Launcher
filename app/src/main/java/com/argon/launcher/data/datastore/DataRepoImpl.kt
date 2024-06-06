package com.argon.launcher.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

import javax.inject.Inject

class DataRepoImpl @Inject constructor(private val prefsDataStore: DataStore<Preferences>) :
    DataStoreRepo {

    override suspend fun <T> putValue(key: Preferences.Key<T>, value: T) {
        prefsDataStore.edit {
            it[key] = value
        }
    }

    override suspend fun <T> getValue(key: Preferences.Key<T>): Flow<T?> {
        return prefsDataStore.data.catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[key]
            }
    }

    override suspend fun <T> removeValue(key: Preferences.Key<T>) {
        prefsDataStore.edit {
            it.remove(key)
        }
    }

    override suspend fun removeAll() {
        prefsDataStore.edit {
            it.clear()
        }
    }
}