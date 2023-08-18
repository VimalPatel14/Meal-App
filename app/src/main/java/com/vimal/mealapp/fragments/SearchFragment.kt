package com.vimal.mealapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.vimal.mealapp.R
import com.vimal.mealapp.adapter.MealAdapter
import com.vimal.mealapp.adapter.ShimmerAdapter
import com.vimal.mealapp.databinding.FragmentSearchBinding
import com.vimal.mealapp.interfaces.MealItemClickListener
import com.vimal.mealapp.model.Meal
import com.vimal.mealapp.viewmodel.SearchMealViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), MealItemClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchMealViewModel by viewModels()
    lateinit var adapter: MealAdapter
    lateinit var adapterShimmer: ShimmerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            Toast.makeText(
                context,
                "We are having some issue. No Offline Data", Toast.LENGTH_SHORT
            ).show()
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
        binding.progressDialog.visibility = View.GONE
        binding.svMeals.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.progressDialog.visibility = View.VISIBLE
                    viewModel.getAllCharacter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onMealItemClick(position: Meal) {
        val characterJson = Gson().toJson(position)
        val bundle = Bundle().apply {
            putString("mealId", characterJson)
        }
        findNavController().navigate(R.id.action_searchMealsFragment_to_mealDetailsFragment, bundle)
    }

    override fun onResume() {
        super.onResume()
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}