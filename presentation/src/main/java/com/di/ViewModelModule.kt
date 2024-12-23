package com.di

import com.example.shoppo.ui.feature.account.login.LoginViewModel
import com.example.shoppo.ui.feature.account.register.RegisterViewModel
import com.example.shoppo.ui.feature.cart.CartViewModel
import com.example.shoppo.ui.feature.home.HomeViewModel
import com.example.shoppo.ui.feature.orders.OrdersViewModel
import com.example.shoppo.ui.feature.product_details.ProductDetailsViewModel
import com.example.shoppo.ui.feature.summary.CartSummaryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { ProductDetailsViewModel(get()) }
    viewModel { CartViewModel(get(), get(), get()) }
    viewModel { CartSummaryViewModel(get(), get()) }
    viewModel { OrdersViewModel(get()) }
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        RegisterViewModel(get())
    }


}