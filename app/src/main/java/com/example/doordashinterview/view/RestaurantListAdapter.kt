package com.example.doordashinterview.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doordashinterview.databinding.ListRestaurantItemBinding
import com.squareup.picasso.Picasso

class RestaurantListAdapter : RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder>() {
    private val list = mutableListOf<RestaurantUIModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = ListRestaurantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(
            binding
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(list[position], position)
    }


    class RestaurantViewHolder(private val binding: ListRestaurantItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurantUIModel: RestaurantUIModel, position: Int) {

            binding.apply {
                statusTextView.text = restaurantUIModel.status
                subtitleTextView.text = restaurantUIModel.subtitle
                titleTextView.text = restaurantUIModel.title
                Picasso.get().load(restaurantUIModel.imageUrl).into(imageView)
            }

        }
    }

    fun addList(newList: List<RestaurantUIModel>) {
        val firstNewListPosition = list.size
        list.addAll(newList)
        notifyItemRangeInserted(firstNewListPosition, newList.size)
    }
}


