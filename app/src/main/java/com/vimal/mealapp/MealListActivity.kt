package com.vimal.mealapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.vimal.mealapp.adapter.MealAdapter
import com.vimal.mealapp.adapter.ShimmerAdapter
import com.vimal.mealapp.databinding.ActivityMealListBinding
import com.vimal.mealapp.interfaces.MealItemClickListener
import com.vimal.mealapp.model.Category
import com.vimal.mealapp.model.Meal
import com.vimal.mealapp.viewmodel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealListActivity : AppCompatActivity(), MealItemClickListener {

    private lateinit var binding: ActivityMealListBinding
    private val viewModel: MealViewModel by viewModels()
    lateinit var adapter: MealAdapter
    lateinit var adapterShimmer: ShimmerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val characterJson = intent.getStringExtra("extra_object")
        val character = Gson().fromJson(characterJson, Category::class.java)
        Log.e("vml", "category ${character.strCategory}")

        val dummyDataList: List<String> = listOf(
            "Item 1", "Item 2", "Item 3", "Item 4", "Item 5",
            "Item 6", "Item 7", "Item 8", "Item 9", "Item 10",
            "Item 11", "Item 12", "Item 13", "Item 14", "Item 15",
        )

        adapterShimmer = ShimmerAdapter(dummyDataList)
        adapter = MealAdapter(this@MealListActivity)
        val layoutManager = GridLayoutManager(this, 2)
        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.adapter = adapter
        binding.progressDialog.adapter = adapterShimmer

        viewModel.mealList.observe(this) {
            Log.e("vml", "Episode ${it.meals}")
            binding.progressDialog.visibility = View.GONE
            binding.recyclerview.visibility = View.VISIBLE
            adapter.setCharacter(it.meals)
            viewModel.addAllCharacter(it.meals)
        }

        viewModel.errorMessage.observe(this) {
            lifecycleScope.launch {
                val meals = viewModel.getAllMeal(character.strCategory)
                if (meals != null) {
                    binding.progressDialog.visibility = View.GONE
                    binding.recyclerview.visibility = View.VISIBLE
                    adapter.setAllEpisode(meals)
                } else {
                    Toast.makeText(
                        this@MealListActivity,
                        "We are having some issue. No Offline Data", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
                binding.recyclerview.visibility = View.GONE
            } else {
                binding.recyclerview.visibility = View.VISIBLE
                binding.progressDialog.visibility = View.GONE
            }
        })

        viewModel.getAllCharacter(character.strCategory)

        binding.name.text = character.strCategory
        Glide.with(this@MealListActivity)
            .load(character.strCategoryThumb)
            .placeholder(R.drawable.loading)
            .into(binding.imageview)
    }

    override fun onMealItemClick(position: Meal) {
        val intent = Intent(this, MealDetailsActivity::class.java)
        intent.putExtra("id", position.idMeal)
        startActivity(intent)
    }
}