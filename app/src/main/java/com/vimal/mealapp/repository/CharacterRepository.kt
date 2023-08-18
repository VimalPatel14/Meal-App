package com.vimal.mealapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vimal.mealapp.api.NetworkState
import com.vimal.mealapp.api.RetrofitService
import com.vimal.mealapp.database.FavouriteMealCategoryDao
import com.vimal.mealapp.model.Category
import com.vimal.mealapp.model.CategoryResponse
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val retrofitService: RetrofitService,
    private val favouriteMealCategoryDao: FavouriteMealCategoryDao
) {
    suspend fun getAllCharacter(): NetworkState<CategoryResponse> {
        val response = retrofitService.getAllCharacter()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null)
                NetworkState.Success(responseBody)
            else
                NetworkState.Error(response)
        } else
            NetworkState.Error(response)
    }

    val allNotes: LiveData<List<Category>> = favouriteMealCategoryDao.getAllCharacter()

    suspend fun insert(character: Category) {
        favouriteMealCategoryDao.insert(character)
    }

    suspend fun insertAll(characterList: List<Category>) {
        favouriteMealCategoryDao.insertAll(characterList)
    }

    suspend fun delete(character: Category) {
        favouriteMealCategoryDao.delete(character)
    }

    suspend fun deleteAll() {
        favouriteMealCategoryDao.deleteAllCharacter()
    }

    suspend fun update(character: Category) {
        favouriteMealCategoryDao.update(character)
    }
}