package com.model.response

import com.model.OrdersListModel
import kotlinx.serialization.Serializable

@Serializable
data class OrdersListResponse(
    val `data`: List<OrderListData>,
    val msg: String
) {
    fun toDomainResponse(): OrdersListModel {
        return OrdersListModel(
            `data` = `data`.map { it.toDomainResponse() },
            msg = msg
        )
    }
}
