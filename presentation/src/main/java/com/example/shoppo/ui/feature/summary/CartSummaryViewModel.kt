package com.example.shoppo.ui.feature.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppo.ShopperSession
import com.model.CartSummary
import com.model.UserAddress
import com.usecase.CartSummaryUseCase
import com.usecase.PlaceOrderUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartSummaryViewModel(
    private val useCase: CartSummaryUseCase,
    private val placeOrderUseCase: PlaceOrderUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CartSummaryEvent>(CartSummaryEvent.Loading)
    val uiState = _uiState.asStateFlow()

    val userDomainModel = ShopperSession.getUser()

    init {
        getCartSummary(userDomainModel!!.id!!.toLong())
    }


    private fun getCartSummary(userId: Long) {
        viewModelScope.launch {
            _uiState.value = CartSummaryEvent.Loading

            val summary = useCase.execute(userId)

            when (summary) {
                is com.network.ResultWrapper.Success -> {
                    _uiState.value = CartSummaryEvent.Success(summary.value)
                }

                is com.network.ResultWrapper.Failure -> {
                    _uiState.value = CartSummaryEvent.Error(summary.exception.message.toString())
                }
            }

        }
    }

    fun placeOrder(userAddress: UserAddress) {
        viewModelScope.launch {
            _uiState.value = CartSummaryEvent.Loading

            val orderId = placeOrderUseCase.execute(userAddress.toAddressDomainModel(),userDomainModel!!.id!!.toLong())

            when (orderId) {
                is com.network.ResultWrapper.Success -> {
                    _uiState.value = CartSummaryEvent.PlaceOrder(orderId.value)
                }

                is com.network.ResultWrapper.Failure -> {
                    _uiState.value = CartSummaryEvent.Error("Something went wrong")
                }
            }

        }
    }
}

sealed class CartSummaryEvent() {
    data object Loading : CartSummaryEvent()
    data class Success(val summary: CartSummary) : CartSummaryEvent()
    data class Error(val error: String) : CartSummaryEvent()
    data class PlaceOrder(val orderId: Long) : CartSummaryEvent()

}