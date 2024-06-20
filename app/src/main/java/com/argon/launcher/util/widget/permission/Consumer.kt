package com.argon.launcher.util.widget.permission

import java.io.Serializable

interface Consumer<T, U> : Serializable {
    fun consume(t: T): U
}