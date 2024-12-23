package com.repository

import com.model.CartItemModel
import com.model.CartModel
import com.model.CartSummary
import com.model.Request.AddCartRequestModel
import com.network.ResultWrapper

interface CartRepository {

    suspend fun addProductToCart(
        requestModel: AddCartRequestModel, userId: Long
    ): ResultWrapper<CartModel>

    suspend fun getCart(userId: Long) : ResultWrapper<CartModel>
    suspend fun updateQuantity(cartItemModel: CartItemModel,userId: Long) : ResultWrapper<CartModel>
    suspend fun deleteItem(cartItemId:Int,userId: Long) : ResultWrapper<CartModel>
    suspend fun getCartSummary(userId: Long) : ResultWrapper<CartSummary>

}