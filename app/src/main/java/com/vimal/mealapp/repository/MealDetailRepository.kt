package com.vimal.mealapp.repository

import com.vimal.mealapp.api.NetworkState
import com.vimal.mealapp.api.RetrofitService
import com.vimal.mealapp.model.MealResponse
import javax.inject.Inject

class MealDetailRepository @Inject constructor(
    private val retrofitService: RetrofitService
) {
    suspend fun getAllCharacter(id: String): NetworkState<MealResponse> {
        val response = retrofitService.getMealDetail(id)
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null)
                NetworkState.Success(responseBody)
            else
                NetworkState.Error(response)
        } else
            NetworkState.Error(response)

    }

//    fun getAllMeal(meal: String): LiveData<List<Meal>> {
//        return mealCategoryDao.getAllMeal(meal)
//    }
//
//    suspend fun insert(character: Meal) {
//        mealCategoryDao.insert(character)
//    }
//
//    suspend fun insertAll(characterList: List<Meal>) {
//        mealCategoryDao.insertAll(characterList)
//    }
//
//    suspend fun delete(character: Meal) {
//        mealCategoryDao.delete(character)
//    }
//
//    suspend fun deleteAll() {
//        mealCategoryDao.deleteAllMeal()
//    }
//
//    suspend fun update(character: Meal) {
//        mealCategoryDao.update(character)
//    }
}