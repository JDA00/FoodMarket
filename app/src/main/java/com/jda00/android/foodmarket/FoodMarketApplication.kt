package com.jda00.android.foodmarket

import android.app.Application

class FoodMarketApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FoodRepository.initialize(this)
    }
}