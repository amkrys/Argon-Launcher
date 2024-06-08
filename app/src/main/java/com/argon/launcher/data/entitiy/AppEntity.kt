package com.argon.launcher.data.entitiy

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "apps")
data class AppEntity(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val packageName: String,
    val label: String,
    var isSelected: Boolean = false,
    @ColumnInfo(name = "isHidden", index = true) var isHidden: Boolean = false

) : Parcelable