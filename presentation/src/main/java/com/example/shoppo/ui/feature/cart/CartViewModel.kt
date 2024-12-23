package com.example.shoppo.ui.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppo.ShopperSession
import com.model.CartItemModel
import com.model.CartModel
import com.network.ResultWrapper
import com.usecase.DeleteProductUseCase
import com.usecase.GetCartUseCase
import com.usecase.UpdateQuantityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val useCase: GetCartUseCase, private val updateQuantityUseCase: UpdateQuantityUseCase, private val deleteItem: DeleteProductUseCase) :ViewModel() {

    private val _uiState = MutableStateFlow<CartEvent>(CartEvent.Loading)
    val uiState = _uiState.asStateFlow()

    val userDomainModel = ShopperSession.getUser()

    init {
        getCart()
    }

    fun getCart() {
        viewModelScope.launch {
            _uiState.value = CartEvent.Loading

            useCase.execute(userDomainModel!!.id!!.toLong()).let { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        _uiState.value = CartEvent.Success(result.value.data)
                    }

                    is ResultWrapper.Failure -> {
                        _uiState.value = CartEvent.Error("Something went wrong")
                    }
                }

            }
        }
    }

    fun incrementQuantity(cartItem: CartItemModel) {
        if(cartItem.quantity==10) return
        updateQuantity(cartItem.copy(quantity = cartItem.quantity + 1))
    }

    fun decrementQuantity(cartItem: CartItemModel) {
        if(cartItem.quantity==1) return
        updateQuantity(cartItem.copy(quantity = cartItem.quantity - 1))
    }

    private fun updateQuantity(cartItem: CartItemModel) {
        viewModelScope.launch {
            _uiState.value = CartEvent.Loading
            val result = updateQuantityUseCase.execute(cartItem,userDomainModel!!.id!!.toLong())
            when (result) {
                is ResultWrapper.Success -> {
                    _uiState.value = CartEvent.Success(result.value.data)
                }

                is ResultWrapper.Failure -> {
                    _uiState.value = CartEvent.Error("Something went wrong!")
                }
            }
        }
    }

    fun removeItem(cartItem: CartItemModel) {
        viewModelScope.launch {
            _uiState.value = CartEvent.Loading
            val result = deleteItem.execute(cartItem.id, 1)
            when (result) {
                is ResultWrapper.Success -> {
                    _uiState.value = CartEvent.Success(result.value.data)
                }
                is ResultWrapper.Failure -> {
                    _uiState.value = CartEvent.Error("Something went wrong!")
                }
            }
        }
    }

}

sealed class CartEvent(){
    object Loading : CartEvent()
    data class Success(val message: List<CartItemModel>) : CartEvent()
    data class Error(val message: String) : CartEvent()

}