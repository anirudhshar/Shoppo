package com.model.response

import com.model.CartItemModel
import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val id: Int,
    val productId: Int,
    val price: Double,
    val imageUrl: String?=null,
    val quantity: Int,
    val productName: String,
){
    fun toDomainModel() : CartItemModel {
        return CartItemModel(
            id = id,
            productId = productId,
            price = price,
            imageUrl = imageUrl,
            quantity = quantity,
            productName = productName
        )

    }
}
