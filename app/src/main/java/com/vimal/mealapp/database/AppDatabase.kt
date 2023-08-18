package com.vimal.mealapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vimal.mealapp.model.Category
import com.vimal.mealapp.model.Meal

@Database(entities = [Category::class, Meal::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCharacterDao(): FavouriteMealCategoryDao
    abstract fun getMealDao(): MealDao
}