package com.argon.launcher.util.widget.permission

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import java.util.UUID

internal val onGrantedListeners: MutableMap<String, (String) -> Unit> = mutableMapOf()
internal val onDeniedListeners: MutableMap<String, (String) -> Unit> = mutableMapOf()

fun Context.askPermissions(vararg permissions: String, onGranted: (String) -> Unit, onDenied: (String) -> Unit) {
    val id = UUID.randomUUID().toString()

    onGrantedListeners[id] = onGranted
    onDeniedListeners[id] = onDenied

    getGranted(*permissions)?.forEach {
        onGranted(it)
    }

    val notGrantedPermissions = getNotGranted(*permissions)

    if (notGrantedPermissions != null) {
        val params = PermissionParams(
            *notGrantedPermissions,
            granted = object : Consumer<String, Unit> {
                override fun consume(t: String) {
                    granted(id, t)
                }
            },
            denied = object : Consumer<String, Unit> {
                override fun consume(t: String) {
                    denied(id, t)
                }
            }
        )
        val intent = Intent(this, PermissionActivity::class.java).also {
            it.putExtra(PERMISSION_PARAMS_KEY, params)
        }
        startActivity(intent)
    }
}

fun Context.havePermission(vararg permissions: String, onGranted: (String) -> Unit, onDenied: (String) -> Unit) {
    val id = UUID.randomUUID().toString()

    onGrantedListeners[id] = onGranted
    onDeniedListeners[id] = onDenied

    getGranted(*permissions)?.forEach {
        onGranted(it)
    }

    getNotGranted(*permissions)?.forEach {
        onDenied(it)
    }
}

internal fun granted(id: String, permission: String) = onGrantedListeners[id]?.invoke(permission)

internal fun denied(id: String, permission: String) = onDeniedListeners[id]?.invoke(permission)

internal fun Context.getGranted(vararg permissions: String): Array<String>? =
    permissions.mapNotNull { permission ->
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED)
            permission
        else
            null
    }.toTypedArray().nullIfEmpty()

internal fun Context.getNotGranted(vararg permissions: String): Array<String>? =
    permissions.mapNotNull { permission ->
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
            permission
        else
            null
    }.toTypedArray().nullIfEmpty()

internal fun Context.getDenied(vararg permissions: String): Array<String>? =
    permissions.mapNotNull { permission ->
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED)
            permission
        else
            null
    }.toTypedArray().nullIfEmpty()

internal fun <T> Array<T>.nullIfEmpty(): Array<T>? = if (isEmpty()) null else this