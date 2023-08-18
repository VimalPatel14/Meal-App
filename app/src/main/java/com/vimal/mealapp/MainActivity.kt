package com.vimal.mealapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.vimal.mealapp.adapter.CharacterAdapter
import com.vimal.mealapp.adapter.ShimmerAdapter
import com.vimal.mealapp.databinding.ActivityMainBinding
import com.vimal.mealapp.interfaces.ItemClickListener
import com.vimal.mealapp.model.Category
import com.vimal.mealapp.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ItemClickListener {

    private val viewModel: CharacterViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: CharacterAdapter
    lateinit var adapterShimmer: ShimmerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dummyDataList: List<String> = listOf(
            "Item 1", "Item 2", "Item 3", "Item 4", "Item 5",
            "Item 6", "Item 7", "Item 8", "Item 9", "Item 10",
            "Item 11", "Item 12", "Item 13", "Item 14", "Item 15",
        )
        adapterShimmer = ShimmerAdapter(dummyDataList)
        adapter = CharacterAdapter(this@MainActivity)
        val layoutManager = GridLayoutManager(this, 2)
        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.adapter = adapter
        binding.progressDialog.adapter = adapterShimmer

        viewModel.characterList.observe(this) {
            binding.progressDialog.visibility = View.GONE
            binding.recyclerview.visibility = View.VISIBLE
            viewModel.allNotes.observe(this, Observer { list ->
                if (list.isNotEmpty()) {
                    for (firstItem in list) {
                        val secondItem = it.categories.find {
                            it.idCategory == firstItem.idCategory
                        }
                        secondItem?.isFavourite = true
                    }
                    adapter.setCharacter(it.categories)
                } else {
                    adapter.setCharacter(it.categories)
                }
            })

        }

        viewModel.errorMessage.observe(this) {
//            viewModel.allNotes.observe(this, Observer { list ->
//                if (list.isNotEmpty()) {
//                    binding.recyclerview.visibility = View.VISIBLE
//                    adapter.setCharacter(list)
//                } else {
            Toast.makeText(
                this@MainActivity,
                "We are having some issue. No Offline Data", Toast.LENGTH_SHORT
            ).show()
//                }
//            })
        }

        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
                binding.recyclerview.visibility = View.GONE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })

        viewModel.getAllCharacter()
    }

    override fun onItemClick(position: Category) {
        val characterJson = Gson().toJson(position)
        val intent = Intent(this, MealListActivity::class.java)
        intent.putExtra("extra_object", characterJson)
        startActivity(intent)
    }

    override fun onFavouriteItemClick(pos: Int, favourite: Boolean, position: Category) {
        Toast.makeText(
            this@MainActivity,
            "Add to ${position.idCategory}", Toast.LENGTH_SHORT
        ).show()
        lifecycleScope.launch {
            if (favourite)
                viewModel.addCharacter(position)
            else
                viewModel.deleteCharacter(position)
        }
    }
}