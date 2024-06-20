package com.argon.launcher.di

import android.content.Context
import com.argon.launcher.domain.repository.AppRepository
import com.argon.launcher.util.helper.AppListUtil
import com.argon.launcher.util.helper.BitmapUtil
import com.argon.launcher.util.helper.StorageUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    @Singleton
    fun storageUtils(@ApplicationContext context: Context): StorageUtil {
        return StorageUtil(context)
    }

    @Provides
    @Singleton
    fun bitmapUtils(): BitmapUtil {
        return BitmapUtil()
    }

    @Provides
    @Singleton
    fun appListUtils(
        appRepository: AppRepository,
        bitmapUtil: BitmapUtil,
        storageUtil: StorageUtil,
        context: Context
    ): AppListUtil {
        return AppListUtil(appRepository, bitmapUtil, storageUtil, context.packageManager, context)
    }

}