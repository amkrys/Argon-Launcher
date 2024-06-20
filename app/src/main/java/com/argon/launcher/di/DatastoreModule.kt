package com.argon.launcher.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.argon.launcher.util.extension.dataStorePreferences
import com.argon.launcher.data.datastore.repository.DataRepositoryImpl
import com.argon.launcher.domain.repository.DatastoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Provides
    @Singleton
    fun providePreference(
        @ApplicationContext applicationContext: Context
    ): DataStore<Preferences> {
        return applicationContext.dataStorePreferences
    }

    @Provides
    @Singleton
    fun providePreferenceRepository(@ApplicationContext applicationContext: Context): DatastoreRepository =
        DataRepositoryImpl(
            providePreference(applicationContext)
        )

}