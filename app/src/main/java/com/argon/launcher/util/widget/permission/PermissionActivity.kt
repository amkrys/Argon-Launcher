package com.argon.launcher.util.widget.permission

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.argon.launcher.util.extension.getSerializable

internal const val PERMISSION_REQUEST = 1
internal const val PERMISSION_PARAMS_KEY = "PERMISSION_PARAMS_KEY"

open class PermissionActivity: AppCompatActivity() {

    private lateinit var permissionParams: PermissionParams

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getSerializable<PermissionParams>(PERMISSION_PARAMS_KEY)?.let {
            permissionParams = it
        } ?: finish()
        ActivityCompat.requestPermissions(this, permissionParams.permissions, PERMISSION_REQUEST)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST) {
            permissions.forEachIndexed { index, permission ->
                if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                    permissionParams.granted
                } else {
                    permissionParams.denied
                }.consume(permission)
            }
            finish()
        }
    }

}