package com.vimal.mealapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.vimal.mealapp.MealListActivity
import com.vimal.mealapp.R
import com.vimal.mealapp.adapter.CharacterAdapter
import com.vimal.mealapp.databinding.FragmentFavouriteBinding
import com.vimal.mealapp.interfaces.ItemClickListener
import com.vimal.mealapp.model.Category
import com.vimal.mealapp.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment(), ItemClickListener {

    private val viewModel: CharacterViewModel by viewModels()
    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CharacterAdapter(this)
        val layoutManager = GridLayoutManager(context, 2)
        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.adapter = adapter

        viewModel.allNotes.observe(viewLifecycleOwner, Observer { list ->
            adapter.characterList.clear()
            if (list.isNotEmpty()) {
                binding.recyclerview.visibility = View.VISIBLE
                binding.nodata.visibility = View.GONE
                adapter.setCharacter(list)
            } else {
                binding.recyclerview.visibility = View.GONE
                binding.nodata.visibility = View.VISIBLE
            }
        })

    }

    override fun onResume() {
        super.onResume()
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.visibility = View.VISIBLE
    }

    override fun onItemClick(position: Category) {
        val characterJson = Gson().toJson(position)
        val bundle = Bundle().apply {
            putString("category", characterJson)
        }
        findNavController().navigate(R.id.action_favoriteMealsFragment_to_mealFragment, bundle)
//        val characterJson = Gson().toJson(position)
//        val intent = Intent(context, MealListActivity::class.java)
//        intent.putExtra("extra_object", characterJson)
//        startActivity(intent)
    }

    override fun onFavouriteItemClick(pos: Int, favourite: Boolean, position: Category) {
        viewModel.deleteCharacter(position)
        adapter.removePosition(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}