package com.repository

import com.model.AddressDomainModel
import com.model.OrdersListModel
import com.network.ResultWrapper

interface OrderRepository {
    suspend fun placeOrder(addressDomainModel: AddressDomainModel,userId: Long): ResultWrapper<Long>
    suspend fun getOrderList(userId: Long): ResultWrapper<OrdersListModel>
}