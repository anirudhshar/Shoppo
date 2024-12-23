package com.model.response

import com.model.DataProductModel
import com.model.ProductListModel
import kotlinx.serialization.Serializable

@Serializable
data class ProductListResponse(
    val data: List<DataProductModel>,
    val msg: String,
    ){
    fun toProductList() = ProductListModel(
        products = data.map { it.toProduct() },
        msg = msg
    )
}
