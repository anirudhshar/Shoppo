package com.di

import com.repository.CartRepository
import com.repository.CartRepositoryImpl
import com.repository.CategoryRepository
import com.repository.CategoryRepositoryImpl
import com.repository.OrderRepository
import com.repository.OrderRepositoryImpl
import com.repository.ProductRepository
import com.repository.ProductRepositoryImpl
import com.repository.UserRepoImpl
import com.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<ProductRepository> {
        ProductRepositoryImpl(get())
    }

    single<CategoryRepository> {
        CategoryRepositoryImpl(get())
    }

    single<CartRepository> {
        CartRepositoryImpl(get())

    }

    single<OrderRepository> {
        OrderRepositoryImpl(get())
    }

    single<UserRepository> {
        UserRepoImpl(get())
    }

}