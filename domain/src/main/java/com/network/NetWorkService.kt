package com.network

import com.model.AddressDomainModel
import com.model.CartItemModel
import com.model.CartModel
import com.model.CartSummary
import com.model.CategoryListModel
import com.model.OrdersListModel
import com.model.Product
import com.model.ProductListModel
import com.model.Request.AddCartRequestModel
import com.model.UserDomainModel

interface NetWorkService {

    suspend fun addProductToCart(
        request: AddCartRequestModel,
        userId: Long
    ): ResultWrapper<CartModel>

    suspend fun getProducts(category: Int?): ResultWrapper<ProductListModel>

    suspend fun getCategories(): ResultWrapper<CategoryListModel>

    suspend fun getCart(userId: Long): ResultWrapper<CartModel>

    suspend fun updateQuantity(cartItemModel: CartItemModel, userId: Long): ResultWrapper<CartModel>

    suspend fun deleteItem(cartItemId: Int, userId: Long): ResultWrapper<CartModel>

    suspend fun getCartSummary(userId: Long): ResultWrapper<CartSummary>

    suspend fun placeOrder(address: AddressDomainModel, userId: Long): ResultWrapper<Long>

    suspend fun getOrderList(userId: Long): ResultWrapper<OrdersListModel>

    suspend fun login(email: String, password: String): ResultWrapper<UserDomainModel>

    suspend fun register(
        email: String,
        password: String,
        name: String
    ): ResultWrapper<UserDomainModel>

}

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Failure(val exception: Exception) : ResultWrapper<Nothing>()

}