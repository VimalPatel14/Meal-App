package com.vimal.mealapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vimal.mealapp.model.Category

@Dao
interface FavouriteMealCategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Category): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(note: List<Category>)

    @Update
    suspend fun update(note: Category)

    @Delete
    suspend fun delete(note: Category)

    @Query("DELETE FROM category_table")
    suspend fun deleteAllCharacter()

    @Query("Select * from category_table")
    fun getAllCharacter(): LiveData<List<Category>>
}