package com.melfouly.makeupshop.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.melfouly.makeupshop.model.MakeupItem

@Entity(tableName = "cart_table")
data class CartEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "image_url") val image: String?,
    @ColumnInfo(name = "price") val price: String?,
    @ColumnInfo(name = "price_sign") val priceSign: String?,
    @ColumnInfo(name = "product_link") val productLink: String?
)

fun MakeupItem.asDatabaseCartModel(): CartEntity {
    return CartEntity(
        id = this.id,
        name = this.name,
        image = this.image,
        price = this.price,
        priceSign = this.priceSign,
        productLink = this.productLink
    )
}

fun List<CartEntity>.asDomainCartModel(): List<MakeupItem> {
    return map {
        MakeupItem(
            id = it.id,
            name = it.name,
            image = it.image,
            price = it.price,
            priceSign = it.priceSign,
            category = null,
            texture = null,
            productLink = it.productLink,
            brand = null,
            description = null
        )
    }.toList()
}
