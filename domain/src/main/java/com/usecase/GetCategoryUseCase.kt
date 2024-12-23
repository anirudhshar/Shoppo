package com.usecase

import com.repository.CategoryRepository

class GetCategoryUseCase(val categoryRepository: CategoryRepository) {

    suspend fun execute() = categoryRepository.getCategories()
}
