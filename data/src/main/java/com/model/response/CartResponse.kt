package com.model.response

import com.model.CartItemModel
import com.model.CartModel
import kotlinx.serialization.Serializable

@Serializable
data class CartResponse(
    val data: List<CartItem>,
    val msg: String

){
    fun toCartModel() : CartModel {
        return CartModel(
            data = data.map { it.toDomainModel() },
            msg = msg
        )
    }
}
