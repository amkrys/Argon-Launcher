package com.argon.launcher.util

import java.util.Locale

fun String.toLowerCased(): String = this.lowercase(Locale.getDefault())

fun String.removeSpecialChars(): String {
    return replace(
        "\\W|_".toRegex(),
        ""
    )
}