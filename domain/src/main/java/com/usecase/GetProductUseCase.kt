package com.usecase

import com.repository.ProductRepository

class GetProductUseCase(private val repository: ProductRepository) {

    suspend fun execute(category: Int?) = repository.getProducts(category)
}