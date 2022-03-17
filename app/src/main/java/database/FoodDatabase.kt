package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jamie.android.foodmarket.Food

@Database(entities = [Food::class], version = 1, exportSchema = false)

// Adding TypeConverters
@TypeConverters(FoodTypeConverters::class)

abstract class FoodDatabase : RoomDatabase() {

    // Register DAO in database
    abstract fun foodDao(): FoodDao

}