package com.argon.launcher.presentation.activity.launcher

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
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
import com.argon.launcher.databinding.ActivityLauncherBinding
import com.argon.launcher.util.extension.log
import com.argon.launcher.util.widget.view.alert
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    private var _binding: ActivityLauncherBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LauncherViewModel by viewModels()

    private var askedForPermission = false

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

    private fun initBottomSheet() = with(binding) {
        bottomSheetBehavior = BottomSheetBehavior.from(includedBottomSheet.clBottomSheet)
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
    }

    private fun initMainObservers() = with(viewModel) {
        wallpaperLiveData.observe(this@LauncherActivity) {
            binding.main.background = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}