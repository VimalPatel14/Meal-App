package com.vimal.mealapp.fragments

import android.content.Intent
import android.os.Bundle
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.vimal.mealapp.MealListActivity
import com.vimal.mealapp.R
import com.vimal.mealapp.adapter.CharacterAdapter
import com.vimal.mealapp.adapter.ShimmerAdapter
import com.vimal.mealapp.databinding.FragmentHomeBinding
import com.vimal.mealapp.interfaces.ItemClickListener
import com.vimal.mealapp.model.Category
import com.vimal.mealapp.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), ItemClickListener {

    private val viewModel: CharacterViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: CharacterAdapter
    lateinit var adapterShimmer: ShimmerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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
        adapter = CharacterAdapter(this)
        val layoutManager = GridLayoutManager(context, 2)
        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.adapter = adapter
        binding.progressDialog.adapter = adapterShimmer

        viewModel.characterList.observe(viewLifecycleOwner) {
            binding.progressDialog.visibility = View.GONE
            binding.recyclerview.visibility = View.VISIBLE
            viewModel.allNotes.observe(viewLifecycleOwner) { list ->
                adapter.characterList.clear()
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
            }

        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
//            viewModel.allNotes.observe(this, Observer { list ->
//                if (list.isNotEmpty()) {
//                    binding.recyclerview.visibility = View.VISIBLE
//                    adapter.setCharacter(list)
//                } else {
            Toast.makeText(
                context,
                "We are having some issue. No Offline Data", Toast.LENGTH_SHORT
            ).show()
//                }
//            })
        }

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
                binding.recyclerview.visibility = View.GONE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })

//        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
//        if (bottomNavigationView.visibility == View.GONE){
//            bottomNavigationView.visibility = View.VISIBLE
//        }

        viewModel.getAllCharacter()
    }

    override fun onItemClick(position: Category) {
        val characterJson = Gson().toJson(position)
        val bundle = Bundle().apply {
            putString("category", characterJson)
        }
        findNavController().navigate(R.id.action_homeFragment_to_mealFragment, bundle)

//        val characterJson = Gson().toJson(position)
//        val intent = Intent(context, MealListActivity::class.java)
//        intent.putExtra("extra_object", characterJson)
//        startActivity(intent)
    }

    override fun onFavouriteItemClick(pos: Int, favourite: Boolean, position: Category) {
        lifecycleScope.launch {
            if (favourite)
                viewModel.addCharacter(position)
            else
                viewModel.deleteCharacter(position)
        }
    }

    override fun onResume() {
        super.onResume()
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}