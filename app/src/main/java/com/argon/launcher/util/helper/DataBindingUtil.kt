package com.argon.launcher.util.helper

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.io.File

@BindingAdapter("setIcon")
fun ImageView.setImage(url: String?) {
    url?.let {
        Glide.with(context)
            .load(File(it))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }
}