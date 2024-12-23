package com.model.response

import com.model.SummaryData
import kotlinx.serialization.Serializable

@Serializable
data class Summary(
    val discount: Double,
    val items: List<CartItem>,
    val shipping: Double,
    val subtotal: Double,
    val tax: Double,
    val total: Double
) {
    fun toSummaryData() = SummaryData(
        discount = discount,
        items = items.map { it.toDomainModel() },
        shipping = shipping,
        subtotal = subtotal,
        tax = tax,
        total = total
    )
}