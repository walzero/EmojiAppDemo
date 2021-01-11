package com.walterrezende.emojiappdemo.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.walterrezende.emojiappdemo.R

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}