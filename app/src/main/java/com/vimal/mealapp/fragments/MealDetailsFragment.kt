package com.vimal.mealapp.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.vimal.mealapp.R
import com.vimal.mealapp.databinding.FragmentHomeBinding
import com.vimal.mealapp.databinding.FragmentMealDetailsBinding
import com.vimal.mealapp.databinding.FragmentSearchBinding
import com.vimal.mealapp.model.Category
import com.vimal.mealapp.model.Meal
import com.vimal.mealapp.viewmodel.MealDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealDetailsFragment : Fragment(){

    private var _binding: FragmentMealDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MealDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle ->
            val mealJson = bundle.getString("mealId")
            val meal = Gson().fromJson(mealJson, Meal::class.java)
            viewModel.mealList.observe(viewLifecycleOwner) {
                Glide.with(this)
                    .load(it.meals.get(0).strMealThumb)
                    .placeholder(R.drawable.loading)
                    .into(binding.ivMealDetails)

                val videoUrl = it.meals.get(0).strYoutube

                Log.e("vml", videoUrl.toString())

                binding.webview.settings.javaScriptEnabled = true
                binding.webview.settings.pluginState = WebSettings.PluginState.ON
                binding.webview.webChromeClient = WebChromeClient()
                binding.webview.loadData(
                    "<iframe width=\"100%\" height=\"100%\" src=\"$videoUrl\" frameborder=\"0\" allowfullscreen></iframe>",
                    "text/html",
                    "utf-8"
                )

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

            viewModel.errorMessage.observe(viewLifecycleOwner) {
                Toast.makeText(
                    context,
                    "We are having some issue. No Offline Data", Toast.LENGTH_SHORT
                ).show()
            }
            viewModel.loading.observe(viewLifecycleOwner, Observer {

            })
            viewModel.getAllCharacter(meal.idMeal)
        }

    }

    override fun onResume() {
        super.onResume()
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}