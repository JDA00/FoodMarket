package com.jamie.android.foodmarket

import androidx.lifecycle.ViewModel

class FoodListViewModel : ViewModel() {


    private val foodRepository = FoodRepository.get()
    val foodListLiveData = foodRepository.getFoods()

    fun addFood(food: Food) {
        foodRepository.addFood(food)
    }


}
