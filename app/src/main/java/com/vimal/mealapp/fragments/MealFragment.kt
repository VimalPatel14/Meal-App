package com.vimal.mealapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.vimal.mealapp.MealDetailsActivity
import com.vimal.mealapp.R
import com.vimal.mealapp.adapter.MealAdapter
import com.vimal.mealapp.adapter.ShimmerAdapter
import com.vimal.mealapp.databinding.FragmentMealBinding
import com.vimal.mealapp.interfaces.MealItemClickListener
import com.vimal.mealapp.model.Category
import com.vimal.mealapp.model.Meal
import com.vimal.mealapp.viewmodel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealFragment : Fragment(), MealItemClickListener {

    private var _binding: FragmentMealBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MealViewModel by viewModels()
    lateinit var adapter: MealAdapter
    lateinit var adapterShimmer: ShimmerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle ->
            val characterJson = bundle.getString("category")
            val character = Gson().fromJson(characterJson, Category::class.java)
            Log.d("seleccion", character.toString())

            val dummyDataList: List<String> = listOf(
                "Item 1", "Item 2", "Item 3", "Item 4", "Item 5",
                "Item 6", "Item 7", "Item 8", "Item 9", "Item 10",
                "Item 11", "Item 12", "Item 13", "Item 14", "Item 15",
            )

            adapterShimmer = ShimmerAdapter(dummyDataList)
            adapter = MealAdapter(this)
            val layoutManager = GridLayoutManager(context, 2)
            binding.recyclerview.layoutManager = layoutManager
            binding.recyclerview.adapter = adapter
            binding.progressDialog.adapter = adapterShimmer

            viewModel.mealList.observe(viewLifecycleOwner) {
                Log.e("vml", "Episode ${it.meals}")
                binding.progressDialog.visibility = View.GONE
                binding.recyclerview.visibility = View.VISIBLE
                adapter.setCharacter(it.meals)
                viewModel.addAllCharacter(it.meals)
            }

            viewModel.errorMessage.observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    val meals = viewModel.getAllMeal(character.strCategory)
                    if (meals != null) {
                        binding.progressDialog.visibility = View.GONE
                        binding.recyclerview.visibility = View.VISIBLE
                        adapter.setAllEpisode(meals)
                    } else {
                        Toast.makeText(
                            context,
                            "We are having some issue. No Offline Data", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            viewModel.loading.observe(viewLifecycleOwner, Observer {
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
            Glide.with(this)
                .load(character.strCategoryThumb)
                .placeholder(R.drawable.loading)
                .into(binding.imageview)
        }

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMealItemClick(position: Meal) {
//        val intent = Intent(context, MealDetailsActivity::class.java)
//        intent.putExtra("id", position.idMeal)
//        startActivity(intent)
        val characterJson = Gson().toJson(position)
        val bundle = Bundle().apply {
            putString("mealId", characterJson)
        }
        findNavController().navigate(R.id.action_mealFragment_to_mealDetailsFragment, bundle)
    }
}