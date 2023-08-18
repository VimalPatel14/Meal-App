package com.vimal.mealapp.interfaces

import com.vimal.mealapp.model.Category

interface ItemClickListener {

    fun onItemClick(position: Category)

    fun onFavouriteItemClick(pos: Int,favourite: Boolean, position: Category)

}