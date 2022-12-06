package com.melfouly.makeupshop.model

import com.google.gson.annotations.SerializedName
import com.melfouly.makeupshop.data.database.MakeupItemEntity
import java.io.Serializable

data class MakeupItem(
    val id: Long,
    val name: String?,
    @SerializedName("image_link")
    val image: String?,
    val price: String?,
    @SerializedName("price_sign")
    val priceSign: String?,
    val brand: String?,
    @SerializedName("product_type")
    val category: String?,
    @SerializedName("category")
    val texture: String?,
    val description: String?,
    @SerializedName("product_link")
    val productLink: String?
) : Serializable

fun MakeupItemEntity.asDomainModel(): MakeupItem {
    return MakeupItem(
        id, name, image, price, priceSign, brand, category, texture, description, productLink
    )
}

