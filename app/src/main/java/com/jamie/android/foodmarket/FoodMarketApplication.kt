package com.jamie.android.foodmarket

import android.app.Application

class FoodMarketApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FoodRepository.initialize(this)
    }
}