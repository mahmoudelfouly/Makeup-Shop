package com.melfouly.makeupshop

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.melfouly.makeupshop.model.MakeupItem

@BindingAdapter("loadImage")
fun bindImageViewToDisplayMakeupImage(imageView: ImageView, makeupItem: MakeupItem?) {
    val context = imageView.context
    if (makeupItem != null) {
        Glide.with(context)
            .load(makeupItem.image?.toUri())
            .error(R.drawable.no_image_found)
            .into(imageView)

        imageView.contentDescription = makeupItem.description
    } else {
        imageView.setImageResource(R.drawable.no_image_found)
        imageView.contentDescription = context.getString(R.string.no_item_to_show)
    }
}

@BindingAdapter("loadCategory")
fun bindTextViewToCategory(textView: TextView, makeupItem: MakeupItem?) {
    val context = textView.context
    if (makeupItem?.category != null) {
        if (makeupItem.category.contains('_')) {
            val editedMakeupItem = makeupItem.category.replace('_', ' ')
            textView.text = editedMakeupItem.replaceFirstChar { it.uppercase() }
            return
        }
        textView.text = makeupItem.category.replaceFirstChar { it.uppercase() }
    } else {
        textView.text = context.getString(R.string.category_failed)
    }
}

@BindingAdapter("loadPrice")
fun bindTextViewToDisplayPriceTag(textView: TextView, makeupItem: MakeupItem?) {
    val context = textView.context
    if (makeupItem?.price != null && makeupItem.price != "0.0" && makeupItem.priceSign != null) {
        textView.text = String.format(
            context.getString(R.string.price_tag),
            makeupItem.price,
            makeupItem.priceSign
        )
    } else {
        textView.text = context.getString(R.string.price_tag_failed)
    }
}

@BindingAdapter("loadBrand")
fun bindTextViewToDisplayBrand(textView: TextView, makeupItem: MakeupItem?) {
    val context = textView.context
    if (makeupItem?.brand != null) {
        textView.text = makeupItem.brand.replaceFirstChar { it.uppercase() }
    } else {
        textView.text = context.getString(R.string.brand_failed)
    }
}

@BindingAdapter("loadTexture")
fun bindTextViewToDisplayTexture(textView: TextView, makeupItem: MakeupItem?) {
    val context = textView.context
    if (makeupItem?.texture != null && makeupItem.texture != "") {
        if (makeupItem.texture.contains('_')) {
            val editedMakeupItem = makeupItem.texture.replace('_', ' ')
            textView.text = editedMakeupItem.replaceFirstChar { it.uppercase() }
            return
        }
        textView.text = makeupItem.texture.replaceFirstChar { it.uppercase() }
    } else {
        textView.text = context.getString(R.string.texture_failed)
    }
}

@BindingAdapter("loadDescription")
fun bindTextViewToDisplayDescription(textView: TextView, makeupItem: MakeupItem?) {
    val context = textView.context
    if (makeupItem?.description != null && makeupItem.description != "") {
        textView.text = makeupItem.description.trimIndent()
    } else {
        textView.text = context.getString(R.string.description_failed)
    }
}