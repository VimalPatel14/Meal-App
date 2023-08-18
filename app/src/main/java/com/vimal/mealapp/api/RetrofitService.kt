package com.vimal.mealapp.api

import com.vimal.mealapp.model.CategoryResponse
import com.vimal.mealapp.model.MealResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("categories.php")
    suspend fun getAllCharacter(): Response<CategoryResponse>

    @GET("filter.php")
    suspend fun getAllMeal(@Query("c") name: String): Response<MealResponse>

    @GET("lookup.php")
    suspend fun getMealDetail(@Query("i") id: String): Response<MealResponse>

    @GET("search.php")
    suspend fun getSearchMeal(@Query("s") name: String): Response<MealResponse>

//    @GET("character/{id}")
//    suspend fun getCharacterDetails(@Path("id") id: Int): Response<RickSanchez>
//
//    @GET("episode/{id}")
//    suspend fun getEpisode(@Path("id") id: Int): Response<Episode>

}