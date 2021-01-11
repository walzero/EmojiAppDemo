package com.walterrezende.emojiappdemo.extensions

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup

fun Fragment.createGridLayoutManager(
    spanOf: Int = DEFAULT_SPAN,
    spanSizeLookup: SpanSizeLookup? = null
): GridLayoutManager {
    return GridLayoutManager(requireActivity(), spanOf).apply {
        spanSizeLookup?.let { lookup -> this.spanSizeLookup = lookup }
    }
}

private const val DEFAULT_SPAN = 3