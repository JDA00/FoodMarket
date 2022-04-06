package database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jda00.android.foodmarket.Food
import java.util.*

@Dao
interface FoodDao {

    @Query("SELECT * FROM food")
    fun getFoods(): LiveData<List<Food>>

    @Query("SELECT * FROM food WHERE id=(:id)")
    fun getFood(id: UUID): LiveData<Food?>

    @Update
    fun updateFood(food: Food)

    @Insert
    fun addFood(food: Food)

    @Delete
    fun deleteFood(food: Food)

}