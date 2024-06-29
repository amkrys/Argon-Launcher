package com.argon.launcher.presentation.activity.launcher

import android.app.WallpaperManager
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.argon.launcher.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class LauncherViewModel: ViewModel() {

    private val _wallpaperLiveData = MutableLiveData<Drawable>()
    val wallpaperLiveData: LiveData<Drawable> get() = _wallpaperLiveData

    suspend fun updateCurrentWallpaper(context: Context) {
        withContext(Dispatchers.IO) {
            try {
                val wallpaperManager = WallpaperManager.getInstance(context)
                _wallpaperLiveData.postValue(wallpaperManager.drawable)
            } catch (e: Exception) {
                _wallpaperLiveData.postValue(ContextCompat.getDrawable(context, R.drawable.default_wallpaper))
            }
        }
    }
}