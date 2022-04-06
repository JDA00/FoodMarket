package com.jda00.android.foodmarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jda00.android.foodmarket.R
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), FoodListFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = FoodListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }

    }

    override fun onFoodSelected(foodId: UUID) {
//        Log.d(TAG, "MainActivity.onFoodSelected: $foodId")
        val fragment = FoodFragment.newInstance(foodId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}