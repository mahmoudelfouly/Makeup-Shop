package com.melfouly.makeupshop.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.melfouly.makeupshop.model.MakeupItem

@Entity(tableName = "makeup_table")
data class MakeupItemEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "image_url") val image: String?,
    @ColumnInfo(name = "price") val price: String?,
    @ColumnInfo(name = "price_sign") val priceSign: String?,
    @ColumnInfo(name = "brand") val brand: String?,
    @ColumnInfo(name = "category") val category: String?,
    @ColumnInfo(name = "texture") val texture: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "product_link") val productLink: String?
)

fun MutableList<MakeupItemEntity>.asDomainModel(): MutableList<MakeupItem> {
    return map {
        MakeupItem(
            id = it.id,
            name = it.name,
            image = it.image,
            price = it.price,
            priceSign = it.priceSign,
            brand = it.brand,
            category = it.category,
            texture = it.texture,
            description = it.description,
            productLink = it.productLink
        )
    }.toMutableList()
}

fun List<MakeupItem>.asDatabaseModel(): MutableList<MakeupItemEntity> {
    return map {
        MakeupItemEntity(
            id = it.id,
            name = it.name,
            image = it.image,
            price = it.price,
            priceSign = it.priceSign,
            brand = it.brand,
            category = it.category,
            texture = it.texture,
            description = it.description,
            productLink = it.productLink
        )
    }.toMutableList()
}