package org.ochamo.breakingbad.ui

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("imageUrl")
fun loadImage(view: AppCompatImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .into(view)
}