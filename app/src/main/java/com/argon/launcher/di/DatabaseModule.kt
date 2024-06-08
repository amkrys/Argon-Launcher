package com.argon.launcher.di

import android.app.Application
import androidx.room.Room
import com.argon.launcher.data.database.core.AppDatabase
import com.argon.launcher.data.database.repository.AppRepositoryImpl
import com.argon.launcher.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "app_database")
            .addMigrations().build()
    }

    @Provides
    @Singleton
    fun provideAppRepository(database: AppDatabase): AppRepository {
        return AppRepositoryImpl(database.appsDao())
    }

}