package com.model.response

import kotlinx.serialization.Serializable

@Serializable
data class UserAuthResponse(
    val `data`: UserResponse,
    val msg: String
)