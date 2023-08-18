package com.vimal.mealapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vimal.mealapp.R

class ShimmerAdapter(private val characterList: List<String>) :
    RecyclerView.Adapter<ShimmerAdapter.LoadingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadingViewHolder {
        val loadingView = LayoutInflater.from(parent.context)
            .inflate(R.layout.shimmer_layout, parent, false)
        return LoadingViewHolder(loadingView)
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val name: TextView = itemView.findViewById(R.id.name)
    }

    override fun onBindViewHolder(holder: LoadingViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(R.drawable.loading)
            .placeholder(R.drawable.loading)
            .into(holder.imageView)
        holder.name.text = holder.itemView.context.getString(R.string.app_name)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }
}