package com.argon.launcher.util.widget.permission

import java.io.Serializable

internal class PermissionParams(
    vararg val permissions: String,
    val granted: Consumer<String, Unit>,
    val denied: Consumer<String, Unit>
) : Serializable