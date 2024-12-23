package com.example.shoppo.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.model.Product
import com.network.ResultWrapper
import com.usecase.GetCategoryUseCase
import com.usecase.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductUseCase: GetProductUseCase,
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUIEvents>(HomeScreenUIEvents.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            _uiState.value = HomeScreenUIEvents.Loading

            val featured = getProducts(1)
            val popularProducts = getProducts(2)
            val categories = getCategory()

            if (featured.isEmpty() || popularProducts.isEmpty() && categories.isNotEmpty()) {
                _uiState.value = HomeScreenUIEvents.Error("Failed to fetch products")
                return@launch
            }

            _uiState.value = HomeScreenUIEvents.Success(featured, popularProducts, categories)

        }
    }


    private suspend fun getProducts(category: Int?): List<Product> {

        getProductUseCase.execute(category).let { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    return (result).value.products
                }

                is ResultWrapper.Failure -> {
                    return emptyList()

                }
            }

        }


    }

    private suspend fun getCategory(): List<String> {
        getCategoryUseCase.execute().let { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    return (result).value.categories.map {
                        it.title
                    }
                }

                is ResultWrapper.Failure -> {
                    return emptyList()
                }
            }
        }

    }
}


sealed class HomeScreenUIEvents() {
    data object Loading : HomeScreenUIEvents()

    data class Success(val featured: List<Product>, val popularProducts: List<Product>, val categories: List<String>) :
        HomeScreenUIEvents()

    data class Error(val message: String) : HomeScreenUIEvents()

}