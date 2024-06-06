package com.argon.launcher.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.argon.launcher.utils.dataStorePreferences
import com.argon.launcher.data.datastore.DataRepoImpl
import com.argon.launcher.data.datastore.DataStoreRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providePreference(
        @ApplicationContext applicationContext: Context
    ): DataStore<Preferences> {
        return applicationContext.dataStorePreferences
    }

    @Provides
    @Singleton
    fun providePreferenceRepository(@ApplicationContext applicationContext: Context): DataStoreRepo =
        DataRepoImpl(
            providePreference(applicationContext)
        )

}