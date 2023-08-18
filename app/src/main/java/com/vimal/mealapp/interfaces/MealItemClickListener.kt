package com.vimal.mealapp.interfaces

import com.vimal.mealapp.model.Meal

interface MealItemClickListener {

    fun onMealItemClick(position: Meal)

}