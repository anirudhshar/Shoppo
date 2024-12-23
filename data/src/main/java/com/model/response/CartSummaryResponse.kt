package com.model.response

import com.model.CartSummary
import kotlinx.serialization.Serializable

@Serializable
data class CartSummaryResponse(
    val `data`: Summary,
    val msg: String
) {
    fun toCartSummary() = CartSummary(
        data = `data`.toSummaryData(),
        msg = msg
    )
}