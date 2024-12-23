package com.navigation

import com.model.Product
import com.model.UiProductModel
import com.model.UserAddress
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
object LoginScreen

@Serializable
object RegisterScreen


@Serializable
object OrderScreen

@Serializable
object ProfileScreen

@Serializable
data class ProductDetails(val product: UiProductModel)

@Serializable
object CartSummaryScreen

@Serializable
data class UserAddressRoute(val userAddressWrapper: UserAddressWrapper)