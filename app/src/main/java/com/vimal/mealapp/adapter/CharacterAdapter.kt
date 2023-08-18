package com.vimal.mealapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vimal.mealapp.R
import com.vimal.mealapp.interfaces.ItemClickListener
import com.vimal.mealapp.model.Category

class CharacterAdapter(
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    var characterList = mutableListOf<Category>()
    private var isLoading = false

    fun setCharacter(movies: List<Category>) {
        val uniqueMovies = movies.filter { movie ->
            !characterList.any { it.strCategory == movie.strCategory } // Check if the movie with the same id is not already in the list
        }
        characterList.addAll(uniqueMovies)
        isLoading = false
        notifyDataSetChanged()
    }

    fun showLoadingCharacter() {
        isLoading = true
        notifyItemInserted(characterList.size) // Insert the loading item at the end
    }

    fun removePosition(position: Int) {
        characterList.removeAt(position)
        notifyItemChanged(position)
    }

    fun removePosition(position: Category) {
        characterList.remove(position)
        notifyDataSetChanged()
    }

    fun removeAllPosition() {
        characterList.clear()
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading && position == itemCount - 1) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_LOADING) {
            val loadingView = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_loading, parent, false)
            LoadingViewHolder(loadingView)
        } else {
            val inflater = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_character, parent, false)
            ViewHolder(inflater)
        }
    }

    private class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainlay: CardView = itemView.findViewById(R.id.mainlay)
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val favourite: ImageView = itemView.findViewById(R.id.favourite)
        val name: TextView = itemView.findViewById(R.id.name)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val character = characterList[position]
                holder.name.text = character.strCategory
                Glide.with(holder.itemView.context)
                    .load(character.strCategoryThumb)
                    .placeholder(R.drawable.loading)
                    .into(holder.imageView)
                holder.mainlay.setOnClickListener {
                    itemClickListener.onItemClick(character)
                }
                if (character.isFavourite) {
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.ic_favorite_essential)
                        .placeholder(R.drawable.ic_favorite_essential)
                        .into(holder.favourite)
                } else {
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.ic_favorite)
                        .placeholder(R.drawable.ic_favorite)
                        .into(holder.favourite)
                }

                holder.favourite.setOnClickListener {
                    if (character.isFavourite) {
                        character.isFavourite = false
                        Glide.with(holder.itemView.context)
                            .load(R.drawable.ic_favorite)
                            .placeholder(R.drawable.ic_favorite)
                            .into(holder.favourite)
                    } else {
                        character.isFavourite = true
                        Glide.with(holder.itemView.context)
                            .load(R.drawable.ic_favorite_essential)
                            .placeholder(R.drawable.ic_favorite_essential)
                            .into(holder.favourite)
                    }
                    itemClickListener.onFavouriteItemClick(position,character.isFavourite, character)
                }
            }

            is LoadingViewHolder -> {
                // You can customize the loading view here if needed
            }
        }
    }

    override fun getItemCount(): Int {
        // Add 1 for the loading item when it's visible
        return if (isLoading) {
            characterList.size + 1
        } else {
            characterList.size
        }
    }
}