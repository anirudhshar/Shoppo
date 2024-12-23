package com.repository

import com.model.CategoryListModel
import com.network.NetWorkService
import com.network.ResultWrapper

class CategoryRepositoryImpl(val networkService: NetWorkService): CategoryRepository {
    override suspend fun getCategories(): ResultWrapper<CategoryListModel> {
        return networkService.getCategories()


    }
}