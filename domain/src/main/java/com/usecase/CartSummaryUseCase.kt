package com.usecase

import com.repository.CartRepository

class CartSummaryUseCase(private val repository: CartRepository) {

    suspend fun execute(userId: Long) = repository.getCartSummary(userId)

}