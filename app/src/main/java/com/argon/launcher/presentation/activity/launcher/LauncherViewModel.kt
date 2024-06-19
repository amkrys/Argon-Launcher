package com.argon.launcher.presentation.activity.launcher

import android.app.WallpaperManager
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.argon.launcher.R
import dagger.hilt.android.lifecycle.HiltViewModel


class LauncherViewModel: ViewModel() {

    private val _wallpaperLiveData = MutableLiveData<Drawable>()
    val wallpaperLiveData: LiveData<Drawable> get() = _wallpaperLiveData

    fun updateCurrentWallpaper(context: Context) {
        try {
            val wallpaperManager = WallpaperManager.getInstance(context)
            _wallpaperLiveData.value = wallpaperManager.drawable
        } catch (e: Exception) {
            _wallpaperLiveData.value = ContextCompat.getDrawable(context, R.drawable.default_wallpaper)
        }
    }
}