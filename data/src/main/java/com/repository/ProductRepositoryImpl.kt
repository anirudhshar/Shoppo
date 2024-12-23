package com.repository

import com.model.Product
import com.model.ProductListModel
import com.model.response.ProductListResponse
import com.network.NetWorkService
import com.network.ResultWrapper

class ProductRepositoryImpl(private val netWorkService: NetWorkService): ProductRepository {
    override suspend fun getProducts(category:Int?): ResultWrapper<ProductListModel> {
       return netWorkService.getProducts(category)
    }

}