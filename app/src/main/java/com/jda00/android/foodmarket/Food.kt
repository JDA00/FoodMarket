package com.jda00.android.foodmarket

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class Food(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var name: String = "",
    var category: Int = 0,
    var image: Int = 0,
    var quantity: String = "",
    var date: Date = Date(),
    var freshFor: String = "",
    var isAvailable: Boolean = false

) {
    override fun toString(): String = "$name\n$quantity\n$freshFor"
}


