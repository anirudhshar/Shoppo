package com.usecase

import com.model.OrdersListModel
import com.network.ResultWrapper
import com.repository.OrderRepository

class OrderListUseCase(
    private val repository: OrderRepository
) {
    suspend fun execute(userId: Long) = repository.getOrderList(userId)
}