package com.argon.launcher.data.database.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.argon.launcher.data.database.dao.AppsDao
import com.argon.launcher.data.entitiy.App

@Database(entities = [App::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appsDao(): AppsDao
}