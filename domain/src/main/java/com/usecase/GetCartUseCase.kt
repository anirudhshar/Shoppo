package com.usecase

import com.repository.CartRepository

class GetCartUseCase(private val cartRepository: CartRepository)  {

    suspend fun execute(userId: Long) = cartRepository.getCart(userId)
}