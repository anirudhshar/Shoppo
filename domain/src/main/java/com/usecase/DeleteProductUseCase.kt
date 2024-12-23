package com.usecase

import com.model.CartItemModel
import com.repository.CartRepository

class DeleteProductUseCase (private val cartRepository: CartRepository){

    suspend fun execute(cartItemId: Int, userId: Long) = cartRepository.deleteItem(cartItemId, userId)

}