package com.usecase

import com.model.AddressDomainModel
import com.repository.OrderRepository

class PlaceOrderUseCase(private val orderRepository: OrderRepository) {

    suspend fun execute(addressDomainModel: AddressDomainModel,userId: Long) = orderRepository.placeOrder(addressDomainModel,userId)
}