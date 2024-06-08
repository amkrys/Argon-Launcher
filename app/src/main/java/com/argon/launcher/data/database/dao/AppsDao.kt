package com.argon.launcher.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.argon.launcher.data.entitiy.AppEntity

@Dao
interface AppsDao {

    @Query("SELECT * from apps WHERE isHidden =:isHidden  ORDER BY label COLLATE NOCASE ASC")
    fun getVisibleApps(isHidden: Boolean): LiveData<MutableList<AppEntity>>

    @Query("SELECT * from apps ORDER BY label COLLATE NOCASE ASC")
    fun getAllApps(): LiveData<MutableList<AppEntity>>

    @Query("SELECT * from apps WHERE isHidden =:isHidden ORDER BY label COLLATE NOCASE ASC")
    fun getOnlyHiddenApps(isHidden: Boolean): LiveData<MutableList<AppEntity>>

    @Query("SELECT COUNT(*) from apps")
    fun isAppsInDB(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appEntity: AppEntity)

    @Query("UPDATE apps SET isHidden = :isHidden where id IN(:ids)")
    suspend fun hideApps(ids: IntArray, isHidden: Boolean)

    @Query("UPDATE apps SET isHidden = :isHidden where id IN(:ids)")
    suspend fun unhiddenApps(ids: IntArray, isHidden: Boolean)

    @Query("DELETE FROM apps")
    suspend fun deleteAllApps()

    @Query("DELETE FROM apps where packageName = :packageName")
    suspend fun deleteApp(packageName: String)
}