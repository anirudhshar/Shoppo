package com.repository

import com.model.CategoryListModel
import com.network.ResultWrapper

interface CategoryRepository {

    suspend fun getCategories(): ResultWrapper<CategoryListModel>
}