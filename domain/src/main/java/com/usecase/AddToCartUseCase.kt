package com.usecase

import com.model.Request.AddCartRequestModel
import com.repository.CartRepository

class AddToCartUseCase(private val cartRepository: CartRepository) {

    suspend fun execute(requestModel: AddCartRequestModel,userId: Long) = cartRepository.addProductToCart(requestModel,userId)
}