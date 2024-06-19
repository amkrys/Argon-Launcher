package com.argon.launcher.presentation.activity.launcher

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.argon.launcher.databinding.ActivityLauncherBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class LauncherActivity : AppCompatActivity() {

    private var _binding: ActivityLauncherBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LauncherViewModel by viewModels()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        enableEdgeToEdge()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkForPermissions()
        initBottomSheet()
        initObservers()
    }

    private fun checkForPermissions() {
        val storagePermission = Manifest.permission.READ_EXTERNAL_STORAGE

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

    private fun initObservers() = with(viewModel) {
        wallpaperLiveData.observe(this@LauncherActivity) {
            binding.main.background = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}