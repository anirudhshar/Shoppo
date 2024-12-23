package com.repository

import com.model.AddressDomainModel
import com.model.OrdersListModel
import com.network.NetWorkService
import com.network.ResultWrapper

class   OrderRepositoryImpl(private val netWorkService: NetWorkService) : OrderRepository {
    override suspend fun placeOrder(addressDomainModel: AddressDomainModel,userId: Long): ResultWrapper<Long> {
        return netWorkService.placeOrder(addressDomainModel,userId)
    }

    override suspend fun getOrderList(userId: Long): ResultWrapper<OrdersListModel> {
        return netWorkService.getOrderList(userId);

    }

}