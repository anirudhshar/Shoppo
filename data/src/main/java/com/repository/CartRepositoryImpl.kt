package com.repository

import com.model.CartItemModel
import com.model.CartModel
import com.model.CartSummary
import com.model.Request.AddCartRequestModel
import com.network.NetWorkService
import com.network.ResultWrapper

class CartRepositoryImpl(private val netWorkService: NetWorkService) : CartRepository {
    override suspend fun addProductToCart(requestModel: AddCartRequestModel,userId: Long): ResultWrapper<CartModel> {
        return netWorkService.addProductToCart(requestModel,userId)
    }

    override suspend fun getCart(userId: Long): ResultWrapper<CartModel> {
        return netWorkService.getCart(userId)
    }

    override suspend fun updateQuantity(cartItemModel: CartItemModel,userId: Long): ResultWrapper<CartModel> {
        return netWorkService.updateQuantity(cartItemModel,userId)
    }

    override suspend fun deleteItem(cartItemId: Int, userId: Long): ResultWrapper<CartModel> {
        return netWorkService.deleteItem(cartItemId,userId)
    }

    override suspend fun getCartSummary(userId: Long): ResultWrapper<CartSummary> {
        return netWorkService.getCartSummary(userId)
    }

}