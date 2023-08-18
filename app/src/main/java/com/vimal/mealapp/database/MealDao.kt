package com.vimal.mealapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vimal.mealapp.model.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Meal): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(note: List<Meal>)

    @Update
    suspend fun update(note: Meal)

    @Delete
    suspend fun delete(note: Meal)

    @Query("DELETE FROM meal_table")
    suspend fun deleteAllMeal()

    @Query("Select * from meal_table WHERE strMeal = :meal order by idMeal ASC")
    fun getAllMeal(meal: String): LiveData<List<Meal>>
}