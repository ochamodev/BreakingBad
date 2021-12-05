package org.ochamo.breakingbad.ui

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import com.bumptech.glide.Glide
import org.ochamo.breakingbad.R


@BindingAdapter("imageUrl")
fun loadImage(view: AppCompatImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .into(view)
}


@BindingAdapter("occupationString")
fun generateOccupationString(view: TextView, occupations: ArrayList<String>) {
    val stringBuilder = StringBuilder()

    occupations.forEach {
        if (it == occupations.last()) {
            stringBuilder.append(it)
        } else {
            stringBuilder.append("$it, ")
        }
    }

    view.text = stringBuilder.toString()

}


@BindingAdapter("markAsFavorite")
fun markOrUnMarkAsFavorite(button: AppCompatImageButton, booleanObs: ObservableBoolean) {
    if (booleanObs.get()) {
        button.setImageResource(R.drawable.ic_baseline_favorite_48)
    } else {
        button.setImageResource(R.drawable.ic_baseline_favorite_border_48)
    }
}