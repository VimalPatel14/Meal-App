package com.vimal.mealapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.vimal.mealapp.databinding.ActivityMealDetailsBinding
import com.vimal.mealapp.viewmodel.MealDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealDetailsBinding
    private val viewModel: MealDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id")

        viewModel.mealList.observe(this) {
            Log.e("vml", "Episode ${it.meals}")
            Toast.makeText(
                this@MealDetailsActivity,
                it.meals.get(0).strMeal, Toast.LENGTH_SHORT
            ).show()

            Glide.with(this@MealDetailsActivity)
                .load(it.meals.get(0).strMealThumb)
                .placeholder(R.drawable.loading)
                .into(binding.ivMealDetails)

            binding.tvMealNameDetails.text = it.meals.get(0).strMeal
            binding.tvMealCategoryDetails.text = it.meals.get(0).strCategory
            binding.tvMealAreaDetails.text = it.meals.get(0).strArea
            binding.tvIngredient1.text = it.meals.get(0).strIngredient1
            binding.tvIngredient2.text = it.meals.get(0).strIngredient2
            binding.tvIngredient3.text = it.meals.get(0).strIngredient3
            binding.tvIngredient4.text = it.meals.get(0).strIngredient4
            binding.tvIngredient5.text = it.meals.get(0).strIngredient5
            binding.tvInstructions.text = it.meals.get(0).strInstructions
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(
                this@MealDetailsActivity,
                "We are having some issue. No Offline Data", Toast.LENGTH_SHORT
            ).show()
        }
        viewModel.loading.observe(this, Observer {

        })
        viewModel.getAllCharacter(id!!)
    }
}