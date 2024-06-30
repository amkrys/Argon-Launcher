package com.argon.launcher.presentation.activity.launcher.view

import android.app.WallpaperManager
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.argon.launcher.R
import com.argon.launcher.data.entitiy.AppEntity
import com.argon.launcher.data.model.AppUiModel
import com.argon.launcher.domain.mapper.toUiModel
import com.argon.launcher.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _appListLiveData = MediatorLiveData<MutableList<AppUiModel>>()
    val appListLiveData: LiveData<MutableList<AppUiModel>> get() = _appListLiveData

    private val _wallpaperLiveData = MutableLiveData<Drawable>()
    val wallpaperLiveData: LiveData<Drawable> get() = _wallpaperLiveData

    init {
        viewModelScope.launch {
            _appListLiveData.addSource(appRepository.getApps().map {
                it.map { appEntity -> appEntity.toUiModel() }.toMutableList()
            }) {
                _appListLiveData.postValue(it)
            }
        }
    }

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