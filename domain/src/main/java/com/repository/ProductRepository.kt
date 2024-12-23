package com.repository

import com.model.Product
import com.model.ProductListModel
import com.network.ResultWrapper

interface ProductRepository {
    suspend fun getProducts(category: Int?): ResultWrapper<ProductListModel>
}