package com.model.Request

data class AddCartRequestModel(
    val productId: Int,
    val productName: String,
    val price: Double,
    val quantity: Int,
    val userId: Int  //link cart item to the user


)