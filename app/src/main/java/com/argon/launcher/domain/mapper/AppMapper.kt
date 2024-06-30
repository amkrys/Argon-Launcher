package com.argon.launcher.domain.mapper

import com.argon.launcher.data.entitiy.AppEntity
import com.argon.launcher.data.model.AppUiModel

fun AppEntity.toUiModel() = AppUiModel(
    packageName = packageName,
    label = label,
    icon = icon
)