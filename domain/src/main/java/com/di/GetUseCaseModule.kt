package com.di

import com.usecase.AddToCartUseCase
import com.usecase.CartSummaryUseCase
import com.usecase.DeleteProductUseCase
import com.usecase.GetCartUseCase
import com.usecase.GetCategoryUseCase
import com.usecase.GetProductUseCase
import com.usecase.LoginUseCase
import com.usecase.OrderListUseCase
import com.usecase.PlaceOrderUseCase
import com.usecase.RegisterUseCase
import com.usecase.UpdateQuantityUseCase
import org.koin.dsl.module

val  useCaseModule= module {

    factory {
        GetProductUseCase(get())
    }
    factory {
        GetCategoryUseCase(get())
    }
    factory {
        AddToCartUseCase(get())
    }

    factory {
        GetCartUseCase(get())
    }

    factory {
        UpdateQuantityUseCase(get())
    }

    factory {
        DeleteProductUseCase(get())
    }

    factory {
        CartSummaryUseCase(get())
    }

    factory {
        PlaceOrderUseCase(get())
    }

    factory {
        OrderListUseCase(get())

    }
    factory {
        LoginUseCase(get())
    }
    factory {
        RegisterUseCase(get())
    }
}