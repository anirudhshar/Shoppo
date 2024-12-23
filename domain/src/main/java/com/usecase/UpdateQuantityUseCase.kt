package com.usecase

import com.model.CartItemModel
import com.model.CartModel
import com.network.ResultWrapper
import com.repository.CartRepository

class UpdateQuantityUseCase(private val cartRepository: CartRepository){

    suspend fun execute(cartItemModel: CartItemModel,userId: Long) = cartRepository.updateQuantity(cartItemModel,userId)

}