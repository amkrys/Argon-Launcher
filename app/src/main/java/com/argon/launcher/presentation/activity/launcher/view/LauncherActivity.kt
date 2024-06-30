package com.argon.launcher.presentation.activity.launcher.view

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.argon.launcher.BuildConfig
import com.argon.launcher.R
import com.argon.launcher.data.model.AppUiModel
import com.argon.launcher.databinding.ActivityLauncherBinding
import com.argon.launcher.presentation.activity.launcher.adapter.AppsAdapter
import com.argon.launcher.util.extension.log
import com.argon.launcher.util.extension.openStatusBar
import com.argon.launcher.util.widget.alert.alert
import com.argon.launcher.util.widget.edgefactory.BaseEdgeEffectFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    private var _binding: ActivityLauncherBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LauncherViewModel by viewModels()

    private var askedForPermission = false

    private val swipeDistanceThreshold = 100
    private val swipeVelocityThreshold = 100

    private lateinit var gestureDetector: GestureDetector
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    @RequiresApi(Build.VERSION_CODES.R)
    private val manageStoragePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (Environment.isExternalStorageManager()) {
            onPermissionGranted()
        } else {
            log("manageStoragePermission NOT granted")
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        enableEdgeToEdge()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initGestureDetector()
        initBottomSheet()
        initMainObservers()
    }

    override fun onResume() {
        super.onResume()
        hasPermissions(
            granted = {
                onPermissionGranted()
            },
            notGranted = {
                checkForPermissions()
            }
        )
    }

    private fun hasPermissions(granted: (() -> Unit), notGranted: (() -> Unit)) {
        if (checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager())) {
            granted()
        } else {
            notGranted()
        }
    }

    private fun checkForPermissions() = alert {
        if (askedForPermission ) {
            return@alert
        }
        title = getString(R.string.ask_for_permission_title)
        message = getString(R.string.ask_for_permission_message)
        btnPosText = getString(android.R.string.ok)
        btnNegText = getString(android.R.string.cancel)
        btnPositiveAction = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                manageStoragePermissionLauncher.launch(
                    Intent(
                        ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                        Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                    )
                )
            }
        }
    }

    private fun onPermissionGranted() = with(lifecycleScope) {
        launch(Dispatchers.Main) {
            viewModel.updateCurrentWallpaper(this@LauncherActivity)
        }
    }

    private fun initGestureDetector() {
        gestureDetector = GestureDetector(this@LauncherActivity, object: SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                var result = false
                if (e1 != null) {
                    val diffY = e2.y - e1.y
                    if (abs(diffY) > swipeDistanceThreshold && abs(velocityY) > swipeVelocityThreshold) {
                        if (diffY > 0) {
                            log("onSwipeDown")
                            openStatusBar()
                        } else {
                            log("onSwipeUp")
                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                        }
                        result = true
                    }
                }
                return result
            }
        })
    }

    private fun initBottomSheet() = with(binding) {
        bottomSheetBehavior = BottomSheetBehavior.from(includedBottomSheet.clBottomSheet)
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                includedBottomSheet.ivThumbArrow.alpha = 1- slideOffset
            }
        })
    }

    private fun initAppDrawer(list: MutableList<AppUiModel>) = with(binding.includedBottomSheet.recycleView) {
        edgeEffectFactory = BaseEdgeEffectFactory()
        adapter = AppsAdapter(list)
    }

    private fun initMainObservers() = with(viewModel) {
        wallpaperLiveData.observe(this@LauncherActivity) {
            binding.main.background = it
        }
        appListLiveData.observe(this@LauncherActivity) {
            initAppDrawer(it)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (gestureDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}