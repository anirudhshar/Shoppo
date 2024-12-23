package com.example.shoppo

import android.app.Application
import com.di.dataModule
import com.di.domainModule
import com.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShoppoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ShoppoApp)
            modules(listOf(presentationModule, domainModule, dataModule))
        }
    }

}