package com.model.response

import com.model.CategoryDataModel
import com.model.CategoryListModel
import kotlinx.serialization.Serializable

@Serializable
data class CategoryListResponse(
    val data :List<CategoryDataModel>,
    val msg: String
){
    fun toCategoryList() = CategoryListModel(
        categories = data.map { it.toCategory() },
        msg = msg
    )
}
