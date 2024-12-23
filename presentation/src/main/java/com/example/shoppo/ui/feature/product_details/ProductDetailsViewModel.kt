package com.example.shoppo.ui.feature.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppo.ShopperSession
import com.model.Request.AddCartRequestModel
import com.model.UiProductModel
import com.usecase.AddToCartUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ProductDetailsViewModel(val useCase: AddToCartUseCase) : ViewModel() {

    private val _state = MutableStateFlow<ProductDetailsEvent>(ProductDetailsEvent.Nothing)
    val state = _state.asStateFlow()

    val userDomainModel = ShopperSession.getUser()

    fun addProductToCart(product: UiProductModel) {
        viewModelScope.launch {
            _state.value = ProductDetailsEvent.Loading

            val result = useCase.execute(
                AddCartRequestModel(
                    productId = product.id,
                    productName = product.title,
                    price = product.price,
                    quantity = 1,
                    userId = userDomainModel!!.id!!
                ),
                userDomainModel!!.id!!.toLong()
            )

            when (result) {
                is com.network.ResultWrapper.Success -> {
                    _state.value = ProductDetailsEvent.Success(result.value.msg)
                }
                is com.network.ResultWrapper.Failure -> {
                    _state.value = ProductDetailsEvent.Error(result.exception.message.toString())
                }
            }


        }

    }


}

sealed class ProductDetailsEvent() {
    data object Loading : ProductDetailsEvent()
    data object Nothing : ProductDetailsEvent()
    data class Success(val message: String) : ProductDetailsEvent()
    data class Error(val message: String) : ProductDetailsEvent()

}
