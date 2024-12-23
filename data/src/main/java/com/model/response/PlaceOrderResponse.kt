package com.model.response

import kotlinx.serialization.Serializable

@Serializable
data class PlaceOrderResponse(
    val `data`: OrderD,
    val msg: String
)
